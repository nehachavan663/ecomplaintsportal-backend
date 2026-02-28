package com.ecomplaintsportal.studentProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentProfileController {

    @Autowired
    private StudentProfileService studentService;

    @GetMapping("/{id}")
    public StudentProfile getStudent(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public StudentProfile updateStudent(
            @PathVariable String id,
            @RequestPart("student") StudentProfile student,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return studentService.updateStudentProfile(id, student, image);
    }
}