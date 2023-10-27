package com.procorp.community.repository;

import com.procorp.community.entities.CommunityChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityChatDao extends JpaRepository<CommunityChat, String> {

}
