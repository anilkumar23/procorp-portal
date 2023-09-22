package com.procorp.chat.resource;

import com.procorp.chat.dtos.StudentDTO;
import com.procorp.chat.service.StudentService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("member-service")
public class StudentRegistrationResource {
    private final static Logger LOG = LoggerFactory.getLogger(StudentRegistrationResource.class);
    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public String addStudent(@Valid @RequestBody StudentDTO student) {
        LOG.info("Student :: Student Name {}", student.getStudentName());
        studentService.addStudent(student);
        return "Student with Name:" + student.getStudentName() + " has been Added.";
    }

}
