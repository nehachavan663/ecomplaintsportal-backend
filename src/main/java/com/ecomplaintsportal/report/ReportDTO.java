
package com.ecomplaintsportal.report;

public class ReportDTO {

    private String department;
    private String category;
    private String status;
    private String startDate;
    private String completedDate;
    private long complaints;

    public ReportDTO(String department,
                     String category,
                     String status,
                     String startDate,
                     String completedDate,
                     long complaints) {

        this.department = department;
        this.category = category;
        this.status = status;
        this.startDate = startDate;
        this.completedDate = completedDate;
        this.complaints = complaints;
    }

    public String getDepartment() { return department; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public String getStartDate() { return startDate; }
    public String getCompletedDate() { return completedDate; }
    public long getComplaints() { return complaints; }
}

