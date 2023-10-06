package com.procorp.chat.dao;

import com.procorp.chat.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MemberDao extends JpaRepository<Member, Long> {

    Integer countAllByMemberIdIn(Set<Long> ids);

    default boolean existsMembersAllByMemberId(Set<Long> ids) {
        return countAllByMemberIdIn(ids).equals(ids.size());
    }
//    Optional<Student> findByIds(Long requestFrom, Long requestTo);

}
