//package com.procorp.chat.resource;
//
//import com.procorp.chat.dtos.CommunityDTO;
//import com.procorp.chat.entities.Community;
//import com.procorp.chat.entities.CommunityMember;
//import com.procorp.chat.service.CommunityService;
//import com.procorp.chat.service.FriendService;
//import com.procorp.chat.service.MemberService;
//import jakarta.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("community-service1")
//public class CommunityResource1 {
//    private final static Logger LOG = LoggerFactory.getLogger(CommunityResource1.class);
//
//
//    @Autowired
//    private CommunityService communityService;
//    @Autowired
//    private FriendService friendsService;
//
//    @Autowired
//    private MemberService memberService;
//
//    @PostMapping("/community")
//    public String createCommunity(@Valid @RequestBody CommunityDTO community) throws Exception {
//        return communityService.createCommunity(community);
//    }
//
//    @PutMapping("/community")
//    public String updateCommunity(@Valid @RequestParam Long commId ,@RequestBody CommunityDTO community) {
//        communityService.updateCommunity(commId,community);
//        return "community details updated";
//    }
//
//    @GetMapping("/communities")
//    public List<Community>  getAllCommunities() {
//        return communityService.getAllCommunities();
//    }
//
//    @GetMapping("/community")
//    public Community  getCommunityById(@Valid @RequestParam Long commId ) {
//        return communityService.getCommunityById(commId);
//    }
//
//
//    @PutMapping("/joinCommunity")
//    public String joinCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
//        return communityService.joinCommunity(commId,memberId);
//    }
//
//    @PutMapping("/acceptCommunityJoinRequest")
//    public String acceptCommunityJoinRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
//        return communityService.acceptCommunityJoinRequest(commId,memberId);
//    }
//
//    @GetMapping("/communityMembersList")
//    public List<CommunityMember> getCommunityMembersList(@Valid @RequestParam Long commId) {
//        return communityService.getCommunityMembersList(commId);
//    }
//
//    @GetMapping("/getCommunityMembersRequests")
//    public List<CommunityMember> getCommunityMembersRequests(@Valid @RequestParam Long commId) {
//        return communityService.getCommunityMembersRequests(commId);
//    }
//
//
//    //This API can be used for rejecting member join request,comm invite request
//    @DeleteMapping("/rejectCommunityJoinRequest")
//    public String rejectCommunityJoinRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
//         return communityService.rejectCommunityJoinRequest(commId,memberId);
//    }
//
//    @PutMapping("/inviteMemberToCommunityRequest")
//    public String inviteMemberToCommunityRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
//        return communityService.inviteMemberToCommunityRequest(commId,memberId);
//    }
//
//
//    //reject APIs and delete APIs are pending
//
//    // APIS to manage communities by Admins
//
//    //approve communiity
//
//    //get comm by ID
//    //get community request for member
//
//    @PutMapping("/approveCommunity")
//    public String approveCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
//        return communityService.approveCommunity(commId,memberId);
//    }
//
//    @DeleteMapping("/rejectCommunityCreationRequest")
//    public String rejectCommunityCreationRequest(@Valid @RequestParam Long commId) {
//        return communityService.rejectCommunityCreationRequest(commId);
//    }
//
//    @GetMapping("/communityCreationRequestsList")
//    public List<Community> getCommunityCreationRequestsList(@Valid @RequestParam Long commId ) {
//        return communityService.getCommunityCreationRequestsList();
//    }
//
//
//
////
////    join to community
////    reject comm request
////
////    create community chat ,read only ,ban member
////
////
////    approve coummunity creation
////
////    leave community
////
////    delete communitu
////            \
////    add community post
////    delete post
////    community access type or privacy settings
////
////1. community management
////            member management
////                    privacy managment
////                            post managment
////                                    chat management
////
//}
//
