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

    // ✅ SAVE MESSAGE
    @PostMapping
    public String saveMessage(@RequestBody ContactMessage message) {
        repo.save(message);
        return "Message Sent Successfully!";
    }

    // ✅ GET ALL MESSAGES
    @GetMapping
    public List<ContactMessage> getAllMessages() {
        return repo.findAll();
    }

    // ✅ SEND RESPONSE + HTML EMAIL
    @PutMapping("/{id}")
    public ContactMessage sendReply(@PathVariable String id,
                                    @RequestBody ContactMessage updatedMsg) {

        ContactMessage msg = repo.findById(id).orElse(null);

        if (msg == null) {
            throw new RuntimeException("Message not found");
        }

        msg.setAdminResponse(updatedMsg.getAdminResponse());

        try {
            // ✅ HTML EMAIL
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(msg.getEmail());
            helper.setSubject("Re: " + msg.getSubject());

            String htmlContent =
                    "<div style='font-family:Arial;padding:20px'>" +
                    "<h2 style='color:#2c3e50;'>E-Complaints Portal</h2>" +

                    "<p>Hello <b>" + msg.getName() + "</b>,</p>" +

                    "<p><b>Your Message:</b></p>" +
                    "<div style='background:#f4f4f4;padding:10px;border-radius:5px'>" +
                    msg.getMessage() +
                    "</div>" +

                    "<p><b>Admin Reply:</b></p>" +
                    "<div style='background:#e8f5e9;padding:10px;border-radius:5px'>" +
                    updatedMsg.getAdminResponse() +
                    "</div>" +

                    "<br><p>Thank you,<br><b>Ecomplaints Team</b></p>" +
                    "</div>";

            helper.setText(htmlContent, true); // ✅ HTML enabled

            mailSender.send(mimeMessage);

            System.out.println("✅ EMAIL SENT SUCCESS");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Email sending failed");
        }

        return repo.save(msg);
    }
}