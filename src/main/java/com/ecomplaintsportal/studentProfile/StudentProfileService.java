package com.ecomplaintsportal.studentProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomplaintsportal.ComplaintForm.ComplaintRepository;
import com.ecomplaintsportal.LRE.User;
import com.ecomplaintsportal.LRE.UserRepository;

@Service
public class StudentProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    public User getStudentProfile(String id){
        return userRepository.findById(id).orElse(null);
    }

    public User updateStudentProfile(String id, User updatedUser){

        User user = userRepository.findById(id).orElse(null);

        if(user == null){
            return null;
        }

        user.setFullName(updatedUser.getFullName());
        user.setFatherName(updatedUser.getFatherName());
        user.setRollNumber(updatedUser.getRollNumber());
        user.setDepartment(updatedUser.getDepartment());
        user.setClassName(updatedUser.getClassName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        return userRepository.save(user);
    }

    public void saveImage(String id, String imageId){

        User user = userRepository.findById(id).orElse(null);

        if(user != null){
            user.setProfileImage(imageId);
            userRepository.save(user);
        }
    }

    public ComplaintSummaryDTO getComplaintSummary(String studentId){

        long pending = complaintRepository.countByStudentIdAndStatus(studentId,"Pending");
        long progress = complaintRepository.countByStudentIdAndStatus(studentId,"In Progress");
        long resolved = complaintRepository.countByStudentIdAndStatus(studentId,"Resolved");

        return new ComplaintSummaryDTO(pending,progress,resolved);
    }
}