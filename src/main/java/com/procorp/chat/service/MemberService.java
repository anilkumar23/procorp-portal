package com.procorp.chat.service;

import com.procorp.chat.config.FeignClientInterceptor;
import com.procorp.chat.dao.FriendRequestDao;
import com.procorp.chat.dao.MemberDao;
import com.procorp.chat.dao.UserDao;
import com.procorp.chat.dtos.*;
import com.procorp.chat.entities.GoogleUser;
import com.procorp.chat.entities.Member;
import com.procorp.chat.exception.StudentCourseIllegalStateException;
import com.procorp.chat.exception.UnauthorizedException;
import com.procorp.chat.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageUtil util;

    /*@Transactional
    public ResponseEntity<GlobalResponseDTO> addMember(MemberDTO memberDTO) {
        Member member = Member.builder().firstName(memberDTO.getFirstName())
                .lastName(memberDTO.getLastName()).mobileNumber(memberDTO.getMobileNumber())
                .gender(memberDTO.getGender()).email(memberDTO.getEmail()).password(memberDTO.getPassword())
                .dateOfBirth(memberDTO.getDateOfBirth()).registrationDate(memberDTO.getRegistrationDate())
                .schoolName(memberDTO.getEducationDetails().getSchool()).collegeName(memberDTO.getEducationDetails()
                        .getCollege()).companyName(memberDTO.getWorkDetailsDTO().getCompanyName())
                .build();
        try {
            MemberResponseDTO dto = mapEntityToDTO(memberDao.save(member));

            LOG.info("Member {} Successfully created", member.getMemberId());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                             .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Member "+dto.getMemberId()+" Successfully created")
                            .responseObj(dto)
                            .build());
        }catch (Exception ex){
            LOG.info("Member {} creation was unSuccessFull", member.getMemberId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .msg("Member "+member.getMemberId()+" creation was UnSuccessful")
                            .responseObj(null)
                            .build());
        }

    }*/

    @Transactional
    public ResponseEntity<GlobalResponseDTO> getMemberById(Long memberId) {
        Optional<Member> member = memberDao.findById(memberId);
        if (member.isPresent())
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                     .msg("Got the Member object By ID: "+memberId)
                    .responseObj(mapEntityToDTO(member.get()))
                    .build());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .msg("Not found Member object By ID: "+memberId)
                .responseObj(null)
                .build());
    }

    @Transactional
    public List<Member> getAllMembers() {
        return memberDao.findAll();
    }

    @Transactional
    public ResponseEntity<GlobalResponseDTO> updateMember(MemberDTO memberDTO) {
        Optional<GoogleUser> user = userDao.findByMemberIdAndEmail(memberDTO.getMemberId(), memberDTO.getEmail());
        if (user.isPresent()) {
            String userName = memberDTO.getUserName();
            if(!StringUtils.hasText(userName)) userName = user.get().getUserName();
            String imageUrl = "";
            if (StringUtils.hasText(user.get().getImageURL())){
               imageUrl = user.get().getImageURL();
            }else {
                if (StringUtils.hasText(memberDTO.getImageUrl()) && memberDTO.getImageUrl().contains("http"))
                    imageUrl = util.uploadProfileImageToS3(memberDTO.getImageUrl(),memberDTO.getEmail());
            }
            Member member = memberDao.save(Member.builder().memberId(memberDTO.getMemberId()).firstName(memberDTO.getFirstName())
                    .lastName(memberDTO.getLastName()).mobileNumber(memberDTO.getMobileNumber())
                    .gender(memberDTO.getGender()).email(memberDTO.getEmail()).password(memberDTO.getPassword())
                    .dateOfBirth(memberDTO.getDateOfBirth()).registrationDate(memberDTO.getRegistrationDate())
                    .schoolName(memberDTO.getEducationDetails().getSchool()).collegeName(memberDTO.getEducationDetails().getCollege())
                    .isEmailVerified(user.get().getIsEmailVerified()).isMobileNoVerified(user.get().getIsMobileNoVerified())
                    .userName(userName).imageUrl(imageUrl)
                    .build());
            LOG.info("Member {} Successfully updated", member.getMemberId());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Member "+ member.getMemberId()+" Successfully updated")
                            .responseObj(mapEntityToDTO(member))
                            .build());
        }else {
            LOG.info("Member update failed, user "+ memberDTO.getEmail() +" does not exist");
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Member update failed, user "+ memberDTO.getEmail() +" does not exist")
                            .responseObj(null)
                            .build());
        }
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
    public ResponseEntity<?> findMembersWithPartialSearch(FilterDTO filterDTO){
        Optional<Member> member = memberDao.findById(filterDTO.getMemberId());
        if (member.isPresent()) {
            List<Member> members = memberDao.findByFirstNameStartsWith(filterDTO.getUserSearchKey());
            ArrayList<Member> tmpList = new ArrayList<>();
            if (members != null && !members.isEmpty()) {
                if (filterDTO.isCollegeName()) {
                    tmpList.addAll(members.stream().filter(n -> !n.getCollegeName().equalsIgnoreCase(member.get().getCollegeName())).toList());
                } else if (filterDTO.isCompanyName()) {
                    tmpList.addAll(members.stream().filter(n -> !n.getCompanyName().equalsIgnoreCase(member.get().getCompanyName())).toList());
                }else if (filterDTO.isSchoolName()) {
                    tmpList.addAll(members.stream().filter(n -> !n.getSchoolName().equalsIgnoreCase(member.get().getSchoolName())).toList());
                }
                tmpList.add(member.get());
                members.removeAll(tmpList);
                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(GlobalResponseDTO.builder()
                                        .statusCode(HttpStatus.OK.value())
                                        .status(HttpStatus.OK.name())
                                        .responseObj(mapEntityToDTO(members))
                                .msg("got the response ")
                                        .build());
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("Member object was not found with ID: "+filterDTO.getMemberId())
                        .responseObj(null)
                        .build());
    }

    private List<MemberResponseDTO> mapEntityToDTO(List<Member> members) {
        List<MemberResponseDTO> membersDTO = new ArrayList<>();
        members.forEach(n ->
                membersDTO.add(MemberResponseDTO.builder()
                        .memberId(n.getMemberId())
                        .firstName(n.getFirstName())
                        .lastName(n.getLastName())
                        .dateOfBirth(n.getDateOfBirth())
                        .gender(n.getGender())
                        .mobileNumber(n.getMobileNumber())
                        .email(n.getEmail())
                        .password(n.getPassword())
                        .registrationDate(n.getRegistrationDate())
                        .imageURL(n.getImageUrl())
                        .collegeName(n.getCollegeName())
                        .companyName(n.getCompanyName())
                        .build()));
        return membersDTO;
    }
    private MemberResponseDTO mapEntityToDTO(Member n) {
                return MemberResponseDTO.builder()
                        .memberId(n.getMemberId())
                        .firstName(n.getFirstName())
                        .lastName(n.getLastName())
                        .dateOfBirth(n.getDateOfBirth())
                        .gender(n.getGender())
                        .mobileNumber(n.getMobileNumber())
                        .email(n.getEmail())
                        .registrationDate(n.getRegistrationDate())
                        .collegeName(n.getCollegeName())
                        .companyName(n.getCompanyName())
                        .isMobileNoVerified(n.getIsMobileNoVerified())
                        .isEmailVerified(n.getIsEmailVerified())
                        .userName(n.getUserName())
                        .imageURL(n.getImageUrl())
                        .build();
    }
   /* @Transactional
    public ResponseEntity<?> uploadImage(MultipartFile file, long memberId) throws IOException {
        Member member = memberDao.getReferenceById(memberId);
        member.setImageData(ImageUtil.compressImage(file.getBytes()));
        memberDao.save(member);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                *//*.body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Image uploaded successfully with Member ID "+memberId)
                        .responseObj("Image uploaded successfully with Member ID "+memberId)
                        .build());*//*
               .body("Image uploaded successfully");
    }

    @Transactional
    public ResponseEntity<?> getImage(long memberId) {
        Member member = memberDao.getReferenceById(memberId);
        if(member.getImageData()!=null){
            byte[] image = ImageUtil.decompressImage(member.getImageData());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    *//*.body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the uploaded Image successfully with Member ID "+memberId)
                            .responseObj(image)
                            .build());*//*
                    .body(image);
        }else {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                   *//* .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("No image was found with Member ID "+memberId)
                            .responseObj(null)
                            .build());*//*
                    .body("No image was found with Member ID "+memberId);
        }

    }*/

    @Transactional
    public ResponseEntity<?> getSuggestions(long memberId) {
        Optional<Member> optionalMember = memberDao.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            ArrayList<Member> members = memberDao.findByCollegeNameAndSchoolNameAndCompanyName(member.getCollegeName(), member.getSchoolName(), member.getCompanyName());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the member list successfully with Member ID "+memberId)
                            .responseObj(members)
                            .build());
        }
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body();
       // return null;
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("No member list found with Member ID "+memberId)
                        .responseObj(null)
                        .build());
    }

}
