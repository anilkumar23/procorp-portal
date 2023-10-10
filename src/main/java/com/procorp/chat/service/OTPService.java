package com.procorp.chat.service;

import com.procorp.chat.dao.OTPDao;
import com.procorp.chat.dtos.OTPStatus;
import com.procorp.chat.dtos.OTPValidationDTO;
import com.procorp.chat.entities.OTPDetails;
import com.procorp.chat.resource.MemberOtpRequestDTO;
import com.procorp.chat.util.RandomOtpGenerator;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;


@Service
public class OTPService {
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String sendermail;

    @Value("${twillo.appkey}")
    private String appkey;

    @Value("${twillo.appsecret}")
    private String appsecret;

    @Value("${twillo.fromnumber}")
    private String twilloNumber;

    @Value("${otp.expirationInMs}")
    private long otpThreshold;

    @Autowired
    private OTPDao dao;

    @Transactional
    public String sendMobileOtp(MemberOtpRequestDTO request){
        try{
            dao.deleteByMobileNo(request.getSource());
                String phoneotp = RandomOtpGenerator.generateOtp();
                sendSMS(request, phoneotp);

                OTPDetails otpDetails = OTPDetails.builder()
                        .mobileNo(request.getSource())
                        .mobileOTP(phoneotp)
                        .mobileOtpExpiry(System.currentTimeMillis())
                        .build();

                dao.save(otpDetails);
                return "Otp sent successfully";
        }catch (Exception e){
            return "Error while Sending Mail : "+e.getMessage();
        }
    }

    @Transactional
    public String senEmailOtp(MemberOtpRequestDTO request){
        try{
            dao.deleteByEmail(request.getSource());
            String emailotp= RandomOtpGenerator.generateOtp();

            sendMail(request,emailotp);

            OTPDetails otpDetails = OTPDetails.builder()
                    .email(request.getSource())
                    .emailOTP(emailotp)
                    .emailOtpExpiry(System.currentTimeMillis())
                    .build();
            dao.save(otpDetails);

            return "Otp sent successfully";
        }catch (Exception e){
            return "Error while Sending Mail : "+e.getMessage();
        }
    }

    @Transactional
    public ResponseEntity<OTPStatus> verifyMobileOTP(OTPValidationDTO dto){
        OTPDetails details = dao.getByMobileNoAndMobileOTP(dto.getSource(), dto.getOtp());
        return getOtpStatusResponseEntity(details);
    }

    @Transactional
    public ResponseEntity<OTPStatus> verifyEmailOTP(OTPValidationDTO dto){
        OTPDetails details = dao.getByEmailAndEmailOTP(dto.getSource(), dto.getOtp());
        return getOtpStatusResponseEntity(details);
    }

    private ResponseEntity<OTPStatus> getOtpStatusResponseEntity(OTPDetails details) {
        if(Objects.nonNull(details)){
            if(System.currentTimeMillis() - details.getMobileOtpExpiry() > otpThreshold){
                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new OTPStatus(OTPStatus.isExpired.YES, "OTP Got Expired, please generate new one."));
            }
            dao.deleteById(details.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new OTPStatus(OTPStatus.isExpired.NO, "Success"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new OTPStatus(OTPStatus.isExpired.INVALID, "Not a valid OTP"));
    }

    public String sendMail(MemberOtpRequestDTO request, String otp){

        try{

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(sendermail);
            message.setTo(request.getSource());
            message.setSubject("OTP for registration");
            message.setText("Hello User! This is Your OTP: " +otp+" for your registration");

            sender.send(message);

            return "Otp sent successfully";
        }catch (Exception e){
            return "Error while Sending Mail : "+e.getMessage();
        }
    }

    public String sendSMS(MemberOtpRequestDTO request, String otp){

        try{

            Twilio.init(appkey, appsecret);

            // +91 is mandatory to check region
            PhoneNumber recieverPhoneNumber = new PhoneNumber(request.getSource());
            PhoneNumber senderTwilloPhoneNumber = new PhoneNumber(twilloNumber);

            MessageCreator creator = com.twilio.rest.api.v2010.account.Message.creator(recieverPhoneNumber, senderTwilloPhoneNumber, "Hello from procorp!! please find your otp here "+otp);
            Message creator1 = creator.create();

            return "Otp sent successfully";
        }catch (Exception e){
            return "Error while Sending Otp : "+e.getMessage();
        }
    }

}
