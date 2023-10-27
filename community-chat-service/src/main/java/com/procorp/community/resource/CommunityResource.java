package com.procorp.community.resource;

import com.procorp.community.dtos.CommunityDTO;
import com.procorp.community.dtos.GlobalResponseDTO;
import com.procorp.community.entities.Community;
import com.procorp.community.entities.CommunityMember;
import com.procorp.community.service.CommunnityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("community-service")
@SecurityRequirement(name = "bearerAuth")
public class CommunityResource {
    private final static Logger LOG = LoggerFactory.getLogger(CommunityResource.class);


    @Autowired
    private CommunnityService communnityService;
//    @Autowired
//    private FriendService friendsService;
//
//    @Autowired
//    private MemberService memberService;

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
    public ResponseEntity<?> getAllCommunities() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the communities details list")
                        .responseObj(communnityService.getAllCommunities())
                        .build());
        //return communnityService.getAllCommunities();
    }


    @PutMapping("/joinCommunity")
    public ResponseEntity<?> joinCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberID) {
        communnityService.joinCommunity(commId,memberID);
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

    @PutMapping("/acceptCommunityJoinRequest")
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
    }

}

