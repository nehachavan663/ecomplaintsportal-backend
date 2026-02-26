package com.ecomplaintsportal.report;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomplaintsportal.ComplaintForm.Complaint;
import com.ecomplaintsportal.ComplaintForm.ComplaintRepository;

@Service
public class ReportService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<ReportDTO> generateReports(String type) {

        List<Complaint> complaints = complaintRepository.findAll();

        LocalDate today = LocalDate.now();

        // ===== Backend Date Filtering =====
        if (type != null && !type.isEmpty()) {

            complaints = complaints.stream()
                    .filter(c -> c.getCreatedAt() != null)
                    .filter(c -> {

                        LocalDate complaintDate =
                                c.getCreatedAt().toLocalDate();

                        switch (type.toLowerCase()) {
                            case "daily":
                                return complaintDate.equals(today);

                            case "monthly":
                                return complaintDate.getMonth() == today.getMonth()
                                        && complaintDate.getYear() == today.getYear();

                            case "yearly":
                                return complaintDate.getYear() == today.getYear();

                            default:
                                return true;
                        }
                    })
                    .collect(Collectors.toList());
        }

        // ===== Group by Department + Status + Category =====
        Map<String, List<Complaint>> grouped =
                complaints.stream()
                        .collect(Collectors.groupingBy(
                                c -> c.getDepartment() + "|" +
                                        c.getStatus() + "|" +
                                        c.getCategory()
                        ));

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        List<ReportDTO> reportList = new ArrayList<>();

        for (List<Complaint> groupComplaints : grouped.values()) {

            Complaint first = groupComplaints.get(0);

            String department = first.getDepartment();
            String status = first.getStatus();
            String category = first.getCategory();

            long count = groupComplaints.size();

            String startDate = groupComplaints.stream()
                    .map(Complaint::getStartedAt)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(d -> d.format(formatter))
                    .orElse(null);

            String completedDate = groupComplaints.stream()
                    .map(Complaint::getResolvedAt)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(d -> d.format(formatter))
                    .orElse(null);

            reportList.add(new ReportDTO(
                    department,
                    category,
                    status,
                    startDate,
                    completedDate,
                    count
            ));
        }

        return reportList;
    }

    // ===== Total Count Per Department =====
    public Map<String, Long> getDepartmentTotals(String type) {

        List<Complaint> complaints = complaintRepository.findAll();

        LocalDate today = LocalDate.now();

        if (type != null && !type.isEmpty()) {

            complaints = complaints.stream()
                    .filter(c -> c.getCreatedAt() != null)
                    .filter(c -> {
                        LocalDate complaintDate =
                                c.getCreatedAt().toLocalDate();

                        switch (type.toLowerCase()) {
                            case "daily":
                                return complaintDate.equals(today);
                            case "monthly":
                                return complaintDate.getMonth() == today.getMonth()
                                        && complaintDate.getYear() == today.getYear();
                            case "yearly":
                                return complaintDate.getYear() == today.getYear();
                            default:
                                return true;
                        }
                    })
                    .collect(Collectors.toList());
        }

        return complaints.stream()
                .collect(Collectors.groupingBy(
                        Complaint::getDepartment,
                        Collectors.counting()
                ));
    }
}


