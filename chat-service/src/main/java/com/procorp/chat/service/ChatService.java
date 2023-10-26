package com.procorp.chat.service;

import com.google.common.collect.Multimap;
import com.procorp.chat.config.FeignClientInterceptor;
import com.procorp.chat.dao.ChatDao;
import com.procorp.chat.dtos.ChatDTO;
import com.procorp.chat.dtos.ChatHistoryDTO;
import com.procorp.chat.dtos.FriendRequestDTO;
import com.procorp.chat.entities.Chat;
import com.procorp.chat.exception.ChatIllegalStateException;
import com.procorp.chat.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatService {
    private final static Logger LOG = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private WebClient webClient;

    public ResponseEntity<Object> insertChat(ChatDTO chatDTO) {
        Mono<List<FriendRequestDTO>> getFriendRequestSentResponse = webClient.get().uri("/getFriendRequestsSent?requestFrom=" + chatDTO.getStudentId())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", FeignClientInterceptor.getBearerTokenHeader())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        Mono<List<FriendRequestDTO>> getFriendRequestReceivedResponse = webClient.get().uri("/getFriendRequestsReceived?requestFrom=" + chatDTO.getStudentId())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", FeignClientInterceptor.getBearerTokenHeader())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        List<FriendRequestDTO> friendRequestDTOS = getFriendRequestSentResponse.block();
        Objects.requireNonNull(friendRequestDTOS).addAll(Objects.requireNonNull(getFriendRequestReceivedResponse.block()));

        boolean flag = Objects.requireNonNull(friendRequestDTOS).stream().anyMatch(n->n.getFriendRequestStatus().equalsIgnoreCase("accepted"));
        if(flag) {
            String chatId = prepareChatId(chatDTO.getStudentId(), chatDTO.getChatPersonId(), chatDTO.getDate());
            chatDao.save(new Chat(chatId, chatDTO.getStudentId(), chatDTO.getChatPersonId(), chatDTO.getDate(),
                    insertTimeStampToEachMsg(chatDTO.getChatHistory())));
            LOG.info("Chat has been successfully created between " + chatDTO.getStudentId() + " and " +
                    chatDTO.getChatPersonId() + " with chatId => " + chatId);
            return ResponseEntity.ok("Chat has been created between " + chatDTO.getStudentId() + " and " +
                    chatDTO.getChatPersonId() + " with chatId => " + chatId);
        }
        return ResponseEntity.ok("Chat has not created between " + chatDTO.getStudentId() + " and " +
                chatDTO.getChatPersonId() + " as friend request is not approved");
    }
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private CopyOnWriteArrayList<Multimap<String, ChatHistoryDTO>> insertTimeStampToEachMsg(CopyOnWriteArrayList<Multimap<String, ChatHistoryDTO>> chatHistory){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (Multimap<String, ChatHistoryDTO> hist: chatHistory) {
            for (String chat: hist.keySet()) {
                for (ChatHistoryDTO s: hist.get(chat)) {
                    s.setTimestamp(sdf3.format(timestamp));
                    System.out.println(s);
                }
            }
        }
        return chatHistory;
    }
    public String deleteChatByMessages(ChatDTO chatDTO){
        Chat chat = validateChat(chatDTO);
        if(chat.getStudentId() == chatDTO.getStudentId()) {
            chat.getChatHistory().forEach(n-> n.entries().forEach(j-> chatDTO.getChatHistory().forEach(p-> p.entries().forEach(h->{
               if(j.getValue().equals(h.getValue())) n.remove(h.getKey(), h.getValue());
            }))));
            chatDao.save(chat);
            LOG.info("The following messages " + chatDTO.getChatHistory().toString() + chatDTO.getStudentId() + " have " +
                    "been deleted between" + chatDTO.getStudentId() + chatDTO.getChatPersonId() +
                    " for chatId => " + chat.getChatId());
            return "Selected Messages have been deleted successfully between" + chatDTO.getStudentId() +
                    chatDTO.getChatPersonId();
        }else{
            return "Member has no sufficient permissions to delete the chat";
        }
    }

    public CopyOnWriteArrayList<Multimap<String, ChatHistoryDTO>> findChat(long studentId, long chatPersonId, String date){
        String chatId = prepareChatId(studentId, chatPersonId, date);
        Optional<Chat> chat = chatDao.findById(chatId);
        if(chat.isEmpty()){
            throw new ChatIllegalStateException("Failed to update or remove Chat. Invalid chatId ==> " + chatId);
        }
        return chat.get().getChatHistory();
    }

    private Chat validateChat(ChatDTO chatDTO){
        String chatId = prepareChatId(chatDTO.getStudentId(),
                chatDTO.getChatPersonId(), chatDTO.getDate());
        Optional<Chat> chat = chatDao.findById(chatId);
        if(chat.isEmpty()){
            throw new ChatIllegalStateException("Failed to update or remove Chat. Invalid chatId ==> " + chatId);
        }
        return chat.get();
    }
    private String prepareChatId(long studentId, long chatPersonId, String date){
            return studentId +"_"+ chatPersonId +"_" + date;
    }
    public ChatDTO updateLatestChat(ChatDTO chatDTO) {
        String chatId = prepareChatId(chatDTO.getStudentId(),
                chatDTO.getChatPersonId(), chatDTO.getDate());
        Optional<Chat> optionalChat = chatDao.findById(chatId);
        if(optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            CopyOnWriteArrayList<Multimap<String, ChatHistoryDTO>> chatHistory = chat.getChatHistory();
            for (Multimap<String, ChatHistoryDTO> i : chatHistory) {
                for (Multimap<String, ChatHistoryDTO> m : chatDTO.getChatHistory()) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    for (String n : m.keySet()) {
                        //TODO need to enhance inner for loops
                        for (ChatHistoryDTO s: m.get(n)) {
                            s.setTimestamp(sdf3.format(timestamp));
                            System.out.println(s);
                        }
                        for (String a : i.keySet()) {
                            if (a.equalsIgnoreCase(n)) {
                                i.get(n).addAll(m.get(n));
                            } else {
                                Collection<ChatHistoryDTO> s = m.get(n);
                                s.forEach(b -> i.put(n, b));
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            Chat newChat = new Chat(prepareChatId(chatDTO.getStudentId(), chatDTO.getChatPersonId(),
                    chatDTO.getDate()), chatDTO.getStudentId(), chatDTO.getChatPersonId(), chatDTO.getDate(),
                    chatHistory);
            chatDao.save(newChat);
            LOG.info("Chat has been successfully Updated between " + chatDTO.getStudentId() + " and " +
                    chatDTO.getChatPersonId() + " for chatId => " + chat.getChatId());
            //we are setting this because to send the entire response back to UI
            chatDTO.setChatHistory(chatHistory);
        }else {
            chatDao.save(new Chat(chatId, chatDTO.getStudentId(), chatDTO.getChatPersonId(), chatDTO.getDate(),
                    insertTimeStampToEachMsg(chatDTO.getChatHistory())));
        }
        return chatDTO;
    }

//    public String updateChat(ChatDTO chatDTO) {
//        Chat chat = validateChat(chatDTO);
//        CopyOnWriteArrayList<Multimap<String, ChatHistoryDTO>>  chatHistory = chat.getChatHistory();
//        if(chatHistory !=null && !chatHistory.isEmpty() && chatDTO.getChatHistory()!=null && !chatDTO.getChatHistory().isEmpty()){
//            chat.setChatHistory(chatDTO.getChatHistory());
//            chatDao.save(chat);
//            LOG.info("Chat has been successfully Updated between "+ chatDTO.getStudentId() + " and " +
//                    chatDTO.getChatPersonId() + " for chatId => " + chat.getChatId());
//        }
//        return prepareChatId(chatDTO.getStudentId(), chatDTO.getChatPersonId(), chatDTO.getDate());
//    }
//public void deleteEntireChat(ChatDTO chatDTO){
//    List<Chat> chats = chatDao.findByStudentIdAndChatPersonId(chatDTO.getStudentId(), chatDTO.getChatPersonId());
//    chatDao.deleteAll(chats);
//}
}
