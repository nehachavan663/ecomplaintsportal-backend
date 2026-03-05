package com.ecomplaintsportal.ComplaintForm;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Service
public class ComplaintService {

    private final ComplaintRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public ComplaintService(ComplaintRepository repository,
                            SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    public Complaint saveComplaint(Complaint complaint) {

        complaint.setStatus("Pending");
        complaint.setCreatedAt(LocalDateTime.now());

        return repository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return repository.findAll();
    }

    public Complaint updateComplaint(String id, Complaint updated) {

        Complaint existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        if (updated.getStatus() != null) {

            String oldStatus = existing.getStatus();
            String newStatus = updated.getStatus();

            if (!oldStatus.equals(newStatus)) {

                if (newStatus.equals("In Progress") && existing.getStartedAt() == null) {
                    existing.setStartedAt(LocalDateTime.now());
                }

                if (newStatus.equals("Resolved") && existing.getResolvedAt() == null) {
                    existing.setResolvedAt(LocalDateTime.now());
                }
            }

            existing.setStatus(newStatus);
        }

        if (updated.getDepartment() != null)
            existing.setDepartment(updated.getDepartment());

        if (updated.getResponse() != null)
            existing.setResponse(updated.getResponse());

        Complaint saved = repository.save(existing);

        // Send real-time update to student profile
        messagingTemplate.convertAndSend(
                "/topic/complaints/" + existing.getStudentId(),
                "updated"
        );

        return saved;
    }

    public void deleteComplaint(String id) {
        repository.deleteById(id);
    }

    // ===============================
    // STUDENT PROFILE METHODS
    // ===============================

    public List<Complaint> getByStudent(String studentId) {
        return repository.findByStudentId(studentId);
    }

    public Map<String, Long> getSummary(String studentId) {

        long pending = repository.countByStudentIdAndStatus(studentId, "Pending");
        long progress = repository.countByStudentIdAndStatus(studentId, "In Progress");
        long resolved = repository.countByStudentIdAndStatus(studentId, "Resolved");

        long total = pending + progress + resolved;

        Map<String, Long> summary = new HashMap<>();
        summary.put("total", total);
        summary.put("pending", pending);
        summary.put("progress", progress);
        summary.put("resolved", resolved);

        return summary;
    }
}