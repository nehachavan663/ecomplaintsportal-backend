package com.ecomplaintsportal.LRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;   // ✅ ADD THIS

    // Register
    public User registerUser(User user) {

        // ✅ Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Login
    public String loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user != null && 
            passwordEncoder.matches(password, user.getPassword())) {   // ✅ FIXED
            return "Login Successful";
        } else {
            return "Invalid Email or Password";
        }
    }

    // Forgot Password
    public String verifyAndUpdate(String email, String question, String answer, String newPassword) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "User Not Found";
        }

        if (user.getSecurityQuestion() == null ||
                !user.getSecurityQuestion().equals(question)) {
            return "Security Question Incorrect";
        }

        if (user.getSecurityAnswer() == null ||
                !user.getSecurityAnswer().equalsIgnoreCase(answer)) {
            return "Security Answer Incorrect";
        }

        // ✅ Encrypt new password before saving
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return "Password Updated Successfully";
    }
}
