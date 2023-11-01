package com.procorp.chat.dao;

import com.procorp.chat.entities.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<GoogleUser, Long> {
    Optional<GoogleUser> findByMemberIdAndEmail(long memberId, String email);
    Optional<GoogleUser> findByEmail(String email);
}
