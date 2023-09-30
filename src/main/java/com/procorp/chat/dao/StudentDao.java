package com.procorp.chat.dao;

import com.procorp.chat.entities.FriendRequest;
import com.procorp.chat.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

    Integer countAllByStudentIdIn(Set<Long> ids);

    default boolean existsMembersAllByStudentId(Set<Long> ids) {
        return countAllByStudentIdIn(ids).equals(ids.size());
    }
//    Optional<Student> findByIds(Long requestFrom, Long requestTo);

}
