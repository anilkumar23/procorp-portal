package com.procorp.authentication.dao;

import com.procorp.authentication.entity.GoogleUser;
import com.procorp.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<GoogleUser, Long> {
    Optional<GoogleUser> findByMemberIdAndEmail(long memberId, String email);
    Optional<GoogleUser> findByEmail(String email);
    Optional<GoogleUser> findByTokenId(String token);
    @Query("SELECT u.tokenId FROM GoogleUser u WHERE u.email = :email")
    public String getTokenByEmail(@Param("email") String email);
}
