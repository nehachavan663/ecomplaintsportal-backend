package com.ecomplaintsportal.LRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/lre")
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Login
    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> data) {

    String login = data.get("login");
    String password = data.get("password");

    User user = userService.loginUser(login, password);

    if (user == null) {
        throw new RuntimeException("Invalid Username / Email / Phone or Password");
    }

    return user;

    }
    // Forgot Password
    @PutMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> data) {

        String email = data.get("email");
        String question = data.get("question");
        String answer = data.get("answer");
        String newPassword = data.get("newPassword");

        return userService.verifyAndUpdate(email, question, answer, newPassword);
    }
}