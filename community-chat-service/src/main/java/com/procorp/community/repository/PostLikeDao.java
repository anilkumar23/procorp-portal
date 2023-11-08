package com.procorp.community.repository;

import com.procorp.community.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeDao extends JpaRepository<PostLike, Long> {
    PostLike findByPostIdAndMemberId(Long postId, Long memberId);
}
