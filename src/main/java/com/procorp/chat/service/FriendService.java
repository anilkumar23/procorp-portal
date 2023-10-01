package com.procorp.chat.service;

import com.procorp.chat.dao.FriendRequestDao;
import com.procorp.chat.dao.StudentDao;
import com.procorp.chat.entities.FriendRequest;
import com.procorp.chat.exception.StudentCourseIllegalStateException;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final static Logger LOG = LoggerFactory.getLogger(FriendService.class);

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Autowired
    private StudentDao studentDao;

    public String sendFriendRequest(Long requestFrom, Long requestTo) {
        Set<Long> ids = new HashSet<>();
        ids.add(requestFrom);
        ids.add(requestTo);
        if(!studentDao.existsMembersAllByStudentId(ids)) {
            return "Member IDS sent doesn't exist";
        }

        //This check is to make sure either of the members dont send request when there is already request raised
        Optional<FriendRequest> requestDetails1 = friendRequestDao.findByRequestFromAndRequestTo(requestFrom,requestTo);
        Optional<FriendRequest> requestDetails2 = friendRequestDao.findByRequestFromAndRequestTo(requestTo,requestFrom);

        if (requestDetails1.isPresent() | requestDetails2.isPresent()) {
            return "Friend's request already exist";
        }
        FriendRequest request = new FriendRequest();
        request.setRequestFrom(requestFrom);
        request.setRequestTo(requestTo);
        request.setFriendRequestStatus("waiting");
        friendRequestDao.save(request);
        LOG.info("request sent");
        return "request sent,Waiting for approval";
    }

    public List<FriendRequest> getFriendRequestsSent(Long requestFrom) {
        return friendRequestDao.findByRequestFrom(requestFrom);
    }

    public List<FriendRequest> getFriendRequestsReceived(Long requestTo) {
        return friendRequestDao.findByRequestTo(requestTo);
    }

    public String cancelFriendRequest(Long requestFrom, Long requestTo) {
        Optional<FriendRequest> requestDetails = friendRequestDao.findByRequestFromAndRequestTo(requestFrom,requestTo);
        System.out.println(requestDetails);
        if (!requestDetails.isPresent()) {
//            throw new FindException("Failed to cancel request. Invalid requestFrom :: " + requestFrom);
            return "Friend's request doesn't exist";
        }
        friendRequestDao.delete(requestDetails.get());
        LOG.info("Deleting record will cancel teh request");
        return "Friend's request cancelled";

        }

    public String acceptFriendRequest(Long requestFrom, Long requestTo) {
        Optional<FriendRequest> requestDetails = friendRequestDao.findByRequestFromAndRequestTo(requestFrom,requestTo);
        LOG.info("Abhi"+String.valueOf(requestDetails));
        if (requestDetails.isPresent()) {
            return "Friend's request doesn't exist";
//            throw new FindException("Failed to cancel request. Invalid requestFrom :: " + requestFrom);
        }
        if(requestDetails.get().getFriendRequestStatus().equalsIgnoreCase("Waiting")) {
            FriendRequest acceptRequest = new FriendRequest(requestDetails.get().getRequestFrom(),requestDetails.get().getRequestTo(),"Accepted",null);
            friendRequestDao.save(acceptRequest);
            return "Friend's request Accepted";
        }
        return "Member : "+requestFrom+" is already a Friend of "+"Member : "+requestTo;
    }

    public String blockFriend(Long requestFrom, Long requestTo) {
        Set<Long> memberIds = new HashSet<>();
        memberIds.add(requestFrom);
        memberIds.add(requestTo);
        if(!studentDao.existsMembersAllByStudentId(memberIds)) {
            return "Member ID doesn't exist";
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
        return "Member is Blocked";
    }

    public String unblockFriend(Long requestFrom, Long requestTo) {
        cancelFriendRequest(requestFrom,requestTo);
        return "Member is Unblocked";
    }

//    get block list
    public String getBlockList(Long blockedBy) {

        List<FriendRequest> request = friendRequestDao.findByBlockedBy(blockedBy);
        List<Long> ids = request.stream()
                .filter(r -> r.getFriendRequestStatus().equalsIgnoreCase("Blocked"))
                .map(FriendRequest::getRequestTo).collect(Collectors.toList());
        LOG.info("ids"+ids);
        return "IDs are : "+ids;
    }

}
