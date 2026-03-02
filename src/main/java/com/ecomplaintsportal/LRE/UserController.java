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

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );
    }

    @PutMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> data) {

        String email = data.get("email");
        String question = data.get("question");
        String answer = data.get("answer");
        String newPassword = data.get("newPassword");

        return userService.verifyAndUpdate(email, question, answer, newPassword);
    }
}
