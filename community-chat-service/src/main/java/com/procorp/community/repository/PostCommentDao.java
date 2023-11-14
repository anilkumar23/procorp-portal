package com.procorp.community.repository;

import com.procorp.community.entities.PostComment;
import com.procorp.community.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentDao extends JpaRepository<PostComment, Long> {
    List<PostComment> findByPostId(Long postId);
}
