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

        user.setPassword(newPassword);
        userRepository.save(user);

        return "Password Updated Successfully";
    }
}
