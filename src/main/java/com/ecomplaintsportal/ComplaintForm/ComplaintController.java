package com.ecomplaintsportal.ComplaintForm;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")

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
    @GetMapping("/student/{studentId}/complaints")
    public List<Complaint> getComplaintsByStudent(@PathVariable String studentId) {
        return service.getByStudent(studentId);
    }

    // GET BY DEPARTMENT
    @GetMapping("/department/{department}")
    public List<Complaint> getByDepartment(@PathVariable String department) {
        return service.getComplaintsByDepartment(department);
    }

    // STUDENT PROFILE SUMMARY
    @GetMapping("/student/{studentId}")
    public Map<String, Long> getSummary(@PathVariable String studentId) {
        return service.getSummary(studentId);
    }

    // UPDATE FULL COMPLAINT
    @PutMapping("/{id}")
    public Complaint updateComplaint(
            @PathVariable String id,
            @RequestBody Complaint updatedComplaint) {

        return service.updateComplaint(id, updatedComplaint);
    }

    // QUICK STATUS UPDATE
    @PutMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable String id,
                                  @RequestParam String status) {

        Complaint updated = new Complaint();
        updated.setStatus(status);

        return service.updateComplaint(id, updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteComplaint(@PathVariable String id) {
        service.deleteComplaint(id);
    }
}