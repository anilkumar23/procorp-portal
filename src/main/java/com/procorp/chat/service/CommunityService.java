//package com.procorp.chat.service;
//
//import com.procorp.chat.dao.CommunityDao;
//import com.procorp.chat.dao.CommunityMemberDao;
//import com.procorp.chat.dtos.CommunityDTO;
//import com.procorp.chat.entities.Community;
//import com.procorp.chat.entities.CommunityMember;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class CommunityService {
//
//    @Autowired
//    private CommunityDao communityDao;
//
//    @Autowired
//    private CommunityMemberDao communityMemberDao;
//
//    public String createCommunity(CommunityDTO communityDto) throws Exception {
//        Optional<Community> communityDetails = communityDao.findByCommName(communityDto.getCommName());
//        if(communityDetails.isPresent()) {
//            return"community name already exist,Please try with new name";
//        } else {
//            Community community = new Community();
//            community.setMemberId(communityDto.getMemberId());
//            community.setCommName(communityDto.getCommName());
//            community.setCommDescription(communityDto.getCommDescription());
//            community.setCommunityStatus("pending-for-approval");
//
//            Community record = communityDao.save(community);
//
//            // Inserting the member as owner in comm member table
//            CommunityMember member = new CommunityMember();
//            member.setMemberId(communityDto.getMemberId());
//            member.setCommId(record.getId());
//            member.setRole("owner");
//            member.setStatus("waiting");
//            communityMemberDao.save(member);
//            return "community created,Admin has to take action";
//        }
//     }
//
//
//    public void updateCommunity(Long commId,CommunityDTO communityDto){
//        Community community = new Community();
//        community.setId(commId);
//        community.setMemberId(communityDto.getMemberId());
//        community.setCommName(communityDto.getCommName());
//        community.setCommDescription(communityDto.getCommDescription());
//        communityDao.save(community);
//    }
//
//    public List<Community> getAllCommunities() {
//        return communityDao.findAll();
//    }
//
//    public String joinCommunity(Long commId, Long memberId) {
//        CommunityMember memberDetails = communityMemberDao.findByCommIdAndMemberId(commId,memberId);
//        if(memberDetails != null) {
//            return "join request already exist";
//        } else {
//            CommunityMember member = new CommunityMember();
//            member.setMemberId(memberId);
//            member.setCommId(commId);
//            member.setStatus("waiting");
//            member.setRole("member");
//            communityMemberDao.save(member);
//            return "community request submitted";
//        }
//    }
//
//    public String acceptCommunityJoinRequest(Long commId, Long memberId) {
//        CommunityMember member = communityMemberDao.findByCommIdAndMemberId(commId,memberId);
//        if(member!= null) {
//            member.setStatus("accepted");
//            communityMemberDao.save(member);
//        } else {
//            return "member or community don't exist";
//        }
//        return "community request accepted";
//    }
//
//    public List<CommunityMember> getCommunityMembersList(Long commId) {
//       List<CommunityMember> list = communityMemberDao.findByCommId(commId);
//        return list.stream()
//                .filter(r -> r.getStatus().equalsIgnoreCase("accepted"))
//                                    .collect(Collectors.toList());
//    }
//
//    public List<CommunityMember> getCommunityMembersRequests(Long commId) {
//        List<CommunityMember> list = communityMemberDao.findByCommId(commId);
//        return list.stream()
//                .filter(r -> r.getStatus().equalsIgnoreCase("waiting"))
//                .collect(Collectors.toList());
//    }
//
//    public String approveCommunity(Long commId, Long memberId) {
//        Optional<Community> community = communityDao.findById(commId);
//        if(community.isPresent()) {
//            community.get().setCommunityStatus("approved-by-admin");
//            communityDao.save(community.get());
//            CommunityMember member = communityMemberDao.findByCommIdAndMemberId(commId, memberId);
//            if (member != null) {
//                member.setStatus("approved");
//                communityMemberDao.save(member);
//                return "community request approved";
//            }
//        }
//        return "community or member is invalid";
//    }
//
//    public String rejectCommunityJoinRequest(Long commId, Long memberId) {
//        CommunityMember member = communityMemberDao.findByCommIdAndMemberId(commId,memberId);
//        if(member!= null && !member.getStatus().equals("accepted")) {
//            communityMemberDao.delete(member);
//            return "rejected the request";
//        }
//        return "Request not found";
//    }
//
//    public String rejectCommunityCreationRequest(Long commId) {
//        Optional<Community> community = communityDao.findById(commId);
//        if(community.isPresent()) {
//            communityDao.delete(community.get());
//            return "rejected the request";
//        }
//      return "Request not found";
//
//    }
//
//    public String inviteMemberToCommunityRequest(Long commId, Long memberId) {
//        CommunityMember isRequestExist = communityMemberDao.findByCommIdAndMemberId(commId,memberId);
//
//        if(isRequestExist != null) {
//            return "invitation already sent ";
//        } else {
//            CommunityMember member = new CommunityMember();
//            member.setMemberId(memberId);
//            member.setCommId(commId);
//            member.setStatus("invited");
//            communityMemberDao.save(member);
//            return "Invitation sent to the member";
//        }
//    }
//
//    public Community getCommunityById(Long commId) {
//        Optional<Community> commDetails = communityDao.findById(commId);
//        return commDetails.orElse(null);
//    }
//
//    public List<Community> getCommunityCreationRequestsList() {
//        List<Community> commList = communityDao.findAll();
//        return commList.stream()
//                .filter(r -> r.getCommunityStatus().equalsIgnoreCase("waiting"))
//                .collect(Collectors.toList());
//    }
//}
