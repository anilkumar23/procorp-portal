package com.procorp.chat.resource;

import com.procorp.chat.dtos.FilterDTO;
import com.procorp.chat.dtos.MemberDTO;
import com.procorp.chat.entities.Member;
import com.procorp.chat.service.MemberService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("member-service")
@SecurityRequirement(name = "bearerAuth")
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

    @PutMapping("/member")
    public Long updateStudent(@javax.validation.Valid @RequestBody MemberDTO member) {
        return memberService.updateMember(member);
    }

    @PostMapping(value = "/getAllMembersByPartialSearch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMembersWithPartialSearch(@Valid @RequestBody FilterDTO filterDTO) {
        LOG.info("Fetching members based on following criteria", filterDTO.toString());
        return memberService.findMembersWithPartialSearch(filterDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadImage",
            consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("memberId") long memberId) throws IOException {
        return memberService.uploadImage(file, memberId);
    }
    @GetMapping("/getProfileImage")
    public ResponseEntity<?> getProfileImageById(@RequestParam("memberId") long memberId) {
        return memberService.getImage(memberId);
    }

    @GetMapping("/getSuggestions")
    public ResponseEntity<?> getSuggestions(@RequestParam("memberId") long memberId) {
        return memberService.getSuggestions(memberId);
    }
}

