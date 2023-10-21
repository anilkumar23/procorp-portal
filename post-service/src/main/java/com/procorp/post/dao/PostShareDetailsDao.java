package com.procorp.post.dao;

import com.procorp.post.entity.PostShareDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

@Repository
public interface PostShareDetailsDao extends JpaRepository<PostShareDetails, Long> {

    ArrayList<PostShareDetails> findByMemberIdIn(Set<Long> memberIds);
}
