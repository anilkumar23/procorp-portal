package com.procorp.community.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.procorp.community.dtos.CommunityPostRequestDto;
import com.procorp.community.dtos.CommunityPostResponseDTO;
import com.procorp.community.dtos.GlobalResponseDTO;
import com.procorp.community.entities.Community;
import com.procorp.community.entities.CommunityPost;
import com.procorp.community.entities.PostComment;
import com.procorp.community.entities.PostLike;
import com.procorp.community.repository.CommunityDao;
import com.procorp.community.repository.CommunityPostDao;
import com.procorp.community.repository.PostCommentDao;
import com.procorp.community.repository.PostLikeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommunityPostService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    WebClient webClient;

    @Autowired
    CommunityPostDao dao;

    @Autowired
    CommunityDao communityDao;

    @Autowired
    PostLikeDao postLikesDao;

    @Autowired
    PostCommentDao postCommentDao;

    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public ResponseEntity<?> uploadFile(CommunityPostRequestDto requestDto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formattedTimestamp = sdf3.format(timestamp);
        CommunityPost post  = null;
        if(requestDto.getMedia() == null || !StringUtils.hasText(requestDto.getMedia().toString())){
            post = dao.save(mapDtoTOEntity(requestDto, "", "", formattedTimestamp));
        }else {
            File fileObj = convertMultiPartFileToFile(requestDto.getMedia());
            String fileName = requestDto.getMemberId() + "_" + formattedTimestamp + "." + requestDto.getMediaType().toLowerCase();
            PutObjectResult s3response = s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            if (s3response != null && s3response.getMetadata() != null) {
                String s3Url = s3Client.getUrl(bucketName, fileName).toString();
                post = dao.save(mapDtoTOEntity(requestDto, s3Url, fileName, formattedTimestamp));
            }
            fileObj.delete();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Posted successfully")
                        .responseObj(post)
                        .build());
    }
    private Community getCommunity(long communityId){
        Optional<Community> community = communityDao.findById(communityId);
        return community.get();
    }
    private CommunityPost mapDtoTOEntity(CommunityPostRequestDto dto, String s3Url, String fileName, String timestamp){
        Community community = getCommunity(dto.getCommunityId());
        return CommunityPost.builder()
                .communityId(dto.getCommunityId())
                .postDescription(dto.getPostDescription())
                .timestamp(timestamp)
                .mediaPath(s3Url)
                .postOwner(dto.getMemberId())
                .mediaType(dto.getMediaType())
                .mediaName(fileName)
                .communityName(community.getCommName())
                .communityPhoto(community.getGroupPhoto())
                .build();
    }
    public ResponseEntity<?> getPosts(long communityId, int pageNo, int pageSize){

        Page<CommunityPost> posts = dao.findByCommunityId(communityId, PageRequest.of(pageNo, pageSize));

        Map<String, Object> response = convertToResponse(posts);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Posts have been successfully retrieved for communityId " + communityId)
                        .responseObj(response)
                        .build());
    }
    public ResponseEntity<?> getPosts(int pageNumber, int size) {
        Page<CommunityPost> posts = dao.findAll(PageRequest.of(pageNumber, size));
        Map<String, Object> response = convertToResponse(posts);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Posts have been successfully retrieved")
                        .responseObj(response)
                        .build());
    }
    public ResponseEntity<?> getAllPosts() {
        List<CommunityPost> posts = dao.findAll(Sort.by(Sort.Direction.DESC, "postId"));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Posts have been successfully retrieved")
                        .responseObj(mapEntitiesToDTO(posts))
                        .build());
    }
    public ResponseEntity<?> getAllPostsByCommId(long communityId){
        List<CommunityPost> posts = dao.findByCommunityId(communityId, Sort.by(Sort.Direction.DESC, "postId"));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Posts have been successfully retrieved for communityId " + communityId)
                        .responseObj(mapEntitiesToDTO(posts))
                        .build());
    }
    private Map<String, Object> convertToResponse(final Page<CommunityPost> pagePosts) {
        Map<String, Object> response = new HashMap<>();
        response.put("posts", mapEntitiesToDTO(pagePosts.getContent()));
        response.put("current-page", pagePosts.getNumber());
        response.put("total-items", pagePosts.getTotalElements());
        response.put("total-pages", pagePosts.getTotalPages());
        return response;
    }
    private List<CommunityPostResponseDTO> mapEntitiesToDTO(List<CommunityPost> posts){
        return posts.stream().map(n-> CommunityPostResponseDTO.builder()
                .postId(n.getPostId())
                .communityId(n.getCommunityId())
                .memberId(n.getPostOwner())
                .timestamp(n.getTimestamp())
                .postDescription(n.getPostDescription())
                .mediaType(n.getMediaType())
                .mediaPath(n.getMediaPath())
                .communityName(n.getCommunityName())
                .communityPhoto(n.getCommunityPhoto())
                .likeCount(3)
                .commentCount(5)
                .build()).collect(Collectors.toList());
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


    public Object likePost(Long postId, Long memberId, String memberName) {
        CommunityPost post = dao.findByPostId(postId);
        if(post!= null) {
            PostLike memberLike = postLikesDao.findByPostIdAndMemberId(postId,memberId);
            if(memberLike == null) {
                PostLike saveLike = new PostLike();
                saveLike.setMemberName(memberName);
                saveLike.setMemberId(memberId);
                saveLike.setPostId(postId);
                postLikesDao.save(saveLike);
                return "like submitted ";
            } else {
                return "member already liked the post ";
            }
        } else {
            return "postId doesn't exist";
        }
    }

    public Object CommentPost(Long postId, Long memberId, String memberName, String comment) {
        CommunityPost post = dao.findByPostId(postId);
        if(post!= null) {
                PostComment postComment = new PostComment();
                postComment.setMemberName(memberName);
                postComment.setMemberId(memberId);
                postComment.setPostId(postId);
                if(!comment.isEmpty()) {
                    postComment.setComment(comment);
                } else {
                    return "Comment should not be empty";
                }
                postCommentDao.save(postComment);
                return "Comment submitted ";
        } else {
            return "postId doesn't exist";
        }
    }

    public Object getPostLikes(Long postId) {
        return postLikesDao.findByPostId(postId);
    }

    public Object getPostComments(Long postId) {
        return postCommentDao.findByPostId(postId);
    }
}
