package com.procorp.community.resource;

import com.procorp.community.dtos.CommunityPostRequestDto;
import com.procorp.community.service.CommunityPostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("community-post-service")
@SecurityRequirement(name = "bearerAuth")
public class CommunityPostResource {
    @Autowired
    private CommunityPostService service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@ModelAttribute CommunityPostRequestDto requestDto) {
        return new ResponseEntity<>(service.uploadFile(requestDto), HttpStatus.OK);
    }

    @GetMapping(value = "/getCommunityPosts")
    public  ResponseEntity<?> getPostsByMemberId(@RequestParam(defaultValue = "0") final Integer pageNumber,
                                                 @RequestParam(defaultValue = "5") final Integer size) {
        return service.getPosts(pageNumber, size);
    }

    @GetMapping(value = "/getAllCommunityPosts")
    public  ResponseEntity<?> getAllPosts() {
        return service.getAllPosts();
    }

    @GetMapping(value = "/getAllPostsByCommId")
    public  ResponseEntity<?> getAllPostsByCommId(@RequestParam long communityId) {
        return service.getAllPostsByCommId(communityId);
    }
   /* @GetMapping(value = "/getPostsByMemberId")
    public  ResponseEntity<?> getPostsByMemberId(@RequestParam long communityId,
                                                 @RequestParam(defaultValue = "0") final Integer pageNumber,
                                                 @RequestParam(defaultValue = "5") final Integer size) {
        return service.getPosts(communityId, pageNumber, size);
    }*/
}
