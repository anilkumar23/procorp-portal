package com.procorp.community.repository;

import com.procorp.community.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeDao extends JpaRepository<PostLike, Long> {
    PostLike findByPostIdAndMemberId(Long postId, Long memberId);

    List<PostLike> findByPostId(Long postId);

}
