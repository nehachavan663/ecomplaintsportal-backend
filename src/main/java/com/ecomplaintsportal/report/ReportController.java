package com.ecomplaintsportal.report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Main report with filtering
    @GetMapping
    public List<ReportDTO> getReports(
            @RequestParam(required = false) String type) {

    	
        return reportService.generateReports(type);
    }

    // Total complaints per department
    @GetMapping("/department-totals")
    public Map<String, Long> getDepartmentTotals(
            @RequestParam(required = false) String type) {

        return reportService.getDepartmentTotals(type);
    }
}
