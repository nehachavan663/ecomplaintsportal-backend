package com.ecomplaintsportal.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecomplaintsportal.ComplaintForm.ComplaintRepository;
import com.ecomplaintsportal.LRE.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData() {

        Map<String, Object> response = new HashMap<>();

        // ✅ Fast counts (indexed)
        long total = complaintRepository.count();
        long pending = complaintRepository.countByStatus("Pending");
        long inProgress = complaintRepository.countByStatus("In Progress");
        long resolved = complaintRepository.countByStatus("Resolved");

        long studentCount = userRepository.count();

        response.put("total", total);
        response.put("pending", pending);
        response.put("inProgress", inProgress);
        response.put("resolved", resolved);
        response.put("studentCount", studentCount);

        // ✅ FIXED: Department stats using aggregation (NO findAll)
        Map<String, Long> departmentStats = new HashMap<>();

        List<Map<String, Object>> results = complaintRepository.countByDepartment();

        for (Map<String, Object> row : results) {

            String dept = (String) row.get("_id");

            if (dept == null || dept.isBlank()) {
                dept = "Unassigned";
            }

            Long count = ((Number) row.get("count")).longValue();

            departmentStats.put(dept, count);
        }

        response.put("departmentStats", departmentStats);

        return response;
    }
}