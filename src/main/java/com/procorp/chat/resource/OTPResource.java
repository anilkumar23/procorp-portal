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
            return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }else if(request.getSource()==null || !StringUtils.hasText(request.getSource())){
            return new ResponseEntity<>("Phone number cannot be null ", HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }else if(request.getSource()==null || !StringUtils.hasText(request.getSource())){
            return new ResponseEntity<>("Email cannot be null ", HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }
        if(dto.getOtp()==null || !StringUtils.hasText(dto.getOtp())) {
            return new ResponseEntity<>("Mobile OTP cannot be null OR blank ", HttpStatus.BAD_REQUEST);
        }
        if(dto.getSource()==null || !StringUtils.hasText(dto.getSource())) {
            return new ResponseEntity<>("Mobile number cannot be null OR blank ", HttpStatus.BAD_REQUEST);
        }

        return service.verifyMobileOTP(dto);
    }

    @GetMapping("/verifyEmailOtp")
    public ResponseEntity<?> verifyEmailOtp(@RequestParam OTPValidationDTO dto){

        if(dto==null) {
            return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }
        if(dto.getOtp()==null || !StringUtils.hasText(dto.getOtp())) {
            return new ResponseEntity<>("Email OTP cannot be blank ", HttpStatus.BAD_REQUEST);
        }
        if(dto.getSource()==null || !StringUtils.hasText(dto.getSource())) {
            return new ResponseEntity<>("Email cannot be blank ", HttpStatus.BAD_REQUEST);
        }

        return service.verifyEmailOTP(dto);
    }
}
