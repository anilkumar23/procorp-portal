package com.procorp.post.dao;

import com.procorp.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {
    ArrayList<Post> getByPostOwner(long id);
    Page<ArrayList<Post>> findByPostOwnerIn(Set<Long> ids, Pageable pageable);

}
