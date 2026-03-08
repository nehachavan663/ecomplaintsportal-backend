package com.ecomplaintsportal.LRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER USER =================
    public User registerUser(User user) {

        // Check if email already exists
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new RuntimeException("Email already registered");
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // ================= LOGIN USER =================
    public User loginUser(String login, String password) {

        // Login using email OR phone OR full name
        User user = userRepository.findByEmailOrPhoneOrFullName(login, login, login);

        // Check password using PasswordEncoder
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    // ================= FORGOT PASSWORD =================
    public String verifyAndUpdate(String email, String question, String answer, String newPassword) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "User Not Found";
        }

        if (!user.getSecurityQuestion().equals(question)) {
            return "Security Question Incorrect";
        }

        if (!user.getSecurityAnswer().equalsIgnoreCase(answer)) {
            return "Security Answer Incorrect";
        }

        // Update password with encryption
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password Updated Successfully";
    }
}