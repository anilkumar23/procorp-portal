package com.procorp.community.resource;

import com.procorp.community.dtos.CommunityDTO;
import com.procorp.community.entities.Community;
import com.procorp.community.entities.CommunityMember;
import com.procorp.community.service.CommunnityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String joinCommunity(@Valid @RequestParam Long commId ,@RequestParam Long memberID) {
        communnityService.joinCommunity(commId,memberID);
        return "community request submitted";
    }

    @PutMapping("/acceptCommunityJoinRequest")
    public String acceptCommunityJoinRequest(@Valid @RequestParam Long commId ,@RequestParam Long memberID) {
        communnityService.acceptCommunityJoinRequest(commId,memberID);
        return "community request submitted";
    }

    @GetMapping("/communityMembersList")
    public List<CommunityMember> getCommunityMembersList(@Valid @RequestParam Long commId) {
        return communnityService.getCommunityMembersList(commId);
    }

    @GetMapping("/getCommunityMembersRequests")
    public List<CommunityMember> getCommunityMembersRequests(@Valid @RequestParam Long commId) {
        return communnityService.getCommunityMembersRequests(commId);
    }

}

