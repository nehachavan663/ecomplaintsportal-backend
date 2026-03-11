package com.ecomplaintsportal.adminProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomplaintsportal.adminlogin.Admin;
import com.ecomplaintsportal.adminlogin.AdminRepository;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminProfileController {

    @Autowired
    private AdminRepository adminRepository;


    /* ================= GET PROFILE ================= */

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getAdminProfile(@PathVariable String email){

        Admin admin = adminRepository.findByEmail(email);

        if(admin == null){
            return ResponseEntity.badRequest().body("Admin not found");
        }

        return ResponseEntity.ok(admin);
    }


    /* ================= UPDATE PROFILE ================= */

    @PutMapping("/profile/update/{email}")
    public ResponseEntity<?> updateProfile(@PathVariable String email, @RequestBody Admin updatedAdmin){

        Admin admin = adminRepository.findByEmail(email);

        if(admin == null){
            return ResponseEntity.badRequest().body("Admin not found");
        }

        admin.setName(updatedAdmin.getName());
        admin.setPhone(updatedAdmin.getPhone());
        admin.setDepartment(updatedAdmin.getDepartment());
        admin.setLocation(updatedAdmin.getLocation());
        admin.setBio(updatedAdmin.getBio());   // ✅ ADD THIS LINE

        adminRepository.save(admin);

        return ResponseEntity.ok(admin);
    }

    /* ================= CHANGE PASSWORD ================= */

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequest request){

        Admin admin = adminRepository.findByEmail(request.getEmail());

        if(admin == null){
            return ResponseEntity.badRequest().body("Admin not found");
        }

        if(!admin.getPassword().equals(request.getCurrentPassword())){
            return ResponseEntity.badRequest().body("Current password incorrect");
        }

        admin.setPassword(request.getNewPassword());

        adminRepository.save(admin);

        return ResponseEntity.ok("Password Updated Successfully");
    }






}