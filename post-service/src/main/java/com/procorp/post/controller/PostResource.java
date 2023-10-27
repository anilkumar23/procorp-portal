package com.procorp.post.controller;


import com.procorp.post.dto.PostRequestDto;
import com.procorp.post.dto.PostResponseDTO;
import com.procorp.post.dto.PostShareDetailsRequestDto;
import com.procorp.post.exception.GlobalResponseDTO;
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
    public ResponseEntity<?> uploadFile(@ModelAttribute PostRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("File uploaded Successfully")
                        .responseObj(service.uploadFile(requestDto))
                        .build());
        //return new ResponseEntity<>(service.uploadFile(requestDto), HttpStatus.OK);
    }

    @PostMapping(value = "/reSharePost")
    public ResponseEntity<?> uploadFile(@RequestBody PostShareDetailsRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Post Re-shared Successfully..")
                        .responseObj(service.reSharePost(requestDto))
                        .build());
        //return new ResponseEntity<>(service.reSharePost(requestDto), HttpStatus.OK);
    }

    @GetMapping(value = "/getPostsByMemberId")
    public ResponseEntity<?> getPostsByMemberId(@RequestParam long memberId,
                                                @RequestParam(defaultValue = "0") final Integer pageNumber,
                                                @RequestParam(defaultValue = "5") final Integer size) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the posts Successfully by memberID")
                        .responseObj(service.getPosts(memberId, pageNumber, size))
                        .build());
       // return new ResponseEntity<>(service.getPosts(memberId), HttpStatus.OK);
    }

/*    @GetMapping(value = "/getPostsByMemberId")
    public  ResponseEntity<?> getPostsByMemberId(@RequestParam long memberId,
                                                 @RequestParam(defaultValue = "0") final Integer pageNumber,
                                                 @RequestParam(defaultValue = "5") final Integer size) {
        return service.getPosts(memberId, pageNumber, size);*/

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
    public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("removed file Successfully")
                        .responseObj(service.deleteFile(fileName))
                        .build());
        //return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }
}
