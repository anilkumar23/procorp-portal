package com.procorp.community.service;


import com.google.common.collect.Multimap;
import com.procorp.community.dtos.CommunityChatDto;
import com.procorp.community.dtos.CommunityChatHistoryDto;
import com.procorp.community.dtos.CommunityDTO;
import com.procorp.community.dtos.GlobalResponseDTO;
import com.procorp.community.entities.Community;
import com.procorp.community.entities.CommunityChat;
import com.procorp.community.entities.CommunityMember;
import com.procorp.community.exception.ChatIllegalStateException;
import com.procorp.community.repository.CommunityChatDao;
import com.procorp.community.repository.CommunityDao;
import com.procorp.community.repository.CommunityMemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class CommunnityService {

    @Autowired
    private CommunityDao communityDao;


    @Autowired
    private CommunityMemberDao communityMemberDao;

    public void createCommunity(CommunityDTO communityDto){
        Community community = new Community();
        community.setMemberId(communityDto.getMemberId());
        community.setCommName(communityDto.getCommName());
        community.setCommDescription(communityDto.getCommDescription());
        communityDao.save(community);
    }


    public void updateCommunity(Long commId,CommunityDTO communityDto){
        Community community = new Community();
        community.setMemberId(communityDto.getMemberId());
        community.setCommName(communityDto.getCommName());
        community.setCommDescription(communityDto.getCommDescription());
        communityDao.save(community);
    }

    public List<Community> getAllCommunities() {
        return communityDao.findAll();
    }

    public void joinCommunity(Long commId, Long memberID) {
        CommunityMember member = new CommunityMember();
        member.setMemberId(memberID);
        member.setCommId(commId);
        member.setStatus("waiting");
        communityMemberDao.save(member);

    }

    public void acceptCommunityJoinRequest(Long commId, Long memberID) {
        CommunityMember member = communityMemberDao.findByMemberId(memberID);
        member.setStatus("accepted");
//        CommunityMember member = new CommunityMember();
//        member.setMemberId(memberID);
//        member.setCommId(commId);
//        member.setStatus("accepted");
        communityMemberDao.save(member);

    }

    public List<CommunityMember> getCommunityMembersList(Long commId) {
       List<CommunityMember> list = communityMemberDao.findAll();
        return list.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("accepted"))
                                    .collect(Collectors.toList());
    }

    public List<CommunityMember> getCommunityMembersRequests(Long commId) {
        List<CommunityMember> list = communityMemberDao.findAll();
        return list.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("waiting"))
                .collect(Collectors.toList());
    }
}
