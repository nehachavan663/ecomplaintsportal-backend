package com.ecomplaintsportal.LRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    @Autowired
    private ContactRepository repo;

    // ✅ SAVE MESSAGE
    @PostMapping
    public String saveMessage(@RequestBody ContactMessage message) {
        repo.save(message);
        return "Message Sent Successfully!";
    }

    // ✅ GET ALL MESSAGES (ADMIN)
    @GetMapping
    public List<ContactMessage> getAllMessages() {
        return repo.findAll();
    }
}