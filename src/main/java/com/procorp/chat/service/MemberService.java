package com.procorp.chat.service;

import com.procorp.chat.dao.FriendRequestDao;
import com.procorp.chat.dao.MemberDao;
import com.procorp.chat.dtos.FilterDTO;
import com.procorp.chat.dtos.MemberResponseDTO;
import com.procorp.chat.entities.Member;
import com.procorp.chat.dtos.MemberDTO;
import com.procorp.chat.exception.StudentCourseIllegalStateException;
import com.procorp.chat.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final static Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Transactional
    public Long addMember(MemberDTO memberDTO) {
        Member member = Member.builder().fullName(memberDTO.getFullName()).mobileNumber(memberDTO.getMobileNumber())
                .gender(memberDTO.getGender()).email(memberDTO.getEmail()).password(memberDTO.getPassword())
                .dateOfBirth(memberDTO.getDateOfBirth()).registrationDate(memberDTO.getRegistrationDate())
                .schoolName(memberDTO.getEducationDetails().getSchool()).collegeName(memberDTO.getEducationDetails()
                        .getCollege()).companyName(memberDTO.getWorkDetailsDTO().getCompanyName())
                .build();
        memberDao.save(member);
        LOG.info("Student {} Successfully added", member.getMemberId());
        return member.getMemberId();
    }

    @Transactional
    public Member getMemberById(Long memberId) {
        return memberDao.findById(memberId).get();
    }

    @Transactional
    public List<Member> getAllMembers(Long memberId) {
        return memberDao.findAll();
    }

    @Transactional
    public Long updateMember(MemberDTO memberDTO) {
        Member member = Member.builder().fullName(memberDTO.getFullName()).mobileNumber(memberDTO.getMobileNumber())
                .gender(memberDTO.getGender()).email(memberDTO.getEmail()).password(memberDTO.getPassword())
                .dateOfBirth(memberDTO.getDateOfBirth()).registrationDate(memberDTO.getRegistrationDate())
                .schoolName(memberDTO.getEducationDetails().getSchool()).collegeName(memberDTO.getEducationDetails().getCollege())
                .build();
        memberDao.save(member);
        LOG.info("Student {} Successfully updated", member.getMemberId());
        return member.getMemberId();
    }

    @Transactional
    public void removeMember(Long memberId) {
        Optional<Member> member = memberDao.findById(memberId);
        if (!member.isPresent()) {
            throw new StudentCourseIllegalStateException("Failed to remove member. Invalid StudentId :: " + memberId);
        }
        memberDao.delete(member.get());
    }

    @Transactional
    public List<MemberResponseDTO> findMembersWithPartialSearch(FilterDTO filterDTO){
        Optional<Member> member = memberDao.findById(filterDTO.getMemberId());
        if (member.isPresent()) {
            List<Member> members = memberDao.findByFullNameStartsWith(filterDTO.getUserSearchKey());
            ArrayList<Member> tmpList = new ArrayList<>();
            if (members != null && !members.isEmpty()) {
                if (filterDTO.isCollegeName()) {
                    tmpList.addAll(members.stream().filter(n -> !n.getCollegeName().equalsIgnoreCase(member.get().getCollegeName())).toList());
                } else if (filterDTO.isCompanyName()) {
                    tmpList.addAll(members.stream().filter(n -> !n.getCompanyName().equalsIgnoreCase(member.get().getCompanyName())).toList());
                }else if (filterDTO.isSchoolName()) {
                    tmpList.addAll(members.stream().filter(n -> !n.getSchoolName().equalsIgnoreCase(member.get().getSchoolName())).toList());
                }
                members.removeAll(tmpList);
                return mapEntityToDTO(members);
            }
        }
        return new ArrayList<>();
    }

    private List<MemberResponseDTO> mapEntityToDTO(List<Member> members) {
        List<MemberResponseDTO> membersDTO = new ArrayList<>();
        members.forEach(n ->
                membersDTO.add(MemberResponseDTO.builder()
                        .memberId(n.getMemberId())
                        .fullName(n.getFullName())
                        .dateOfBirth(n.getDateOfBirth())
                        .gender(n.getGender())
                        .mobileNumber(n.getMobileNumber())
                        .email(n.getEmail())
                        .password(n.getPassword())
                        .registrationDate(n.getRegistrationDate())
//                        .imageData(n.getImageData())
                        .collegeName(n.getCollegeName())
                        .companyName(n.getCompanyName())
                        .build()));
        return membersDTO;
    }
    @Transactional
    public ResponseEntity<?> uploadImage(MultipartFile file, long memberId) throws IOException {
        Member member = memberDao.getReferenceById(memberId);
        member.setImageData(ImageUtil.compressImage(file.getBytes()));
        memberDao.save(member);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body("Image uploaded successfully");
    }

    @Transactional
    public ResponseEntity<?> getImage(long memberId) {
        Member member = memberDao.getReferenceById(memberId);
        byte[] image = ImageUtil.decompressImage(member.getImageData());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }
}
