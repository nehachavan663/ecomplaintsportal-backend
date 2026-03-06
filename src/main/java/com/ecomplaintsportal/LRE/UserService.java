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

    // Register
    public User registerUser(User user) {

        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new RuntimeException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Login
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    // Forgot Password
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

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password Updated Successfully";
    }
}