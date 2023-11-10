package com.procorp.chat.resource;

import com.amazonaws.services.s3.AmazonS3;
import com.procorp.chat.dao.MemberDao;
import com.procorp.chat.dao.UserDao;
import com.procorp.chat.dtos.*;
import com.procorp.chat.entities.GoogleUser;
import com.procorp.chat.entities.Member;
import com.procorp.chat.service.MemberService;
import com.procorp.chat.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("user-service")
public class UserResource {
    private final static Logger LOG = LoggerFactory.getLogger(MemberService.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    WebClient webClient;

    @Value("${authservice.base.url}")
    private String authServiceBaseUrl;

    @Autowired
    private ImageUtil util;

    @Autowired
    private MemberDao memberDao;

    @PostMapping("/addUser")
    public ResponseEntity<GlobalResponseDTO> addUser(@RequestBody GoogleUserDTO googleUserDTO) {

        Mono<Object> flag = webClient.get().uri(authServiceBaseUrl+"/generateAuthToken?email=" + googleUserDTO.getEmail())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Object>() {});
        String token = Objects.requireNonNull(flag.block()).toString().replace("{","")
                .replace("}","").replace("token=","");

        try {
            Optional<GoogleUser> user = userDao.findByEmailAndUuid(googleUserDTO.getEmail(), googleUserDTO.getUuid());
            if (user.isPresent()) {
                String imageUrl = "";
                if (StringUtils.hasText(user.get().getImageURL())){
                    imageUrl = user.get().getImageURL();
                }else {
                    if (StringUtils.hasText(googleUserDTO.getImageURL()))
                        imageUrl = util.uploadProfileImageToS3(googleUserDTO.getImageURL(),googleUserDTO.getEmail());
                }
                GoogleUser user1 = userDao.save(GoogleUser.builder()
                        .memberId(user.get().getMemberId())
                        .userName(googleUserDTO.getUserName())
                        .uuid(googleUserDTO.getUuid())
                        .tokenId(token)
                        .email(googleUserDTO.getEmail())
                        .isEmailVerified(googleUserDTO.isEmailVerified())
                        .isMobileNoVerified(googleUserDTO.isMobileVerified())
                        .imageURL(imageUrl)
                        .mobileNumber(googleUserDTO.getMobileNumber())
                        .build());
                Member member = Member.builder()
                        .memberId(user1.getMemberId())
                        .userName(user1.getUserName())
                        .email(user1.getEmail())
                        .isMobileNoVerified(user1.getIsMobileNoVerified())
                        .isEmailVerified(user1.getIsEmailVerified())
                        .imageUrl(user1.getImageURL())
                        .build();
                memberDao.save(member);
                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(GlobalResponseDTO.builder()
                                .statusCode(HttpStatus.OK.value())
                                .status(HttpStatus.OK.name())
                                .msg("Member " + user.get().getEmail() + " is already present in the system " +
                                        "and have retrieved Successfully")
                                .responseObj(user1)
                                .build());
            }
            GoogleUser googleUser = null;
            if(googleUserDTO.getImageURL() != null && googleUserDTO.getImageURL().contains("http")){
                googleUser = GoogleUser.builder().userName(googleUserDTO.getUserName()).mobileNumber(googleUserDTO.getMobileNumber())
                        .email(googleUserDTO.getEmail()).tokenId(token).isEmailVerified(googleUserDTO.isEmailVerified())
                        .isMobileNoVerified(googleUserDTO.isMobileVerified()).uuid(googleUserDTO.getUuid())
                        .imageURL(util.uploadProfileImageToS3(googleUserDTO.getImageURL(), googleUserDTO.getUserName()) ).build();

            }else {
                 googleUser = GoogleUser.builder().userName(googleUserDTO.getUserName()).mobileNumber(googleUserDTO.getMobileNumber())
                        .email(googleUserDTO.getEmail()).tokenId(token).isEmailVerified(googleUserDTO.isEmailVerified())
                        .isMobileNoVerified(googleUserDTO.isMobileVerified()).uuid(googleUserDTO.getUuid())
                        .imageURL(googleUserDTO.getImageURL()).build();
            }
            GoogleUser googleUser1 = userDao.save(googleUser);
            Member member = Member.builder()
                    .memberId(googleUser1.getMemberId())
                    .userName(googleUser1.getUserName())
                    .email(googleUser1.getEmail())
                    .isMobileNoVerified(googleUser1.getIsMobileNoVerified())
                    .isEmailVerified(googleUser1.getIsEmailVerified())
                    .imageUrl(googleUser1.getImageURL())
                    .build();
            memberDao.save(member);
            LOG.info("Member {} Successfully created", googleUser.getEmail());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Member "+googleUser.getEmail()+" Successfully created")
                            .responseObj(mapEntityToDTO(googleUser))
                            .build());
        }catch (Exception ex){
            LOG.info("Member {} creation was unSuccessFull", googleUserDTO.getEmail());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .msg("Member "+ googleUserDTO.getEmail()+" creation was UnSuccessful")
                            .responseObj(null)
                            .build());
        }

    }
    private UserResponseDTO mapEntityToDTO(GoogleUser n) {
        return UserResponseDTO.builder()
                .memberId(n.getMemberId())
                .userName(n.getUserName())
                .mobileNumber(n.getMobileNumber())
                .email(n.getEmail())
                .userToken(n.getTokenId())
                .isEmailVerified(n.getIsEmailVerified())
                .isMobileNoVerified(n.getIsMobileNoVerified())
                .imageURL(n.getImageURL())
                .build();
    }

    @GetMapping("/userDetails/{memberId}/{emailId}")
    public GlobalResponseDTO getToken(@PathVariable long memberId, @PathVariable String emailId) {
        Optional<GoogleUser> user = userDao.findByMemberIdAndEmail(memberId, emailId);
        if (user.isPresent())
            return GlobalResponseDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .responseObj(mapEntityToDTO(user.get()))
                    .build();
        return  GlobalResponseDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .responseObj(null)
                .build();
    }
}
