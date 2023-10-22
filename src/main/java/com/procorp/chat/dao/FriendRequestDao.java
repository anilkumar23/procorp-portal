package com.procorp.chat.dao;

import com.procorp.chat.entities.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestDao extends JpaRepository<FriendRequest, Long> {
    public Optional<FriendRequest> findByRequestFromAndRequestTo(Long requestFrom, Long requestTo);

    public List<FriendRequest> findByRequestFrom(Long requestFrom);

    public List<FriendRequest> findByRequestTo(Long requestTo);

    public List<FriendRequest> findByBlockedBy(Long blockedBy);
    List<FriendRequest> findAllByRequestFromOrRequestTo(long requestFrom, long requestTo);

}
