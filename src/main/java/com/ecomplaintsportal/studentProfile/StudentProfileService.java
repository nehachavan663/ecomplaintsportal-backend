package com.ecomplaintsportal.studentProfile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentProfileService {

    @Autowired
    private StudentProfileRepository studentRepository;

    private final String uploadDir = "uploads/";

    // =========================
    // GET STUDENT BY ID
    // =========================
    public StudentProfile getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // =========================
    // UPDATE STUDENT PROFILE
    // =========================
    public StudentProfile updateStudentProfile(String id,
                                               StudentProfile updatedStudent,
                                               MultipartFile image) {

        StudentProfile existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existing.setName(updatedStudent.getName());
        existing.setRollNo(updatedStudent.getRollNo());
        existing.setDepartment(updatedStudent.getDepartment());
        existing.setClassName(updatedStudent.getClassName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setPhone(updatedStudent.getPhone());
        existing.setAddress(updatedStudent.getAddress());

        if (image != null && !image.isEmpty()) {
            try {
                String fileName = image.getOriginalFilename();
                java.nio.file.Path uploadPath = Paths.get(uploadDir);

                Files.createDirectories(uploadPath);

                Files.copy(image.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);

                existing.setProfileImage(fileName);

            } catch (Exception e) {
                throw new RuntimeException("Image upload failed");
            }
        }

        return studentRepository.save(existing);
    }
}