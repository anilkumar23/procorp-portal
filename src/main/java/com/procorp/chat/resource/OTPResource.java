package com.procorp.chat.resource;

import com.procorp.chat.dtos.OTPValidationDTO;
import com.procorp.chat.service.OTPService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class OTPResource {

    @Autowired
    OTPService service;

    @PostMapping("/sendMobileOtp")
    public ResponseEntity<?> sendMobileOtp(@RequestBody MemberOtpRequestDTO request){

        if(request==null){
            return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }else if(!StringUtils.hasText(request.getSource())){
            return new ResponseEntity<>("Phone number cannot be null ", HttpStatus.BAD_REQUEST);
        }

        String message = service.sendMobileOtp(request);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/sendEmailOtp")
    public ResponseEntity<?> sendEmailOtp(@RequestBody MemberOtpRequestDTO request){

        if(request==null){
            return new ResponseEntity<>("input cannot be null ", HttpStatus.BAD_REQUEST);
        }else if(!StringUtils.hasText(request.getSource())){
            return new ResponseEntity<>("Email cannot be null ", HttpStatus.BAD_REQUEST);
        }

        String message = service.senEmailOtp(request);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/verifyMobileOtp")
    public ResponseEntity<?> verifyMobileOtp(@RequestBody OTPValidationDTO dto){

        if(!StringUtils.hasText(dto.getOtp())) {
            return new ResponseEntity<>("Mobile OTP cannot be blank ", HttpStatus.BAD_REQUEST);
        }

        return service.verifyMobileOTP(dto);
    }

    @GetMapping("/verifyEmailOtp")
    public ResponseEntity<?> verifyEmailOtp(@RequestParam OTPValidationDTO dto){

        if(!StringUtils.hasText(dto.getOtp())) {
            return new ResponseEntity<>("Email OTP cannot be blank ", HttpStatus.BAD_REQUEST);
        }

        return service.verifyEmailOTP(dto);
    }
}
