package com.procorp.community.repository;

import com.procorp.community.entities.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface CommunityPostDao extends JpaRepository<CommunityPost, Long> {
    ArrayList<CommunityPost> getByPostOwner(long id);
    Page<CommunityPost> findByCommunityId(long communityId, Pageable pageable);
    List<CommunityPost> findByCommunityId(long communityId, Sort sort);
}
