package com.procorp.chat.resource;

import com.procorp.chat.dtos.CommunityDTO;
import com.procorp.chat.dtos.FriendRequestDTO;
import com.procorp.chat.entities.Community;
import com.procorp.chat.entities.CommunityMember;
import com.procorp.chat.entities.Member;
import com.procorp.chat.service.CommunnityService;
import com.procorp.chat.service.FriendService;
import com.procorp.chat.service.MemberService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("community-service")
public class CommunityResource {
    private final static Logger LOG = LoggerFactory.getLogger(CommunityResource.class);


    @Autowired
    private CommunnityService communnityService;
    @Autowired
    private FriendService friendsService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/community")
    public String createCommunity(@Valid @RequestBody CommunityDTO community) {
        communnityService.createCommunity(community);
        return "community created";
    }

    @PutMapping("/community")
    public String updateCommunity(@Valid @RequestParam Long commId ,@RequestBody CommunityDTO community) {
        communnityService.updateCommunity(commId,community);
        return "community details updated";
    }

    @GetMapping("/communities")
    public List<Community>  getAllCommunities() {
        return communnityService.getAllCommunities();
    }


    @PutMapping("/joinCommunity")
    public String joinCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        communnityService.joinCommunity(commId,memberId);
        return "community request submitted";
    }

    @PutMapping("/acceptCommunityJoinRequest")
    public String acceptCommunityJoinRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        return communnityService.acceptCommunityJoinRequest(commId,memberId);
    }

    @GetMapping("/communityMembersList")
    public List<CommunityMember> getCommunityMembersList(@Valid @RequestParam Long commId) {
        return communnityService.getCommunityMembersList(commId);
    }

    @GetMapping("/getCommunityMembersRequests")
    public List<CommunityMember> getCommunityMembersRequests(@Valid @RequestParam Long commId) {
        return communnityService.getCommunityMembersRequests(commId);
    }


    // APIS to manage communities by Admins

    //approve communiity

    @PutMapping("/approveCommunity")
    public String approveCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        communnityService.approveCommunity(commId,memberId);
        return "community request approved";
    }



//
//    @PutMapping("/inviteToCommunity")
//    public String inviteToCommunity(@Valid @RequestParam Long commId ,@RequestBody Community community) {
////        LOG.info("Student :: Student Name {}", student.getFullName());
//        communnityService.updateCommunity(commId,community);
//        return "community details updated";
//    }
//
//    join to community
//    reject comm request
//
//    create community chat ,read only ,ban member
//
//
//    approve coummunity creation
//
//    leave community
//
//    delete communitu
//            \
//    add community post
//    delete post
//    community access type or privacy settings
//
//1. community management
//            member management
//                    privacy managment
//                            post managment
//                                    chat management
//
}

