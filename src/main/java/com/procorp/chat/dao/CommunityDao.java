package com.procorp.chat.dao;

import com.procorp.chat.entities.Community;
import com.procorp.chat.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommunityDao extends JpaRepository<Community, Long> {

}
