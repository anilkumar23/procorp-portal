package com.procorp.chat.resource;

import com.procorp.chat.dtos.FilterDTO;
import com.procorp.chat.dtos.GlobalResponseDTO;
import com.procorp.chat.dtos.MemberDTO;
import com.procorp.chat.entities.Member;
import com.procorp.chat.service.MemberService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("member-service")
@SecurityRequirement(name = "bearerAuth")
public class MemberRegistrationResource {

    private final static Logger LOG = LoggerFactory.getLogger(MemberRegistrationResource.class);

    @Autowired
    private MemberService memberService;

  /*  @PostMapping("/member")
    public ResponseEntity<GlobalResponseDTO> addStudent(@Valid @RequestBody MemberDTO member) {
        LOG.info("member :: member Name {} {}", member.getFirstName(), member.getLastName());
        return memberService.addMember(member);
    }*/

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<GlobalResponseDTO> removeStudent(Long memberId) {
        memberService.removeMember(memberId);
        LOG.info("member with Id:" + memberId + " has been removed.");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("member with Id:" + memberId + " has been removed.")
                        .responseObj("member with Id:" + memberId + " has been removed.")
                        .build());
      //  return "member with Id:" + memberId + " has been removed.";
    }

    @GetMapping("/getMemberById/{memberId}")
    public ResponseEntity<GlobalResponseDTO> getMemberById(@PathVariable Long memberId) {
        return memberService.getMemberById(memberId);
    }

    @GetMapping("/getAllMembers")
    public ResponseEntity<?> getAllMembers() {
        LOG.info("called getAllMembers");
        List<Member> memberList = memberService.getAllMembers();
        if(memberList!=null && !memberList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got all members list :" )
                            .responseObj(memberList)
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("members list :")
                            .responseObj(new ArrayList<>())
                            .build());
        }

      //  return memberService.getAllMembers(memberId);

    }

    @PutMapping("/member")
    public  ResponseEntity<?> updateMember(@javax.validation.Valid @RequestBody MemberDTO member) {
       /* return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Successfully updated the member object")
                        .responseObj(memberService.updateMember(member))
                        .build());*/
        return memberService.updateMember(member);
    }

    @PostMapping(value = "/getAllMembersByPartialSearch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMembersWithPartialSearch(@Valid @RequestBody FilterDTO filterDTO) {
        LOG.info("Fetching members based on following criteria", filterDTO.toString());
        return memberService.findMembersWithPartialSearch(filterDTO);
    }

    /**
     * Commented because added this logic as part of initial registration at
     * @param memberId
     * @return
     */
   /* @RequestMapping(method = RequestMethod.POST, value = "/uploadImage",
            consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("memberId") long memberId) throws IOException {
        return memberService.uploadImage(file, memberId);
    }
    @GetMapping("/getProfileImage")
    public ResponseEntity<?> getProfileImageById(@RequestParam("memberId") long memberId) {
        return memberService.getImage(memberId);
    }*/

    @GetMapping("/getSuggestions")
    public ResponseEntity<?> getSuggestions(@RequestParam("memberId") long memberId) {
        return memberService.getSuggestions(memberId);
    }
}

