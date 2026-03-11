package com.ecomplaintsportal.LRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/lre")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TwoFactorService twoFactorService;

    @Autowired
    private UserRepository userRepository;

    // ================= REGISTER =================
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // ================= LOGIN =================
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

    // ================= FORGOT PASSWORD =================
    @PutMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> data) {

        String email = data.get("email");
        String question = data.get("question");
        String answer = data.get("answer");
        String newPassword = data.get("newPassword");

        return userService.verifyAndUpdate(email, question, answer, newPassword);
    }

    // ================= CHANGE PASSWORD =================
    @PutMapping("/change-password")
    public String changePassword(@RequestBody Map<String, String> data) {

        String userId = data.get("userId");
        String currentPassword = data.get("currentPassword");
        String newPassword = data.get("newPassword");

        return userService.changePassword(userId, currentPassword, newPassword);
    }
    @GetMapping("/generate-2fa/{userId}")
    public Map<String,String> generate2FA(@PathVariable String userId){

        String secret = twoFactorService.generateSecret();

        User user = userRepository.findById(userId).orElse(null);

        if(user != null){
            user.setTwoFASecret(secret);
            userRepository.save(user);
        }

        String qrUrl = "otpauth://totp/OCMS:" + userId +
                "?secret=" + secret +
                "&issuer=OCMSPortal";

        Map<String,String> result = new HashMap<>();

        result.put("secret",secret);
        result.put("qrUrl",qrUrl);

        return result;
    }
    @PostMapping("/verify-2fa")
    public boolean verifyOTP(@RequestBody Map<String,String> data){

        String userId = data.get("userId");
        String code = data.get("code");

        User user = userRepository.findById(userId).orElse(null);

        if(user == null) return false;

        boolean valid = twoFactorService.verifyCode(user.getTwoFASecret(),code);

        if(valid){
            user.setTwoFAEnabled(true);
            userRepository.save(user);
        }

        return valid;
    }
}