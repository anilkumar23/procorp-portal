package com.procorp.community.repository;

import com.procorp.community.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CommunityDao extends JpaRepository<Community, Long> {

    Optional<Community> findByCommName(String commName);

    Community findByIdAndMemberId(Long id, Long memberId);
}
