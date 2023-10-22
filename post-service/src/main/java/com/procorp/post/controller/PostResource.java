package com.procorp.post.controller;


import com.procorp.post.dto.PostRequestDto;
import com.procorp.post.dto.PostResponseDTO;
import com.procorp.post.dto.PostShareDetailsRequestDto;
import com.procorp.post.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/file")
@SecurityRequirement(name = "bearerAuth")
public class PostResource {

    @Autowired
    private PostService service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@ModelAttribute PostRequestDto requestDto) {
        return new ResponseEntity<>(service.uploadFile(requestDto), HttpStatus.OK);
    }

    @PostMapping(value = "/reSharePost")
    public ResponseEntity<String> uploadFile(@RequestBody PostShareDetailsRequestDto requestDto) {
        return new ResponseEntity<>(service.reSharePost(requestDto), HttpStatus.OK);
    }
    @GetMapping(value = "/getPostsByMemberId/{memberId}")
    public ResponseEntity<ArrayList<PostResponseDTO>> getPostsByMemberId(@PathVariable long memberId) {
        return new ResponseEntity<>(service.getPosts(memberId), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "image/png")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }
}
