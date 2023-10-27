package com.procorp.community.repository;

import com.procorp.community.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityDao extends JpaRepository<Community, Long> {

}
