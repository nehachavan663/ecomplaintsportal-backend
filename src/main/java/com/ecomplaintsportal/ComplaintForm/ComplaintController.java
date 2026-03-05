package com.ecomplaintsportal.ComplaintForm;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:3000")
public class ComplaintController {

    private final ComplaintService service;

    public ComplaintController(ComplaintService service) {
        this.service = service;
    }

    @PostMapping
    public Complaint createComplaint(@RequestBody Complaint complaint) {
        return service.saveComplaint(complaint);
    }

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return service.getAllComplaints();
    }

    @GetMapping("/department/{department}")
    public List<Complaint> getByDepartment(@PathVariable String department) {
        return service.getComplaintsByDepartment(department);
    }

    @PutMapping("/{id}")
    public Complaint updateComplaint(
            @PathVariable String id,
            @RequestBody Complaint updatedComplaint) {

        return service.updateComplaint(id, updatedComplaint);
    }

    @DeleteMapping("/{id}")
    public void deleteComplaint(@PathVariable String id) {
        service.deleteComplaint(id);
    }
}