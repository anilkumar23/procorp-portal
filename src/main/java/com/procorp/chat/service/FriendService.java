package com.procorp.chat.service;

import com.procorp.chat.dao.FriendRequestDao;
import com.procorp.chat.dao.MemberDao;
import com.procorp.chat.dtos.FriendRequestDTO;
import com.procorp.chat.dtos.GlobalResponseDTO;
import com.procorp.chat.entities.FriendRequest;
import com.procorp.chat.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final static Logger LOG = LoggerFactory.getLogger(FriendService.class);

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Autowired
    private MemberDao memberDao;

    public ResponseEntity<?> sendFriendRequest(Long requestFrom, Long requestTo) {
        Set<Long> ids = new HashSet<>();
        ids.add(requestFrom);
        ids.add(requestTo);
        if(!memberDao.existsMembersAllByMemberId(ids)) {

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Member IDS sent doesn't exist ")
                            .responseObj("Member IDS sent doesn't exist")
                            .build());
            //return "Member IDS sent doesn't exist";
        }

        //This check is to make sure either of the members dont send request when there is already request raised
        Optional<FriendRequest> requestDetails1 = friendRequestDao.findByRequestFromAndRequestTo(requestFrom,requestTo);
        Optional<FriendRequest> requestDetails2 = friendRequestDao.findByRequestFromAndRequestTo(requestTo,requestFrom);

        if (requestDetails1.isPresent() | requestDetails2.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Friend's request already exist")
                            .responseObj("Friend's request already exist")
                            .build());
           // return "Friend's request already exist";
        }
        FriendRequest request = new FriendRequest();
        request.setRequestFrom(requestFrom);
        request.setRequestTo(requestTo);
        request.setFriendRequestStatus("waiting");
        friendRequestDao.save(request);
        LOG.info("request sent");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("request sent,Waiting for approval")
                        .responseObj("request sent,Waiting for approval")
                        .build());
        //return "request sent,Waiting for approval";
    }

    public ResponseEntity<?> getFriendRequestsSent(Long requestFrom) {
       List<FriendRequest> friendRequests = friendRequestDao.findByRequestFrom(requestFrom);
       if(friendRequests!=null && !friendRequests.isEmpty()){
           return ResponseEntity.status(HttpStatus.OK)
                   .contentType(MediaType.APPLICATION_JSON)
                   .body(GlobalResponseDTO.builder()
                           .statusCode(HttpStatus.OK.value())
                           .status(HttpStatus.OK.name())
                           .msg("Got the list of friend requests sent from "+requestFrom)
                           .responseObj(mapEntityToDTO(friendRequests))
                           .build());
       }else{
           return ResponseEntity.status(HttpStatus.OK)
                   .contentType(MediaType.APPLICATION_JSON)
                   .body(GlobalResponseDTO.builder()
                           .statusCode(HttpStatus.NO_CONTENT.value())
                           .status(HttpStatus.NO_CONTENT.name())
                           .msg("friend requests sent from "+requestFrom+ " are empty")
                           .responseObj(new ArrayList<>())
                           .build());
       }

       // return mapEntityToDTO(friendRequests);
    }

    public ResponseEntity<?> getFriendRequests(Long requestTo) {
        List<FriendRequest> friendRequestList =
                friendRequestDao.findByRequestTo(requestTo);
        if(friendRequestList!=null && !friendRequestList.isEmpty()){
            List<Long> waitingListIds = friendRequestList.stream()
                    .filter(r -> r.getFriendRequestStatus().equalsIgnoreCase("waiting"))
                    .map(FriendRequest::getRequestFrom).toList();
//            System.out.println("waitingListIds"+waitingListIds);
            List<Member> requestList = memberDao.findAllById(waitingListIds);
//            System.out.println("requestList"+requestList);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the list of friend requests of member ID "+requestTo)
                            .responseObj(mapEntityToMemberDTO(requestList))
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("friend requests are empty with member ID "+requestTo)
                            .responseObj(new ArrayList<>())
                            .build());
        }

        //return mapEntityToDTO(approvedList);
    }


    public ResponseEntity<?> getFriendsList(Long requestTo) {
        List<FriendRequest> friendRequestList =
                friendRequestDao.findByRequestTo(requestTo);
        if(friendRequestList!=null && !friendRequestList.isEmpty()){
            List<Long> waitingListIds = friendRequestList.stream()
                    .filter(r -> r.getFriendRequestStatus().equalsIgnoreCase("accepted"))
                    .map(FriendRequest::getRequestFrom).toList();
//            System.out.println("waitingListIds"+waitingListIds);
            List<Member> requestList = memberDao.findAllById(waitingListIds);
//            System.out.println("requestList"+requestList);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the list of friends of member ID "+requestTo)
                            .responseObj(mapEntityToMemberDTO(requestList))
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("friend List are empty with member ID "+requestTo)
                            .responseObj(new ArrayList<>())
                            .build());
        }

        //return mapEntityToDTO(approvedList);
    }

    private List<Member> mapEntityToMemberDTO(List<Member> memberList){
        List<Member> memberDTOList = new ArrayList<>();
        memberList.forEach(n-> memberDTOList.add(new Member(n.getMemberId(), n.getEmail(),
                n.getImageData())));
        return memberDTOList;
    }
    private List<FriendRequestDTO> mapEntityToDTO(List<FriendRequest> friendRequests){
        List<FriendRequestDTO> friendRequestDTOList = new ArrayList<>();
        friendRequests.forEach(n-> friendRequestDTOList.add(new FriendRequestDTO(n.getId(), n.getRequestFrom(),
                n.getRequestTo(), n.getFriendRequestStatus(), n.getBlockedBy())));
        return friendRequestDTOList;
    }
    public ResponseEntity<?> getFriendRequestsReceived(Long requestTo) {
        List<FriendRequest> friendRequests =  friendRequestDao.findByRequestTo(requestTo);
        if(friendRequests!=null && !friendRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the list of friend requests received for member ID " + requestTo)
                            .responseObj(mapEntityToDTO(friendRequests))
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("friend requests received are empty with member ID "+requestTo)
                            .responseObj(new ArrayList<>())
                            .build());
        }
       // return mapEntityToDTO(friendRequests);
    }

    public ResponseEntity<?> cancelFriendRequest(Long requestFrom, Long requestTo) {
        Optional<FriendRequest> requestDetails = friendRequestDao.findByRequestFromAndRequestTo(requestFrom,requestTo);
        System.out.println(requestDetails);
        if (requestDetails.isEmpty()) {
//            throw new FindException("Failed to cancel request. Invalid requestFrom :: " + requestFrom);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Friend's request doesn't exist")
                            .responseObj("Friend's request doesn't exist")
                            .build());
            //  return "Friend's request doesn't exist";
        }
        friendRequestDao.delete(requestDetails.get());
        LOG.info("Deleting record will cancel teh request");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Friend's request cancelled")
                        .responseObj("Friend's request cancelled")
                        .build());
       // return "Friend's request cancelled";

        }

    public ResponseEntity<?> acceptFriendRequest(Long requestFrom, Long requestTo) {
        Optional<FriendRequest> requestDetails = friendRequestDao.findByRequestFromAndRequestTo(requestFrom,requestTo);
        LOG.info("Abhi"+String.valueOf(requestDetails));
        if (requestDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Friend's request doesn't exist")
                            .responseObj("Friend's request doesn't exist")
                            .build());
          //  return "Friend's request doesn't exist";
//            throw new FindException("Failed to cancel request. Invalid requestFrom :: " + requestFrom);
        }
        if(requestDetails.get().getFriendRequestStatus().equalsIgnoreCase("Waiting")) {
            requestDetails.get().setFriendRequestStatus("Accepted");
//            FriendRequest acceptRequest = new FriendRequest(requestDetails.get().getRequestFrom(),requestDetails.get().getRequestTo(),"Accepted",null);
            friendRequestDao.save(requestDetails.get());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Friend's request Accepted")
                            .responseObj("Friend's request Accepted")
                            .build());
           // return "Friend's request Accepted";
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Member : "+requestFrom+" is already a Friend of "+"Member : "+requestTo)
                        .responseObj("Member : "+requestFrom+" is already a Friend of "+"Member : "+requestTo)
                        .build());
       // return "Member : "+requestFrom+" is already a Friend of "+"Member : "+requestTo;
    }

    public ResponseEntity<?> blockFriend(Long requestFrom, Long requestTo) {
        Set<Long> memberIds = new HashSet<>();
        memberIds.add(requestFrom);
        memberIds.add(requestTo);
        if(!memberDao.existsMembersAllByMemberId(memberIds)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Member ID doesn't exist")
                            .responseObj("Member ID doesn't exist")
                            .build());
            //return "Member ID doesn't exist";
        }

        Optional<FriendRequest> requestDetails1 = friendRequestDao.findByRequestFromAndRequestTo(requestFrom,requestTo);
        Optional<FriendRequest> requestDetails2 = friendRequestDao.findByRequestFromAndRequestTo(requestTo,requestFrom);
        System.out.println("requestDetails1"+requestDetails1);
        System.out.println("requestDetails2"+requestDetails2);

        if (requestDetails1.isPresent()) {
            FriendRequest requestFromObject = requestDetails1.get();
            requestFromObject.setBlockedBy(requestFrom);
            requestFromObject.setFriendRequestStatus("Blocked");
            friendRequestDao.save(requestFromObject);
        } else if (requestDetails2.isPresent()){
            FriendRequest requestToObject = requestDetails2.get();
            requestToObject.setBlockedBy(requestTo);
            requestToObject.setFriendRequestStatus("Blocked");
            friendRequestDao.save(requestToObject);
        } else {
            FriendRequest updateRequest = new FriendRequest(requestFrom,requestTo,"Blocked",requestFrom);
            friendRequestDao.save(updateRequest);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Member is Blocked")
                        .responseObj("Member is Blocked")
                        .build());
       // return "Member is Blocked";
    }

    public String unblockFriend(Long requestFrom, Long requestTo) {
        cancelFriendRequest(requestFrom,requestTo);
        return "Member is Unblocked";
    }

//    get block list
    public ResponseEntity<?> getBlockList(Long blockedBy) {

        List<FriendRequest> request = friendRequestDao.findByBlockedBy(blockedBy);
        if(request!=null&& !request.isEmpty()) {
            List<Long> ids = request.stream()
                    .filter(r -> r.getFriendRequestStatus().equalsIgnoreCase("Blocked"))
                    .map(FriendRequest::getRequestTo).toList();
            LOG.info("ids" + ids);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("IDs are : " + ids)
                            .responseObj("IDs are : " + ids)
                            .build());
        }else {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("block list are empty with member ID "+blockedBy)
                            .responseObj(new ArrayList<>())
                            .build());
        }
        //return "IDs are : "+ids;
    }

}
