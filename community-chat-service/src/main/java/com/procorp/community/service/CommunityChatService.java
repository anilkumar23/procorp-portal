package com.procorp.community.service;


import com.google.common.collect.Multimap;
import com.procorp.community.dtos.*;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
                    .communityChatHistory(insertTimeStampToMsg(dto.getCommunityChatHistory(), dto.getMemberId()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Chat has been Successfully created")
                            .responseObj(communityChatDao.save(chat))
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

    private String prepareChatId(long communityId, String date) {
        return communityId + "_" + date;
    }

    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CopyOnWriteArrayList<CommunityChatHistoryResponseDto> insertTimeStampToMsg(CommunityChatHistoryDto chatHistory, long memberId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CopyOnWriteArrayList<CommunityChatHistoryResponseDto> list = new CopyOnWriteArrayList<>();
        list.add(prepareCommunityChatHistoryResponse(chatHistory, memberId, sdf3.format(timestamp)));
        return list;
    }
    private CommunityChatHistoryResponseDto prepareCommunityChatHistoryResponse(CommunityChatHistoryDto dto, long memberId, String timestamp){
        return CommunityChatHistoryResponseDto.builder()
                .memberId(memberId)
                .msg(dto.getMsg())
                .timestamp(timestamp)
                .build();
    }
    @Transactional
    public ResponseEntity<?> updateLatestChat(CommunityChatDto chatDTO) {
        CopyOnWriteArrayList<CommunityChatHistoryResponseDto> chatHistory = new CopyOnWriteArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommunityMember member = communityMemberDao.findByCommIdAndMemberId(chatDTO.getCommunityId(), chatDTO.getMemberId());
        if (null != member && (member.getStatus().equalsIgnoreCase("accepted") ||
                member.getStatus().equalsIgnoreCase("approved"))) {
            String chatId = prepareChatId(chatDTO.getCommunityId(), chatDTO.getDate());
            Optional<CommunityChat> optionalChat = communityChatDao.findById(chatId);
            if (optionalChat.isPresent()) {
                CommunityChat chat = optionalChat.get();
                chatHistory = chat.getCommunityChatHistory();

                chatHistory.add(prepareCommunityChatHistoryResponse(chatDTO.getCommunityChatHistory(),chatDTO.getMemberId(), sdf3.format(timestamp)));
                CommunityChat newChat = new CommunityChat(prepareChatId(chatDTO.getCommunityId(),
                        chatDTO.getDate()), chatDTO.getCommunityId(), chatDTO.getMemberId(), chatDTO.getDate(),
                        chatHistory);
                communityChatDao.save(newChat);
            } else {
                communityChatDao.save(new CommunityChat(chatId, chatDTO.getCommunityId(), chatDTO.getMemberId(), chatDTO.getDate(),
                        insertTimeStampToMsg(chatDTO.getCommunityChatHistory(), chatDTO.getMemberId())));
                chatHistory.add(prepareCommunityChatHistoryResponse(chatDTO.getCommunityChatHistory(), chatDTO.getMemberId(), sdf3.format(timestamp)));
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
                        .responseObj(CommunityChatResponseDto.builder()
                                .memberId(chatDTO.getMemberId())
                                .communityId(chatDTO.getCommunityId())
                                .date(chatDTO.getDate())
                                .communityChatHistory(chatHistory)
                                .build())
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

    @Transactional
    public ResponseEntity<?> deleteChatMessages(CommunityChatDeleteDto chatDTO) {
        String chatId = prepareChatId(chatDTO.getCommunityId(), chatDTO.getDate());
        Optional<CommunityChat> optionalChat = communityChatDao.findById(chatId);
        CommunityChatHistoryResponseDto communityChatHistoryResponseDto = chatDTO.getCommunityChatHistory();
        if (optionalChat.isPresent()) {
            CommunityChat chat = optionalChat.get();
            chat.getCommunityChatHistory().
                    forEach(n -> {
                        if (n.getMsg().equalsIgnoreCase(communityChatHistoryResponseDto.getMsg()) &&
                        n.getMemberId() == communityChatHistoryResponseDto.getMemberId() &&
                        n.getTimestamp().equalsIgnoreCase(communityChatHistoryResponseDto.getTimestamp()))
                            chat.getCommunityChatHistory().remove(n);
                    });
            communityChatDao.save(chat);



            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Messages have been successfully deleted")
                            .responseObj("Messages have been successfully deleted")
                            .build());
           // return "Messages have been successfully deleted";
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Member has no sufficient permissions to delete the chat")
                            .responseObj("Member has no sufficient permissions to delete the chat")
                            .build());
            //return "Member has no sufficient permissions to delete the chat";
        }
    }
}
