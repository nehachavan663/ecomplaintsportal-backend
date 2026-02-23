package com.ecomplaintsportal.ComplaintForm;

import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
@Service
public class ComplaintService {

    private final ComplaintRepository repository;

    public ComplaintService(ComplaintRepository repository) {
        this.repository = repository;
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

        // 🔁 STATUS CHANGE LOGIC
        if (updated.getStatus() != null) {

            String oldStatus = existing.getStatus();
            String newStatus = updated.getStatus();

            if (!oldStatus.equals(newStatus)) {

                // When work starts
                if (newStatus.equals("In Progress") && existing.getStartedAt() == null) {
                    existing.setStartedAt(LocalDateTime.now());
                }

                // When work completes
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

        return repository.save(existing);
    }

    public void deleteComplaint(String id) {
        repository.deleteById(id);
    }
}