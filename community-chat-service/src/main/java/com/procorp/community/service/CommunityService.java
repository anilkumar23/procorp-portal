package com.procorp.community.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.procorp.community.config.FeignClientInterceptor;
import com.procorp.community.dtos.CommunityDTO;
import com.procorp.community.dtos.MemberResponseDTO;
import com.procorp.community.entities.*;
import com.procorp.community.exception.UnauthorizedException;
import com.procorp.community.repository.CommunityDao;
import com.procorp.community.repository.CommunityMemberDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private CommunityMemberDao communityMemberDao;

//    @Autowired
//    private RestTemplate restTemplate;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private WebClient webClient;

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
    public static <T, F> List<T> convertList(List<F> list, Class<T> clazz) {
        Objects.requireNonNull(clazz);
        ObjectMapper mapper = new ObjectMapper();

        // Important: this must be declared so mapper doesn't throw
        // an exception for all properties which it can't map.
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return Optional.ofNullable(list)
                .orElse(Collections.emptyList())
                .stream()
                .map(obj -> mapper.convertValue(obj, clazz))
                .collect(Collectors.toList());
    }
    private List<MemberResponseDTO> mapEntityToDTO(List<Member> members) {
        List<MemberResponseDTO> membersDTO = new ArrayList<>();
        members.forEach(n ->
                membersDTO.add(MemberResponseDTO.builder()
                        .memberId(n.getMemberId())
                        .firstName(n.getFirstName())
                        .lastName(n.getLastName())
                        .dateOfBirth(n.getDateOfBirth().toString())
                        .gender(n.getGender())
                        .mobileNumber(n.getMobileNumber())
                        .email(n.getEmail())
                        .password(n.getPassword())
                        .registrationDate(n.getRegistrationDate().toString())
                        .imageURL(n.getImageUrl())
                        .collegeName(n.getCollegeName())
                        .companyName(n.getCompanyName())
                        .build()));
        return membersDTO;
    }
    @Autowired
    RestTemplate restTemplate;
    public List<MemberResponseDTO> getCommunityMembersList(Long commId) {
        List<CommunityMember> list = communityMemberDao.findByCommId(commId);
        List<Long> commMemberIds = list.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("accepted"))
                .map(CommunityMember::getMemberId).toList();
        System.out.println("waitingListIds"+commMemberIds);

        String url ="http://lb-1786377629.us-east-1.elb.amazonaws.com:8090/member-service/getAllMembers";
        String token = FeignClientInterceptor.getBearerTokenHeader();
        if (!StringUtils.hasText(token)) throw new UnauthorizedException("Invalid Bearer token or token has expired, please refresh token and give a try!!");
        String[] tokenFields = token.split(" ");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<?> response = restTemplate.exchange(
                    url, HttpMethod.GET,entity,
                    new ParameterizedTypeReference<>() {
                    });
            LinkedHashMap<String, Object> resp = (LinkedHashMap<String, Object>) response.getBody();
            ArrayList list1 = new ArrayList<>(Collections.singleton(resp.get("responseObj")));
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            List<Member> members = mapper.convertValue(list1.get(0), new TypeReference<List<Member>>(){});

            return mapEntityToDTO(members.stream().filter(n->commMemberIds.contains(n.getMemberId())).collect(Collectors.toList()));
        }catch (Exception ex){
            System.out.println(ex);
        }
        return null;
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
