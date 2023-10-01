package com.procorp.chat.service;

import com.procorp.chat.dao.FriendRequestDao;
import com.procorp.chat.dao.StudentDao;
import com.procorp.chat.entities.FriendRequest;
import com.procorp.chat.entities.Student;
import com.procorp.chat.dtos.StudentDTO;
import com.procorp.chat.exception.StudentCourseIllegalStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final static Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    public Long addStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getFullName(), studentDTO.getMobileNumber(), studentDTO.getGender(), studentDTO.getEmail(), studentDTO.getPassword(),studentDTO.getDateOfBirth(),studentDTO.getRegistrationDate());
        studentDao.save(student);
        LOG.info("Student {} Successfully added", student.getStudentId());
        return student.getStudentId();
    }

    public Student getStudentById(Long studentId) {
        return studentDao.findById(studentId).get();
    }

    public List<Student> getAllStudents(Long memberId) {
//        List<FriendRequest> request = null;
//        if(friendRequestDao.findByRequestFrom(memberId).isEmpty()) {
//            request = friendRequestDao.findByRequestFrom(memberId);
//            request = request.stream().filter(r -> r.getFriendRequestStatus().equals("blocked"))
//                    .collect(Collectors.toList());
//            request.get
//        } else if(friendRequestDao.findByRequestTo(memberId).isEmpty()) {
//            request = friendRequestDao.findByRequestTo(memberId);
//            request = request.stream().filter(r -> r.getFriendRequestStatus().equals("blocked"))
//                    .collect(Collectors.toList());
//        }
        return studentDao.findAll();
    }

    public Long updateStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getFullName(), studentDTO.getMobileNumber(), studentDTO.getGender(), studentDTO.getEmail(), studentDTO.getPassword(),studentDTO.getDateOfBirth(),studentDTO.getRegistrationDate());
        studentDao.save(student);
        LOG.info("Student {} Successfully updated", student.getStudentId());
        return student.getStudentId();
    }

    public void removeStudent(Long studentId) {
        Optional<Student> student = studentDao.findById(studentId);
        if (!student.isPresent()) {
            throw new StudentCourseIllegalStateException("Failed to remove Student. Invalid StudentId :: " + studentId);
        }
        studentDao.delete(student.get());
    }

}
