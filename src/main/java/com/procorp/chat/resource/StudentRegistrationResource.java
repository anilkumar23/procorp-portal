package com.procorp.chat.resource;

import com.procorp.chat.dtos.StudentDTO;
import com.procorp.chat.entities.Student;
import com.procorp.chat.service.StudentService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("member-service")
public class StudentRegistrationResource {
    private final static Logger LOG = LoggerFactory.getLogger(StudentRegistrationResource.class);
    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public String addStudent(@Valid @RequestBody StudentDTO student) {
        LOG.info("Student :: Student Name {}", student.getFullName());
        studentService.addStudent(student);
        return "Student with Name:" + student.getFullName() + " has been Added.";
    }


    @DeleteMapping("/student/{studentId}")
    public String removeStudent(Long studentId) {
        studentService.removeStudent(studentId);
        return "Student with Id:" + studentId + " has been removed.";
    }

    @GetMapping("/getStudentsById/{studentId}")
    public Student getStudentsById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents(Long memberId) {
        return studentService.getAllStudents(memberId);

    }

//	@PutMapping("/student")
//	public List<Student> updateStudent(@Valid @RequestBody Student student) {
//		return studentService.updateStudent();
//	}

    @PutMapping("/student")
    public Long updateStudent(@javax.validation.Valid @RequestBody StudentDTO student) {
        return studentService.updateStudent(student);
    }
}

