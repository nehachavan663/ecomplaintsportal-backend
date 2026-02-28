package com.ecomplaintsportal.ComplaintForm;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:3000")
public class ComplaintController {

    private final ComplaintService service;

    public ComplaintController(ComplaintService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public Complaint createComplaint(@RequestBody Complaint complaint) {
        return service.saveComplaint(complaint);
    }

    // GET ALL
    @GetMapping
    public List<Complaint> getAllComplaints() {
        return service.getAllComplaints();
    }

    // UPDATE (Admin Update)
    @PutMapping("/{id}")
    public Complaint updateComplaint(
            @PathVariable String id,
            @RequestBody Complaint updatedComplaint) {

        return service.updateComplaint(id, updatedComplaint);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteComplaint(@PathVariable String id) {
        service.deleteComplaint(id);
    }
    
 // ==================================================
    // NEW ENDPOINT: Update Only Status (Admin Quick Update)
    // ==================================================
    @PutMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable String id,
                                  @RequestParam String status) {

        Complaint updated = new Complaint();
        updated.setStatus(status);

        return service.updateComplaint(id, updated);
    }

    // ==================================================
    // NEW ENDPOINT: Student Profile Summary
    // ==================================================
    @GetMapping("/summary/{studentId}")
    public Map<String, Object> getSummary(@PathVariable String studentId) {
        return Map.of(
                "pending", service.countPendingByStudent(studentId),
                "inProgress", service.countInProgressByStudent(studentId),
                "resolved", service.countResolvedByStudent(studentId)
        );
    }
}