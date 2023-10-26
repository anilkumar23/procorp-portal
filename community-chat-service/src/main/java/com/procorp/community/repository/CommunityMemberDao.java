package com.procorp.community.repository;

import com.procorp.community.entities.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMemberDao extends JpaRepository<CommunityMember, Long> {

   public CommunityMember findByMemberId(Long memberID);
   CommunityMember findByCommIdAndMemberId(long communityId, long memberId);
}
