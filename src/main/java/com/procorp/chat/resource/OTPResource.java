package com.procorp.chat.resource;

import com.procorp.chat.dtos.GlobalResponseDTO;
import com.procorp.chat.dtos.OTPValidationDTO;
import com.procorp.chat.service.OTPService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class OTPResource {

    @Autowired
    OTPService service;

    @PostMapping("/sendMobileOtp")
    public ResponseEntity<?> sendMobileOtp(@RequestBody MemberOtpRequestDTO request){

        if(request==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("input cannot be null")
                            .responseObj("input cannot be null")
                            .build());
           // return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }else if(request.getSource()==null || !StringUtils.hasText(request.getSource())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Phone number cannot be null or empty")
                            .responseObj("Phone number cannot be null or empty")
                            .build());
           // return new ResponseEntity<>("Phone number cannot be null ", HttpStatus.BAD_REQUEST);
        }

        String message = service.sendMobileOtp(request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg(message)
                        .responseObj(message)
                        .build());
        //return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/sendEmailOtp")
    public ResponseEntity<?> sendEmailOtp(@RequestBody MemberOtpRequestDTO request){

        if(request==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("input cannot be null")
                            .responseObj("input cannot be null")
                            .build());
           // return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }else if(request.getSource()==null || !StringUtils.hasText(request.getSource())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Email cannot be null or empty")
                            .responseObj("Email cannot be null or empty")
                            .build());
           // return new ResponseEntity<>("Email cannot be null ", HttpStatus.BAD_REQUEST);
        }

        String message = service.senEmailOtp(request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg(message)
                        .responseObj(message)
                        .build());
       // return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/verifyMobileOtp")
    public ResponseEntity<?> verifyMobileOtp(@RequestBody OTPValidationDTO dto){

        if(dto==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("input cannot be null")
                            .responseObj("input cannot be null")
                            .build());
           // return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }
        if(dto.getOtp()==null || !StringUtils.hasText(dto.getOtp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Mobile OTP cannot be null OR blank")
                            .responseObj("Mobile OTP cannot be null OR blank")
                            .build());
           // return new ResponseEntity<>("Mobile OTP cannot be null OR blank ", HttpStatus.BAD_REQUEST);
        }
        if(dto.getSource()==null || !StringUtils.hasText(dto.getSource())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Mobile Number cannot be null OR blank")
                            .responseObj("Mobile Number cannot be null OR blank")
                            .build());
            // return new ResponseEntity<>("Mobile number cannot be null OR blank ", HttpStatus.BAD_REQUEST);
        }

        return service.verifyMobileOTP(dto);
    }

    @PostMapping("/verifyEmailOtp")
    public ResponseEntity<?> verifyEmailOtp(@RequestBody OTPValidationDTO dto){

        if(dto==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("input cannot be null")
                            .responseObj("input cannot be null")
                            .build());
           // return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }
        if(dto.getOtp()==null || !StringUtils.hasText(dto.getOtp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Email OTP cannot be null OR blank")
                            .responseObj("Email OTP cannot be null OR blank")
                            .build());
           // return new ResponseEntity<>("Email OTP cannot be blank ", HttpStatus.BAD_REQUEST);
        }
        if(dto.getSource()==null || !StringUtils.hasText(dto.getSource())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Email cannot be null OR blank")
                            .responseObj("Email cannot be null OR blank")
                            .build());
           // return new ResponseEntity<>("Email cannot be blank ", HttpStatus.BAD_REQUEST);
        }

        return service.verifyEmailOTP(dto);
    }
}
