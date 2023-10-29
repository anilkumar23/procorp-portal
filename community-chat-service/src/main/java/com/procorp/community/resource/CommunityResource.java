package com.procorp.community.resource;

import com.procorp.community.dtos.CommunityDTO;
import com.procorp.community.entities.Community;
import com.procorp.community.entities.CommunityMember;
import com.procorp.community.service.CommunityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("community-service")
@SecurityRequirement(name = "bearerAuth")
public class CommunityResource {
    private final static Logger LOG = LoggerFactory.getLogger(CommunityResource.class);

    @Autowired
    private CommunityService communityService;


    @PostMapping(value = "/createCommunity", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createCommunity(@Valid @ModelAttribute CommunityDTO community){
        return communityService.createCommunity(community);
    }

    @PutMapping(value ="/updateCommunity", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateCommunity(@Valid @RequestParam Long commId ,@ModelAttribute CommunityDTO community) {
        communityService.updateCommunity(commId,community);
        return "community details updated";
    }

    @GetMapping("/communities")
    public List<Community>  getAllCommunities() {
        return communityService.getAllCommunities();
    }

    @GetMapping("/community")
    public Community  getCommunityById(@Valid @RequestParam Long commId) {
        return communityService.getCommunityById(commId);
    }

    @GetMapping("/communitiesByOwner")
    public List<Community>  getCommunityByOwner(@Valid @RequestParam Long memberId) {
        return communityService.getCommunitiesByOwner(memberId);
    }

    @PutMapping("/joinCommunity")
    public String joinCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        return communityService.joinCommunity(commId,memberId);
    }

    @PutMapping("/acceptCommunityMemberRequest")
    public String acceptCommunityMemberRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        return communityService.acceptCommunityMemberRequest(commId,memberId);
    }

    @GetMapping("/communityMembersList")
    public List<CommunityMember> getCommunityMembersList(@Valid @RequestParam Long commId) {
        return communityService.getCommunityMembersList(commId);
    }
//PHASE-2
//    @GetMapping("/getCommunityMembersRequests")
//    public List<CommunityMember> getCommunityMembersRequests(@Valid @RequestParam Long commId) {
//        return communityService.getCommunityMembersRequests(commId);
//    }


    //PHASE-2 This API can be used for rejecting member join request,comm invite request
//    @DeleteMapping("/rejectCommunityJoinRequest")
//    public String rejectCommunityJoinRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
//        return communityService.rejectCommunityJoinRequest(commId,memberId);
//    }

    //PHASE-2
//    @PutMapping("/inviteMemberToCommunityRequest")
//    public String inviteMemberToCommunityRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
//        return communityService.inviteMemberToCommunityRequest(commId,memberId);
//    }


    //reject APIs and delete APIs are pending

    // APIS to manage communities by Admins

    //approve community

    //get comm by ID
    //get community request for member

    @PutMapping("/approveCommunity")
    public String approveCommunity(@Valid @RequestParam Long commId) {
        return communityService.approveCommunity(commId);
    }

    @DeleteMapping("/rejectCommunityCreationRequest")
    public String rejectCommunityCreationRequest(@Valid @RequestParam Long commId) {
        return communityService.rejectCommunityCreationRequest(commId);
    }

    @GetMapping("/communityCreationRequestsList")
    public List<Community> getCommunityCreationRequestsList() {
        return communityService.getCommunityCreationRequestsList();
    }



//
//    join to community
//    reject comm request
//
//    create community chat ,read only ,ban member
//
//
//    approve community creation
//
//    leave community
//
//    delete community
//            \
//    add community post
//    delete post
//    community access type or privacy settings
//
//1. community management
//            member management
//                    privacy management
//                            post management
//                                    chat management
//
}

