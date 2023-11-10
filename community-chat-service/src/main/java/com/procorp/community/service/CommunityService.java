package com.procorp.community.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.procorp.community.dtos.CommunityDTO;
import com.procorp.community.dtos.CommunityPostRequestDto;
import com.procorp.community.dtos.GlobalResponseDTO;
import com.procorp.community.entities.Community;
import com.procorp.community.entities.CommunityMember;
import com.procorp.community.entities.CommunityPost;
import com.procorp.community.repository.CommunityDao;
import com.procorp.community.repository.CommunityMemberDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private CommunityMemberDao communityMemberDao;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String createCommunity(CommunityDTO communityDto) {
        Optional<Community> communityDetails = communityDao.findByCommName(communityDto.getCommName());
        if(communityDetails.isPresent()) {
            return"community name already exist,Please try with new name";
        } else {
            Community community = new Community();
            community.setMemberId(communityDto.getMemberId());
            community.setCommName(communityDto.getCommName());
            community.setCommDescription(communityDto.getCommDescription());
            community.setObjective(communityDto.getObjective());
            community.setCommunityStatus("pending-for-approval");
            if (communityDto.getCoverPhoto() != null && StringUtils.hasText(communityDto.getCoverPhoto().toString()))
                community.setCoverPhoto(uploadFile(communityDto.getCoverPhoto(), communityDto.getCommName(), communityDto.getMemberId()));
            if (communityDto.getGroupPhoto() != null && StringUtils.hasText(communityDto.getGroupPhoto().toString()))
                community.setGroupPhoto(uploadFile(communityDto.getGroupPhoto(), communityDto.getCommName(), communityDto.getMemberId()));
            Community record = communityDao.save(community);

            // Inserting the member as owner in comm member table
            CommunityMember member = new CommunityMember();
            member.setMemberId(communityDto.getMemberId());
            member.setCommId(record.getId());
            member.setRole("owner");
            member.setStatus("waiting");
            communityMemberDao.save(member);
            return "community created,Admin has to take action";
        }
    }
    public String uploadFile(MultipartFile file, String communityName, long memberId) {
        String s3Url = "";
        try {
        File fileObj = convertMultiPartFileToFile(file);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formattedTimestamp = sdf3.format(timestamp);
        CommunityPost post  = null;
                String fileName = memberId + "_" + communityName + "_" +  formattedTimestamp + ".png";
        PutObjectResult s3response = s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        if (s3response != null && s3response.getMetadata() !=null) {
            s3Url = s3Client.getUrl(bucketName, fileName).toString();
        }
        fileObj.delete();

        }catch (Exception ex){
            log.error("Exception occurred while uploading file to s3");
        }
        return s3Url;
    }
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    public void updateCommunity(Long commId,CommunityDTO communityDto){
        Community community = new Community();
        community.setId(commId);
        community.setMemberId(communityDto.getMemberId());
        community.setCommName(communityDto.getCommName());
        community.setCommDescription(communityDto.getCommDescription());
        if (communityDto.getCoverPhoto() != null && StringUtils.hasText(communityDto.getCoverPhoto().toString()))
            community.setCoverPhoto(uploadFile(communityDto.getCoverPhoto(), communityDto.getCommName(), communityDto.getMemberId()));
        if (communityDto.getGroupPhoto() != null && StringUtils.hasText(communityDto.getGroupPhoto().toString()))
            community.setGroupPhoto(uploadFile(communityDto.getGroupPhoto(), communityDto.getCommName(), communityDto.getMemberId()));
        communityDao.save(community);
    }

    public List<Community> getAllCommunities() {
        return communityDao.findAll();
    }

    public String joinCommunity(Long commId, Long memberId) {
        CommunityMember memberDetails = communityMemberDao.findByCommIdAndMemberId(commId,memberId);
        if(memberDetails != null) {
            return "join request already exist";
        } else {
            CommunityMember member = new CommunityMember();
            member.setMemberId(memberId);
            member.setCommId(commId);
            member.setStatus("waiting");
            member.setRole("follower");
            communityMemberDao.save(member);
            return "community request submitted";
        }
    }

    public String acceptCommunityMemberRequest(Long commId, Long memberId) {
        CommunityMember member = communityMemberDao.findByCommIdAndMemberId(commId,memberId);
        if(member!= null) {
            member.setStatus("accepted");
            member.setRole("member");
            communityMemberDao.save(member);
        } else {
            return "member or community don't exist";
        }
        return "community request accepted";
    }

    public List<CommunityMember> getCommunityMembersList(Long commId) {
//        List<CommunityMember> list = communityMemberDao.findByCommId(commId);
        //        return list.stream()
//                .filter(r -> r.getStatus().equalsIgnoreCase("accepted"))
//                .collect(Collectors.toList());
        return communityMemberDao.findByCommId(commId);
    }

    public List<CommunityMember> getCommunityMembersRequests(Long commId) {
        List<CommunityMember> list = communityMemberDao.findByCommId(commId);
        return list.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("waiting"))
                .collect(Collectors.toList());
    }

    public String approveCommunity(Long commId) {
        Optional<Community> community = communityDao.findById(commId);
        if(community.isPresent()) {
            community.get().setCommunityStatus("approved-by-admin");
            communityDao.save(community.get());
            CommunityMember member = communityMemberDao.findByCommIdAndMemberId(commId, community.get().getMemberId());
            if (member != null) {
                member.setStatus("approved");
                communityMemberDao.save(member);
                return "community request approved";
            }
        }
        return "community or member is invalid";
    }

    public String rejectCommunityJoinRequest(Long commId, Long memberId) {
        CommunityMember member = communityMemberDao.findByCommIdAndMemberId(commId,memberId);
        if(member!= null && !member.getStatus().equals("accepted")) {
            communityMemberDao.delete(member);
            return "rejected the request";
        }
        return "Request not found";
    }

    public String rejectCommunityCreationRequest(Long commId) {
        Optional<Community> community = communityDao.findById(commId);
        if(community.isPresent()) {
            communityDao.delete(community.get());
            return "rejected the request";
        }
        return "Request not found";

    }

    public String inviteMemberToCommunityRequest(Long commId, Long memberId) {
        CommunityMember isRequestExist = communityMemberDao.findByCommIdAndMemberId(commId,memberId);

        if(isRequestExist != null) {
            return "invitation already sent ";
        } else {
            CommunityMember member = new CommunityMember();
            member.setMemberId(memberId);
            member.setCommId(commId);
            member.setStatus("invited");
            communityMemberDao.save(member);
            return "Invitation sent to the member";
        }
    }

    public Community getCommunityById(Long commId) {
        Optional<Community> commDetails = communityDao.findById(commId);
        return commDetails.orElse(null);
    }

    public List<Community> getCommunityCreationRequestsList() {
        List<Community> commList = communityDao.findAll();
        return commList.stream()
                .filter(r -> r.getCommunityStatus().equalsIgnoreCase("pending-for-approval"))
                .collect(Collectors.toList());
    }

    public List<Community> getCommunitiesByRole(Long memberId, String role) {
        List<Long> communityIds = communityMemberDao.findByMemberIdAndRole(memberId, role.toLowerCase())
                .stream().map(CommunityMember::getCommId).toList();

        List<Community> commList = communityDao.findAllById(communityIds);
        return commList;
    }
}
