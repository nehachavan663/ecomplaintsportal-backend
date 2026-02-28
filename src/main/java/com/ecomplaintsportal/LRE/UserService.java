package com.ecomplaintsportal.LRE;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecomplaintsportal.LRE.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Login
    public String loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return "Login Successful";
        } else {
            return "Invalid Email or Password";
        }
    }

    // Forgot Password
    public String verifyAndUpdate(String email, String answer, String newPassword) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "User not found";
        }

        if (!user.getSecurityAnswer().equalsIgnoreCase(answer)) {
            return "Incorrect security answer";
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        return "Password updated successfully";
    }
    }
