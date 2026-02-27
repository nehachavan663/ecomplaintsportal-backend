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

        List<Complaint> complaints =
                complaintRepository.findTop5ByOrderByIdDesc();

        List<DashboardComplaintDTO> recentComplaints =
                complaints.stream()
                        .map(c -> new DashboardComplaintDTO(
                                c.getId(),
                                c.getUserName(),
                                c.getTitle(),
                                c.getCategory(),
                                c.getStatus()
                        ))
                        .toList();
        response.put("total", total);
        response.put("pending", pending);
        response.put("inProgress", inProgress);
        response.put("resolved", resolved);
        response.put("recentComplaints", recentComplaints);

        return response;
    }
}