package com.procorp.chat.service;

import com.procorp.chat.dao.FriendRequestDao;
import com.procorp.chat.dao.MemberDao;
import com.procorp.chat.entities.Member;
import com.procorp.chat.dtos.MemberDTO;
import com.procorp.chat.exception.StudentCourseIllegalStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final static Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    public Long addMember(MemberDTO memberDTO) {
        Member member = new Member(memberDTO.getFullName(), memberDTO.getMobileNumber(), memberDTO.getGender(), memberDTO.getEmail(), memberDTO.getPassword(),memberDTO.getDateOfBirth(),memberDTO.getRegistrationDate());
        memberDao.save(member);
        LOG.info("Student {} Successfully added", member.getMemberId());
        return member.getMemberId();
    }

    public Member getMemberById(Long memberId) {
        return memberDao.findById(memberId).get();
    }

    public List<Member> getAllMembers(Long memberId) {
//        List<FriendRequest> request = null;
//        if(friendRequestDao.findByRequestFrom(memberId).isEmpty()) {
//            request = friendRequestDao.findByRequestFrom(memberId);
//            request = request.stream().filter(r -> r.getFriendRequestStatus().equals("blocked"))
//                    .collect(Collectors.toList());
//            request.get
//        } else if(friendRequestDao.findByRequestTo(memberId).isEmpty()) {
//            request = friendRequestDao.findByRequestTo(memberId);
//            request = request.stream().filter(r -> r.getFriendRequestStatus().equals("blocked"))
//                    .collect(Collectors.toList());
//        }
        return memberDao.findAll();
    }

    public Long updateMember(MemberDTO memberDTO) {
        Member member = new Member(memberDTO.getFullName(), memberDTO.getMobileNumber(), memberDTO.getGender(), memberDTO.getEmail(), memberDTO.getPassword(),memberDTO.getDateOfBirth(),memberDTO.getRegistrationDate());
        memberDao.save(member);
        LOG.info("Student {} Successfully updated", member.getMemberId());
        return member.getMemberId();
    }

    public void removeMember(Long memberId) {
        Optional<Member> member = memberDao.findById(memberId);
        if (!member.isPresent()) {
            throw new StudentCourseIllegalStateException("Failed to remove member. Invalid StudentId :: " + memberId);
        }
        memberDao.delete(member.get());
    }

}
