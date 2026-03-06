package com.ecomplaintsportal.adminlogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    // Optional GET for testing in browser
    @GetMapping("/login")
    public String testLogin() {
        return "Admin Login API is working. Use POST request.";
    }

    // Actual login API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin loginRequest) {

        Admin admin = adminRepository.findByEmail(loginRequest.getEmail());

        if (admin == null) {
            return ResponseEntity.status(401).body("Admin not found");
        }

        if (!admin.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        return ResponseEntity.ok(admin);
    }
}