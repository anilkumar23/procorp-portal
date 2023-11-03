package com.procorp.chat.resource;

import com.procorp.chat.dtos.FriendRequestDTO;
import com.procorp.chat.dtos.GlobalResponseDTO;
import com.procorp.chat.service.FriendService;
import com.procorp.chat.service.MemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("friend-service")
@SecurityRequirement(name = "bearerAuth")
public class FriendResource {
    private final static Logger LOG = LoggerFactory.getLogger(FriendResource.class);
    @Autowired
    private FriendService friendsService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/sendFriendRequest")
    public ResponseEntity<?> sendFriendRequest(@Valid @RequestParam Long requestFrom, @RequestParam Long requestTo) {
//        LOG.info("Student :: Student Name {}", student.getFullName());
//        if(Optional.ofNullable(studentService.getStudentById(requestFrom))
//                Optional.ofNullable(studentService.getStudentById(requestFrom)) ) {
//            return "Members do not exist";
//        }
        return friendsService.sendFriendRequest(requestFrom,requestTo);
    }

//    @GetMapping("/getFriendRequestsSent")
//    public ResponseEntity<?> getFriendRequestsSent(@Valid @RequestParam Long requestFrom) {
////        LOG.info("Student :: Student Name {}", student.getFullName());
//        return friendsService.getFriendRequestsSent(requestFrom);
//    }

    @GetMapping("/getFriendRequests")
    public ResponseEntity<?> getFriendRequests(@Valid @RequestParam Long requestTo) {
        return friendsService.getFriendRequests(requestTo);
    }

    @GetMapping("/getFriendsList")
    public ResponseEntity<?> getFriendsList(@Valid @RequestParam Long memberId) {
        return friendsService.getFriendsList(memberId);
    }

//    @GetMapping("/getFriendRequestsReceived")
//    public ResponseEntity<?> getFriendRequestsReceived(@Valid @RequestParam Long requestFrom) {
////        LOG.info("Student :: Student Name {}", student.getFullName());
//        return friendsService.getFriendRequestsReceived(requestFrom);
//    }

//    This API can be used for both cancel and reject friends request
    @DeleteMapping("/cancelFriendRequest")
    public ResponseEntity<?> cancelFriendRequest(@Valid @RequestParam Long requestFrom,@RequestParam Long requestTo) {
//        LOG.info("Student :: Student Name {}", student.getFullName());
        ResponseEntity<?> response = friendsService.cancelFriendRequest(requestFrom,requestTo);
        LOG.info("Friend's Request from:" + requestFrom + " has been cancelled.");
        return response;
    }

    @PutMapping("/acceptFriendRequest")
    public ResponseEntity<?> acceptFriendRequest(@Valid @RequestParam Long requestFrom,@RequestParam Long requestTo) {
//        LOG.info("Student :: Student Name {}", student.getFullName());
        ResponseEntity<?> response =friendsService.acceptFriendRequest(requestFrom,requestTo);
        LOG.info( "Friend's Request from:" + requestFrom + " has been accepted.");
        return response;
    }

    @PutMapping("/blockFriend")
    public ResponseEntity<?> blockFriend(@Valid @RequestParam Long requestFrom,@RequestParam Long requestTo) {
//        LOG.info("Student :: Student Name {}", student.getFullName());
        ResponseEntity<?> response =friendsService.blockFriend(requestFrom,requestTo);
        LOG.info( "Member :" + requestTo + " has been Blocked.");
        return response;
    }

    @GetMapping("/blockList")
    public ResponseEntity<?>  blockList(@Valid @RequestParam Long requestFrom) {
//        LOG.info("Student :: Student Name {}", student.getFullName());
        ResponseEntity<?>  response =friendsService.getBlockList(requestFrom);
        LOG.info( "Member :" + requestFrom + " block list.");
        return response;
    }




//    unfriedn
//    block
//

//
//    @DeleteMapping("/student/{studentId}")
//    public String removeStudent(Long studentId) {
//        studentService.removeStudent(studentId);
//        return "Student with Id:" + studentId + " has been removed.";
//    }
//
//    @GetMapping("/getStudentsById/{studentId}")
//    public Student getStudentsById(@PathVariable Long studentId) {
//        return studentService.getStudentById(studentId);
//    }
//
//    @GetMapping("/getAllStudents")
//    public List<Student> getAllStudents() {
//        return studentService.getAllStudents();
//    }
//
////	@PutMapping("/student")
////	public List<Student> updateStudent(@Valid @RequestBody Student student) {
////		return studentService.updateStudent();
////	}
//
//    @PutMapping("/student")
//    public Long updateStudent(@javax.validation.Valid @RequestBody StudentDTO student) {
//        return studentService.updateStudent(student);
//    }
}

