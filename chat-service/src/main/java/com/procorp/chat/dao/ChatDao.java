package com.procorp.chat.dao;

import com.procorp.chat.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatDao extends JpaRepository<Chat, String> {
}
