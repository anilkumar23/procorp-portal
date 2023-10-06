package com.procorp.chat.resource;

import com.procorp.chat.dtos.MemberDTO;
import com.procorp.chat.entities.Member;
import com.procorp.chat.service.MemberService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("member-service")
public class MemberRegistrationResource {
    private final static Logger LOG = LoggerFactory.getLogger(MemberRegistrationResource.class);
    @Autowired
    private MemberService memberService;

    @PostMapping("/member")
    public String addStudent(@Valid @RequestBody MemberDTO member) {
        LOG.info("member :: member Name {}", member.getFullName());
        memberService.addMember(member);
        return "member with Name:" + member.getFullName() + " has been Added.";
    }


    @DeleteMapping("/member/{memberId}")
    public String removeStudent(Long memberId) {
        memberService.removeMember(memberId);
        return "member with Id:" + memberId + " has been removed.";
    }

    @GetMapping("/getMemberById/{memberId}")
    public Member getMemberById(@PathVariable Long memberId) {
        return memberService.getMemberById(memberId);
    }

    @GetMapping("/getAllMembers")
    public List<Member> getAllStudents(Long memberId) {
        return memberService.getAllMembers(memberId);

    }

//	@PutMapping("/student")
//	public List<Student> updateStudent(@Valid @RequestBody Student student) {
//		return studentService.updateStudent();
//	}

    @PutMapping("/member")
    public Long updateStudent(@javax.validation.Valid @RequestBody MemberDTO member) {
        return memberService.updateMember(member);
    }
}

