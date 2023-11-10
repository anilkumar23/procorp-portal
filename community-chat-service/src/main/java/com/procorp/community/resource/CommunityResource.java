package com.procorp.community.resource;

import com.procorp.community.dtos.CommunityDTO;
import com.procorp.community.dtos.GlobalResponseDTO;
import com.procorp.community.entities.Community;
import com.procorp.community.entities.CommunityMember;
import com.procorp.community.service.CommunityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

/*
    @PostMapping("/community")
    public ResponseEntity<?> createCommunity(@Valid @RequestBody CommunityDTO community) {
        communityService.createCommunity(community);
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
        communityService.updateCommunity(commId,community);
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
    public ResponseEntity<?> getAllCommunities() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the communities details list")
                        .responseObj(communityService.getAllCommunities())
                        .build());
    }
      */  //return communnityService.getAllCommunities();


    @PostMapping(value = "/createCommunity", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCommunity(@Valid @ModelAttribute CommunityDTO community) {
        String response = communityService.createCommunity(community);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg(response)
                        .responseObj(response)
                        .build());
    }

    @PutMapping(value = "/updateCommunity", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCommunity(@Valid @RequestParam Long commId, @ModelAttribute CommunityDTO community) {
        communityService.updateCommunity(commId, community);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("community details updated")
                        .responseObj("community details updated")
                        .build());
        //return "community details updated";
    }

    @GetMapping("/communities")
    public ResponseEntity<?> getAllCommunities() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the communities details list")
                        .responseObj(communityService.getAllCommunities())
                        .build());
        //return communityService.getAllCommunities();
    }

    @GetMapping("/community")
    public ResponseEntity<?> getCommunityById(@Valid @RequestParam Long commId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the community details By ID: " + commId)
                        .responseObj(communityService.getCommunityById(commId))
                        .build());
    }
    //  return communityService.getCommunityById(commId);


    @GetMapping("/communitiesByRole")
    public ResponseEntity<?> getCommunityByRole(@Valid @RequestParam Long memberId, @RequestParam String role) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the community details By Role "+role+" for memberId ID: " + memberId)
                        .responseObj(communityService.getCommunitiesByRole(memberId, role))
                        .build());
    }


    @PutMapping("/joinCommunity")
    public ResponseEntity<?> joinCommunity(@Valid @RequestParam Long commId, @RequestParam Long memberID) {
        String response = communityService.joinCommunity(commId, memberID);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg(response)
                        .responseObj(response)
                        .build());
        //return "community request submitted";
    }

    @PutMapping("/acceptCommunityMemberRequest")
    public ResponseEntity<?> acceptCommunityMemberRequest(@Valid @RequestParam Long commId, @RequestParam Long memberId) {
        String response = communityService.acceptCommunityMemberRequest(commId, memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg(response)
                        .responseObj(response)
                        .build());
    }

    @GetMapping("/communityMembersList")
    public ResponseEntity<?> getCommunityMembersList(@Valid @RequestParam Long commId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the communityMembersList")
                        .responseObj(communityService.getCommunityMembersList(commId))
                        .build());
        // return communityService.getCommunityMembersList(commId);
    }


   /* @PutMapping("/acceptCommunityMemberRequest")
    public String acceptCommunityMemberRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        return communityService.acceptCommunityMemberRequest(commId,memberId);
    }

    @GetMapping("/communityMembersList")
    public List<CommunityMember> getCommunityMembersList(@Valid @RequestParam Long commId) {
        return communityService.getCommunityMembersList(commId);
    }*/

    /*@PutMapping("/acceptCommunityJoinRequest")
    public ResponseEntity<?> acceptCommunityJoinRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberID) {
        communnityService.acceptCommunityJoinRequest(commId,memberID);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("community request submitted")
                        .responseObj("community request submitted")
                        .build());
        //return "community request submitted";
    }

    @GetMapping("/communityMembersList")
    public ResponseEntity<?> getCommunityMembersList(@Valid @RequestParam Long commId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the communities member list")
                        .responseObj(communnityService.getCommunityMembersList(commId))
                        .build());
        //return communnityService.getCommunityMembersList(commId);
    }

    @GetMapping("/getCommunityMembersRequests")
    public ResponseEntity<?> getCommunityMembersRequests(@Valid @RequestParam Long commId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the communities member request list")
                        .responseObj(communnityService.getCommunityMembersRequests(commId))
                        .build());
       // return communnityService.getCommunityMembersRequests(commId);

    public String joinCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberId) {
        return communityService.joinCommunity(commId,memberId);
    }*/

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
    public ResponseEntity<?> approveCommunity(@Valid @RequestParam Long commId) {
        String response = communityService.approveCommunity(commId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg(response)
                        .responseObj(response)
                        .build());
    }

    @DeleteMapping("/rejectCommunityCreationRequest")
    public ResponseEntity<?> rejectCommunityCreationRequest(@Valid @RequestParam Long commId) {
        String response = communityService.rejectCommunityCreationRequest(commId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg(response)
                        .responseObj(response)
                        .build());
    }

    @GetMapping("/communityCreationRequestsList")
    public ResponseEntity<?> getCommunityCreationRequestsList() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the communityCreationRequestsList")
                        .responseObj(communityService.getCommunityCreationRequestsList())
                        .build());
        // return communityService.getCommunityCreationRequestsList();
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
