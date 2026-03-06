package com.ecomplaintsportal.ComplaintForm;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ComplaintService {

    private final ComplaintRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public ComplaintService(ComplaintRepository repository,
                            SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    /* ================= CREATE COMPLAINT ================= */

    public Complaint saveComplaint(Complaint complaint) {

        if (complaint.getStatus() == null || complaint.getStatus().isBlank()) {
            complaint.setStatus("Pending");
        }

        complaint.setCreatedAt(LocalDateTime.now());

        return repository.save(complaint);
    }

    /* ================= GET ALL COMPLAINTS ================= */

    public List<Complaint> getAllComplaints() {
        return repository.findAll();
    }

    /* ================= UPDATE COMPLAINT ================= */

    public Complaint updateComplaint(String id, Complaint updatedComplaint) {

        Complaint existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        /* ---------- STATUS UPDATE ---------- */

        if (updatedComplaint.getStatus() != null) {

            String newStatus = updatedComplaint.getStatus().trim();
            String oldStatus = existing.getStatus();

            if (!newStatus.equals(oldStatus)) {

                if ("In Progress".equalsIgnoreCase(newStatus) && existing.getStartedAt() == null) {
                    existing.setStartedAt(LocalDateTime.now());
                }

                if ("Resolved".equalsIgnoreCase(newStatus) && existing.getResolvedAt() == null) {
                    existing.setResolvedAt(LocalDateTime.now());
                }
            }

            existing.setStatus(newStatus);
        }

        /* ---------- RESPONSE UPDATE ---------- */

        if (updatedComplaint.getResponse() != null) {
            existing.setResponse(updatedComplaint.getResponse());
        }

        /* ---------- RESOLVED IMAGE ---------- */

        if (updatedComplaint.getResolvedImage() != null) {
            existing.setResolvedImage(updatedComplaint.getResolvedImage());
        }

        /* ---------- DEPARTMENT ASSIGN ---------- */

        if (updatedComplaint.getDepartment() != null &&
                !updatedComplaint.getDepartment().isBlank()) {

            existing.setDepartment(updatedComplaint.getDepartment().trim());
        }

        Complaint saved = repository.save(existing);

        /* ---------- WEBSOCKET UPDATE ---------- */

        if (existing.getStudentId() != null) {
            messagingTemplate.convertAndSend(
                    "/topic/complaints/" + existing.getStudentId(),
                    "updated"
            );
        }

        return saved;
    }

    /* ================= DELETE COMPLAINT ================= */

    public void deleteComplaint(String id) {
        repository.deleteById(id);
    }

    /* ================= GET BY DEPARTMENT ================= */

    public List<Complaint> getComplaintsByDepartment(String department) {

        if (department == null || department.isBlank()) {
            return List.of();
        }

        return repository.findByDepartmentIgnoreCase(department.trim());
    }

    /* ================= GET BY STUDENT ================= */

    public List<Complaint> getByStudent(String studentId) {
        return repository.findByStudentId(studentId);
    }

    /* ================= STUDENT SUMMARY ================= */

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