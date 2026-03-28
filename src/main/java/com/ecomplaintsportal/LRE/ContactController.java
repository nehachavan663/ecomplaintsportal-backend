package com.ecomplaintsportal.LRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    // SAVE MESSAGE
    @PostMapping
    public String saveMessage(@RequestBody ContactMessage message) {
        repo.save(message);
        return "Message Sent Successfully!";
    }

    // GET ALL MESSAGES
    @GetMapping
    public List<ContactMessage> getAllMessages() {
        return repo.findAll();
    }

    // ✅ SEND RESPONSE + EMAIL
    @PutMapping("/{id}")
    public ContactMessage sendReply(@PathVariable String id,
                                    @RequestBody ContactMessage updatedMsg) {

        ContactMessage msg = repo.findById(id).orElse(null);

        if (msg != null) {

            msg.setAdminResponse(updatedMsg.getAdminResponse());

            // 📧 SEND EMAIL
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(msg.getEmail());
            mail.setSubject("Response from Ecomplaints Portal");
            mail.setText(
                "Hello " + msg.getName() + ",\n\n" +
                "Your Message:\n" + msg.getMessage() + "\n\n" +
                "Admin Reply:\n" + updatedMsg.getAdminResponse() + "\n\n" +
                "Thank you."
            );

            // 🔥 ADD HERE
            try {
                mailSender.send(mail);
                System.out.println("EMAIL SENT SUCCESS");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return repo.save(msg);
        }

        return null;
    }
}