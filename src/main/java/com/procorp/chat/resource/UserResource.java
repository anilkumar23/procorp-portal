package com.procorp.chat.resource;

import com.procorp.chat.dao.UserDao;
import com.procorp.chat.dtos.GlobalResponseDTO;
import com.procorp.chat.dtos.GoogleUserDTO;
import com.procorp.chat.dtos.MemberDTO;
import com.procorp.chat.dtos.MemberResponseDTO;
import com.procorp.chat.entities.GoogleUser;
import com.procorp.chat.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/addUser")
    public ResponseEntity<GlobalResponseDTO> addUser(@RequestBody GoogleUserDTO googleUserDTO) {

        Mono<Object> flag = webClient.get().uri(authServiceBaseUrl+"/generateAuthToken?email=" + googleUserDTO.getEmail())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Object>() {});
        String token = Objects.requireNonNull(flag.block()).toString().replace("{","")
                .replace("}","").replace("token=","");


        GoogleUser googleUser = GoogleUser.builder().userName(googleUserDTO.getUserName()).mobileNumber(googleUserDTO.getMobileNumber())
                .email(googleUserDTO.getEmail()).tokenId(token).isEmailVerified(googleUserDTO.isEmailVerified())
                .isMobileNoVerified(googleUserDTO.isMobileVerified()).uuid(googleUserDTO.getUuid())
                .imageURL(googleUserDTO.getImageURL()).build();
        try {
            userDao.save(googleUser);

            LOG.info("Member {} Successfully created", googleUser.getMemberId());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Member "+googleUser.getMemberId()+" Successfully created")
                            .responseObj(googleUser)
                            .build());
        }catch (Exception ex){
            LOG.info("Member {} creation was unSuccessFull", googleUser.getMemberId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .msg("Member "+ googleUser.getMemberId()+" creation was UnSuccessful")
                            .responseObj(null)
                            .build());
        }

    }
    private MemberResponseDTO mapEntityToDTO(GoogleUser n) {
        return MemberResponseDTO.builder()
                .memberId(n.getMemberId())
                .userName(n.getUserName())
                .mobileNumber(n.getMobileNumber())
                .email(n.getEmail())
                .userToken(n.getTokenId())
                .isEmailVerified(n.getIsEmailVerified())
                .isMobileNoVerified(n.getIsMobileNoVerified())
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
