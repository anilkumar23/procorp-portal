package com.procorp.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.procorp.post.config.FeignClientInterceptor;
import com.procorp.post.dao.PostDao;
import com.procorp.post.dao.PostShareDetailsDao;
import com.procorp.post.dto.*;
import com.procorp.post.entity.Post;
import com.procorp.post.entity.PostShareDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    WebClient webClient;

    @Autowired
    PostDao dao;

    @Autowired
    PostShareDetailsDao postShareDetailsDao;
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public ResponseEntity<?> uploadFile(PostRequestDto requestDto) {
        File fileObj = convertMultiPartFileToFile(requestDto.getMedia());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formattedTimestamp = sdf3.format(timestamp);
        Post post  = null;
                String fileName = requestDto.getMemberId() + "_" + formattedTimestamp + "." + requestDto.getMediaType().toLowerCase();
        PutObjectResult s3response = s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        if (s3response != null && s3response.getMetadata() !=null) {
            String s3Url = s3Client.getUrl(bucketName, fileName).toString();
           post = dao.save(mapDtoTOEntity(requestDto, s3Url, fileName, formattedTimestamp));
        }
        fileObj.delete();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Post "+ fileName +" has been successfully uploaded")
                        .responseObj(post)
                        .build());
    }

    private Post mapDtoTOEntity(PostRequestDto dto, String s3Url, String fileName, String timestamp){
        return Post.builder()
                .postDescription(dto.getPostDescription())
                .timestamp(timestamp)
                .mediaPath(s3Url)
                .postOwner(dto.getMemberId())
                .mediaType(dto.getMediaType())
                .mediaName(fileName)
                .build();
    }

    public String reSharePost(PostShareDetailsRequestDto requestDto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formattedTimestamp = sdf3.format(timestamp);
        PostShareDetails postShareDetails = PostShareDetails.builder()
                .memberId(requestDto.getMemberId())
                .postId(requestDto.getPostId())
                .timeStamp(formattedTimestamp)
                .build();
        postShareDetailsDao.save(postShareDetails);
        return "Post Re-shared Successfully..";
    }

    public ResponseEntity<?> getPosts(long memberId, int pageNo, int pageSize){
        Mono<List<FriendRequestDTO>> getFriendRequestSentResponse = webClient.get().uri("/getFriendRequests?requestFrom=" + memberId)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", FeignClientInterceptor.getBearerTokenHeader())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        if(getFriendRequestSentResponse!=null&&!getFriendRequestSentResponse.block().isEmpty()) {
            List<FriendRequestDTO> friendRequestDTOS = getFriendRequestSentResponse.block();
            Set<Long> set = Objects.requireNonNull(friendRequestDTOS).stream().map(FriendRequestDTO::getRequestTo).collect(Collectors.toSet());
            set.addAll(Objects.requireNonNull(friendRequestDTOS).stream().map(FriendRequestDTO::getRequestFrom).collect(Collectors.toSet()));
//        set.remove(memberId);
            Page<ArrayList<Post>> posts = dao.findByPostOwnerIn(set, PageRequest.of(pageNo, pageSize));

            /*Find mutual friends posts*/ //Once the requirement comes from UI then uncomment this
//        posts.addAll(dao.findAllById(Objects.requireNonNull(postShareDetailsDao.findByMemberIdIn(set)).stream()
//                .map(PostShareDetails::getPostId).collect(Collectors.toSet())));
//        ArrayList<PostResponseDTO> responseDTOS = mapEntitiesToDTO(dao.findByPostOwnerIn(set, PageRequest.of(pageNo, pageSize)));
            Map<String, Object> response = convertToResponse(posts);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Following posts have been retrieved for member " + memberId)
                            .responseObj(response)
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Following posts are empty")
                            .responseObj(null)
                            .build());
        }
    }
    private Map<String, Object> convertToResponse(final Page<ArrayList<Post>> pagePersons) {
        Map<String, Object> response = new HashMap<>();
        response.put("posts", pagePersons.getContent());
        response.put("current-page", pagePersons.getNumber());
        response.put("total-items", pagePersons.getTotalElements());
        response.put("total-pages", pagePersons.getTotalPages());
        return response;
    }
    private ArrayList<PostResponseDTO> mapEntitiesToDTO(ArrayList<Post> posts){
        return (ArrayList<PostResponseDTO>) posts.stream().map(n->{
           return PostResponseDTO.builder()
                   .postId(n.getPostId())
                   .memberId(n.getPostOwner())
                   .timestamp(n.getTimestamp())
                   .postDescription(n.getPostDescription())
                   .mediaType(n.getMediaType())
                   .mediaPath(n.getMediaPath())
                   .build();
        }).collect(Collectors.toList());
    }
    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
