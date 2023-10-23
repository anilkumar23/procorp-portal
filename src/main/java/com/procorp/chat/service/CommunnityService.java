package com.procorp.chat.service;

import com.procorp.chat.dao.CommunityDao;
import com.procorp.chat.dao.CommunityMemberDao;
import com.procorp.chat.dtos.CommunityDTO;
import com.procorp.chat.entities.Community;
import com.procorp.chat.entities.CommunityMember;
import com.procorp.chat.entities.FriendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunnityService {

    @Autowired
    private CommunityDao communityDao;


    @Autowired
    private CommunityMemberDao communityMmberDao;

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
        communityMmberDao.save(member);

    }

    public void acceptCommunityJoinRequest(Long commId, Long memberID) {
        CommunityMember member = communityMmberDao.findBymemberId(memberID);
        member.setStatus("accepted");
//        CommunityMember member = new CommunityMember();
//        member.setMemberId(memberID);
//        member.setCommId(commId);
//        member.setStatus("accepted");
        communityMmberDao.save(member);

    }

    public List<CommunityMember> getCommunityMembersList(Long commId) {
       List<CommunityMember> list = communityMmberDao.findAll();
        return list.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("accepted"))
                                    .collect(Collectors.toList());
    }

    public List<CommunityMember> getCommunityMembersRequests(Long commId) {
        List<CommunityMember> list = communityMmberDao.findAll();
        return list.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("waiting"))
                .collect(Collectors.toList());
    }
}
