package com.procorp.community.repository;

import com.procorp.community.entities.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMemberDao extends JpaRepository<CommunityMember, Long> {

   public CommunityMember findBymemberId(Long memberID);

   public CommunityMember findByCommIdAndMemberId(Long commId,Long memberID);

   List<CommunityMember> findByCommId(Long commId);
   List<CommunityMember> findByMemberIdAndRole(long memberId, String role);

}
