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
import com.procorp.post.dto.FriendRequestDTO;
import com.procorp.post.dto.PostRequestDto;
import com.procorp.post.dto.PostResponseDTO;
import com.procorp.post.dto.PostShareDetailsRequestDto;
import com.procorp.post.entity.Post;
import com.procorp.post.entity.PostShareDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    public String uploadFile(PostRequestDto requestDto) {
        File fileObj = convertMultiPartFileToFile(requestDto.getMedia());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formattedTimestamp = sdf3.format(timestamp);
        String fileName = requestDto.getMemberId() + "_" + formattedTimestamp + "." + requestDto.getMediaType().toLowerCase();
        PutObjectResult s3response = s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        if (s3response != null && s3response.getMetadata() !=null) {
            String s3Url = s3Client.getUrl(bucketName, fileName).toString();
            saveMediaData(mapDtoTOEntity(requestDto, s3Url, fileName, formattedTimestamp));
        }
        fileObj.delete();
        return "File uploaded : " + fileName;
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
    private void saveMediaData(Post post){
        dao.save(post);
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

    public ArrayList<PostResponseDTO> getPosts(long memberId){
        ArrayList<Post> posts = dao.getByPostOwner(memberId);
        Mono<List<FriendRequestDTO>> getFriendRequestSentResponse = webClient.get().uri("/getFriendRequests?requestFrom=" + memberId)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", FeignClientInterceptor.getBearerTokenHeader())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        List<FriendRequestDTO> friendRequestDTOS = getFriendRequestSentResponse.block();
        Set<Long> set = Objects.requireNonNull(friendRequestDTOS).stream().map(FriendRequestDTO::getRequestTo).collect(Collectors.toSet());
        set.addAll(Objects.requireNonNull(friendRequestDTOS).stream().map(FriendRequestDTO::getRequestFrom).collect(Collectors.toSet()));
        set.remove(memberId);
        posts.addAll(dao.findByPostOwnerIn(set));
        posts.addAll(dao.findAllById(Objects.requireNonNull(postShareDetailsDao.findByMemberIdIn(set)).stream()
                .map(PostShareDetails::getPostId).collect(Collectors.toSet())));
        return mapEntitiesToDTO(posts);
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
