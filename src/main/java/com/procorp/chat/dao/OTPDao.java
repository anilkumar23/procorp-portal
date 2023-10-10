package com.procorp.chat.dao;

import com.procorp.chat.entities.OTPDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPDao extends JpaRepository<OTPDetails, Integer> {
    OTPDetails getByEmail(String email);
    OTPDetails getByMobileNo(String mobileNo);
    void deleteByEmail(String email);
    void deleteByMobileNo(String mobileNo);
    OTPDetails getByEmailAndEmailOTP(String email, String otp);
    OTPDetails getByMobileNoAndMobileOTP(String mobileNo, String otp);
}
