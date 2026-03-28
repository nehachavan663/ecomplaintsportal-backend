package com.ecomplaintsportal.LRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

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

    // SEND RESPONSE + HTML EMAIL
    @PutMapping("/{id}")
    public ContactMessage sendReply(@PathVariable String id,
                                    @RequestBody ContactMessage updatedMsg) {

        ContactMessage msg = repo.findById(id).orElse(null);

        if (msg == null) {
            throw new RuntimeException("Message not found");
        }

        String replyText = updatedMsg.getAdminResponse();

        if (replyText == null || replyText.trim().isEmpty()) {
            throw new RuntimeException("Reply cannot be empty");
        }

        msg.setAdminResponse(replyText);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(msg.getEmail());
            helper.setSubject("Re: " + msg.getSubject());

            String htmlContent =
                    "<div style='font-family:Segoe UI;padding:20px'>" +
                    "<h2 style='color:#4CAF50;'>E-Complaints Portal</h2>" +
                    "<p>Hello <b>" + msg.getName() + "</b>,</p>" +
                    "<p><b>Your Message:</b></p>" +
                    "<div style='background:#f1f1f1;padding:12px;border-radius:8px'>" +
                    msg.getMessage() +
                    "</div>" +
                    "<p><b>Admin Reply:</b></p>" +
                    "<div style='background:#d4edda;padding:12px;border-radius:8px;color:#155724'>" +
                    replyText +
                    "</div>" +
                    "<br><p>Regards,<br><b>Ecomplaints Team</b></p>" +
                    "</div>";

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return repo.save(msg);   // ✅ IMPORTANT
    }
    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable String id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Message not found");
        }

        repo.deleteById(id);

        return "Message deleted successfully";
    }
}