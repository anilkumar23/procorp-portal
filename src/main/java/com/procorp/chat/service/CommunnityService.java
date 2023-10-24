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
import java.util.Optional;
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
        community.setCommunityStatus("pending-for-approval");

        Community record = communityDao.save(community);

        // Inserting the member as owner in comm member table
        CommunityMember member = new CommunityMember();
        member.setMemberId(communityDto.getMemberId());
        member.setCommId(record.getId());
        member.setRole("owner");
        member.setStatus("waiting");
        communityMemberDao.save(member);
    }


    public void updateCommunity(Long commId,CommunityDTO communityDto){
        Community community = new Community();
        community.setId(commId);
        community.setMemberId(communityDto.getMemberId());
        community.setCommName(communityDto.getCommName());
        community.setCommDescription(communityDto.getCommDescription());
        communityDao.save(community);
    }

    public List<Community> getAllCommunities() {
        return communityDao.findAll();
    }

    public void joinCommunity(Long commId, Long memberId) {
        CommunityMember member = new CommunityMember();
        member.setMemberId(memberId);
        member.setCommId(commId);
        member.setStatus("waiting");
        member.setRole("member");
        communityMemberDao.save(member);

    }

    public String acceptCommunityJoinRequest(Long commId, Long memberId) {
        CommunityMember member = communityMemberDao.findBymemberId(memberId);
        if(member!= null) {
            member.setStatus("accepted");
            communityMemberDao.save(member);
        } else {
            return "member or community don't exist";
        }
        return "community request accepted";
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

    public void approveCommunity(Long commId, Long memberId) {
        Optional<Community> community = communityDao.findById(commId);
        community.get().setCommunityStatus("approved-by-admin");
        communityDao.save(community.get());
        CommunityMember member = communityMemberDao.findByCommIdAndMemberId(commId,memberId);
        member.setStatus("approved");
        communityMemberDao.save(member);
    }
}
