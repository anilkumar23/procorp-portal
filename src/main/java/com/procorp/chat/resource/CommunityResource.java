package com.procorp.chat.resource;

import com.procorp.chat.dtos.CommunityDTO;
import com.procorp.chat.dtos.FriendRequestDTO;
import com.procorp.chat.dtos.GlobalResponseDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<?> createCommunity(@Valid @RequestBody CommunityDTO community) {
        communnityService.createCommunity(community);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("community created")
                        .responseObj("community created")
                        .build());
        //return "community created";
    }

    @PutMapping("/community")
    public ResponseEntity<?> updateCommunity(@Valid @RequestParam Long commId ,@RequestBody CommunityDTO community) {
        communnityService.updateCommunity(commId,community);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("community details updated")
                        .responseObj("community details updated")
                        .build());
       // return "community details updated";
    }

    @GetMapping("/communities")
    public ResponseEntity<?>  getAllCommunities() {

        List<Community> communities = communnityService.getAllCommunities();
      //  return communnityService.getAllCommunities();
        if(communities!=null && !communities.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the community details List")
                            .responseObj(communities)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("community details are empty")
                        .responseObj(new ArrayList<>())
                        .build());
    }


    @PutMapping("/joinCommunity")
    public ResponseEntity<?> joinCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        communnityService.joinCommunity(commId,memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("community request submitted")
                        .responseObj("community request submitted")
                        .build());
       // return "community request submitted";
    }

    @PutMapping("/acceptCommunityJoinRequest")
    public ResponseEntity<?> acceptCommunityJoinRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        return communnityService.acceptCommunityJoinRequest(commId,memberId);
    }

    @GetMapping("/communityMembersList")
    public ResponseEntity<?> getCommunityMembersList(@Valid @RequestParam Long commId) {
        List<CommunityMember> communityMembersList = communnityService.getCommunityMembersList(commId);
        //return communnityService.getCommunityMembersList(commId);
        if(communityMembersList!=null && !communityMembersList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the community member List")
                            .responseObj(communityMembersList)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("community member list details are empty")
                        .responseObj(new ArrayList<>())
                        .build());
    }

    @GetMapping("/getCommunityMembersRequests")
    public ResponseEntity<?> getCommunityMembersRequests(@Valid @RequestParam Long commId) {
      //  return communnityService.getCommunityMembersRequests(commId);
        List<CommunityMember> communityMembersRequest = communnityService.getCommunityMembersRequests(commId);
        if(communityMembersRequest!=null && !communityMembersRequest.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the community member Request List")
                            .responseObj(communityMembersRequest)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("community member request details are empty")
                        .responseObj(new ArrayList<>())
                        .build());
    }


    // APIS to manage communities by Admins

    //approve communiity

    @PutMapping("/approveCommunity")
    public ResponseEntity<?> approveCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        communnityService.approveCommunity(commId,memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("community request approved")
                        .responseObj("community request approved")
                        .build());
       // return "community request approved";
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

