package com.procorp.community.service;


import com.google.common.collect.Multimap;
import com.procorp.community.dtos.CommunityChatDto;
import com.procorp.community.dtos.CommunityChatHistoryDto;
import com.procorp.community.dtos.GlobalResponseDTO;
import com.procorp.community.entities.CommunityChat;
import com.procorp.community.entities.CommunityMember;
import com.procorp.community.exception.ChatIllegalStateException;
import com.procorp.community.repository.CommunityChatDao;
import com.procorp.community.repository.CommunityMemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CommunityChatService {

    @Autowired
    private CommunityMemberDao communityMemberDao;

    @Autowired
    private CommunityChatDao communityChatDao;

    public ResponseEntity<?> createCommunityChat(CommunityChatDto dto) {
        CommunityMember member = communityMemberDao.findByCommIdAndMemberId(dto.getCommunityId(), dto.getMemberId());
        if (null != member && (member.getStatus().equalsIgnoreCase("accepted") ||
                member.getStatus().equalsIgnoreCase("approved"))) {
            String chatId = prepareChatId(dto.getCommunityId(), dto.getDate());

            CommunityChat chat = CommunityChat.builder()
                    .chatId(chatId)
                    .communityId(dto.getCommunityId())
                    .memberId(dto.getMemberId())
                    .date(dto.getDate())
                    .communityChatHistory(insertTimeStampToEachMsg(dto.getCommunityChatHistory()))
                    .build();
            communityChatDao.save(chat);
//            CopyOnWriteArrayList<Multimap<String, CommunityChatHistoryDto>> chatHistory = chat1.getCommunityChatHistory();
//            dto.setCommunityChatHistory(chatHistory);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Chat has been Successfully created")
                            .responseObj(dto)
                            .build());
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Chat creation failed due to member is not part of the community or has insufficient privileges")
                            .responseObj(null)
                            .build());
        }
    }

    private CommunityChatDto mapEntityToDto(CommunityChat chat) {
        return CommunityChatDto.builder()
                .communityId(chat.getCommunityId())
                .memberId(chat.getMemberId())
                .date(chat.getDate())
                .communityChatHistory(chat.getCommunityChatHistory())
                .build();
    }

    private String prepareChatId(long communityId, String date) {
        return communityId + "_" + date;
    }

    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CopyOnWriteArrayList<Multimap<String, CommunityChatHistoryDto>> insertTimeStampToEachMsg(CopyOnWriteArrayList<Multimap<String, CommunityChatHistoryDto>> chatHistory) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (Multimap<String, CommunityChatHistoryDto> hist : chatHistory) {
            for (String chat : hist.keySet()) {
                for (CommunityChatHistoryDto s : hist.get(chat)) {
                    s.setTimestamp(sdf3.format(timestamp));
                    System.out.println(s);
                }
            }
        }
        return chatHistory;
    }


    public ResponseEntity<?> updateLatestChat(CommunityChatDto chatDTO) {
        CommunityMember member = communityMemberDao.findByCommIdAndMemberId(chatDTO.getCommunityId(), chatDTO.getMemberId());
        if (null != member && (member.getStatus().equalsIgnoreCase("accepted") ||
                member.getStatus().equalsIgnoreCase("approved"))) {
            String chatId = prepareChatId(chatDTO.getCommunityId(), chatDTO.getDate());
            Optional<CommunityChat> optionalChat = communityChatDao.findById(chatId);
            if (optionalChat.isPresent()) {
                CommunityChat chat = optionalChat.get();
                CopyOnWriteArrayList<Multimap<String, CommunityChatHistoryDto>> chatHistory = chat.getCommunityChatHistory();
                for (Multimap<String, CommunityChatHistoryDto> i : chatHistory) {
                    for (Multimap<String, CommunityChatHistoryDto> m : chatDTO.getCommunityChatHistory()) {
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        for (String n : m.keySet()) {
                            //TODO need to enhance inner for loops
                            for (CommunityChatHistoryDto s : m.get(n)) {
                                s.setTimestamp(sdf3.format(timestamp));
                                System.out.println(s);
                            }
                            for (String a : i.keySet()) {
                                if (a.equalsIgnoreCase(n)) {
                                    i.get(n).addAll(m.get(n));
                                } else {
                                    Collection<CommunityChatHistoryDto> s = m.get(n);
                                    s.forEach(b -> i.put(n, b));
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                CommunityChat newChat = new CommunityChat(prepareChatId(chatDTO.getCommunityId(),
                        chatDTO.getDate()), chatDTO.getCommunityId(), chatDTO.getMemberId(), chatDTO.getDate(),
                        chatHistory);
                communityChatDao.save(newChat);

                chatDTO.setCommunityChatHistory(chatHistory);

            } else {
                communityChatDao.save(new CommunityChat(chatId, chatDTO.getCommunityId(), chatDTO.getMemberId(), chatDTO.getDate(),
                        insertTimeStampToEachMsg(chatDTO.getCommunityChatHistory())));
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Chat Updation failed due to member is not part of the community or has insufficient privileges")
                            .responseObj(null)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Chat has updated Successfully")
                        .responseObj(chatDTO)
                        .build());
    }

    public ResponseEntity<?> findCommunityChat(long communityId, String date) {
        Optional<CommunityChat> chat = communityChatDao.findById(prepareChatId(communityId, date));
        if (chat.isEmpty()) {
            throw new ChatIllegalStateException("Unable to find chat at " + date + " for communityId " + communityId);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Chat has been fetched Successfully for communityId " + communityId + " and date " + date)
                        .responseObj(chat.get().getCommunityChatHistory())
                        .build());
    }

    public String deleteChatMessages(CommunityChatDto chatDTO) {
        String chatId = prepareChatId(chatDTO.getCommunityId(), chatDTO.getDate());
        Optional<CommunityChat> optionalChat = communityChatDao.findById(chatId);
        if (optionalChat.isPresent()) {
            CommunityChat chat = optionalChat.get();
            chat.getCommunityChatHistory().
                    forEach(n -> n.entries().
                            forEach(j -> chatDTO.getCommunityChatHistory().
                                    forEach(p -> p.entries().
                                            forEach(h ->
                                            {
                                              if (j.getValue().equals(h.getValue())) n.remove(h.getKey(), h.getValue());
                                            }
            ))));
            communityChatDao.save(chat);

            return "Messages have been successfully deleted";
        } else {
            return "Member has no sufficient permissions to delete the chat";
        }
    }
}
