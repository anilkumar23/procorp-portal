package com.procorp.chat.dao;

import com.procorp.chat.entities.Community;
import com.procorp.chat.entities.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMemberDao extends JpaRepository<CommunityMember, Long> {

   public CommunityMember findBymemberId(Long memberID);
}
