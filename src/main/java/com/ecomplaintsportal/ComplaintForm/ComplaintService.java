package com.ecomplaintsportal.ComplaintForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository repository;

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

        Optional<Complaint> optionalComplaint = repository.findById(id);

        if (optionalComplaint.isEmpty()) {
            return null;
        }

        Complaint existing = optionalComplaint.get();

        /* ---------- STATUS UPDATE ---------- */

        if (updatedComplaint.getStatus() != null) {

            existing.setStatus(updatedComplaint.getStatus().trim());

            if ("In Progress".equalsIgnoreCase(updatedComplaint.getStatus())) {
                existing.setStartedAt(LocalDateTime.now());
            }

            if ("Resolved".equalsIgnoreCase(updatedComplaint.getStatus())) {
                existing.setResolvedAt(LocalDateTime.now());
            }
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

        return repository.save(existing);
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
}