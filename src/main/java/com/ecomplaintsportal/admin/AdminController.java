package com.ecomplaintsportal.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecomplaintsportal.ComplaintForm.Complaint;
import com.ecomplaintsportal.ComplaintForm.ComplaintRepository;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private ComplaintRepository complaintRepository;
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData() {

        Map<String, Object> response = new HashMap<>();

        long total = complaintRepository.count();
        long pending = complaintRepository.countByStatus("Pending");
        long inProgress = complaintRepository.countByStatus("In Progress");
        long resolved = complaintRepository.countByStatus("Resolved");

        response.put("total", total);
        response.put("pending", pending);
        response.put("inProgress", inProgress);
        response.put("resolved", resolved);

        // Department statistics
        Map<String, Long> departmentStats = new HashMap<>();

        List<Complaint> complaints = complaintRepository.findAll();

        for (Complaint c : complaints) {
            String dept = c.getDepartment();

            departmentStats.put(
                dept,
                departmentStats.getOrDefault(dept, 0L) + 1
            );
        }

        response.put("departmentStats", departmentStats);

       
        

        return response;
    }
}