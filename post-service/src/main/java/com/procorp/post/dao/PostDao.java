package com.procorp.post.dao;

import com.procorp.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {
    ArrayList<Post> getByPostOwner(long id);
    ArrayList<Post> findByPostOwnerIn(Set<Long> ids);

}
