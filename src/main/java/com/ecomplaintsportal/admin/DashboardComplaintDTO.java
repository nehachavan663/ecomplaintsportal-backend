package com.ecomplaintsportal.admin;

public class DashboardComplaintDTO {

    private String id;
    private String userName;
    private String title;
    private String category;
    private String status;

    public DashboardComplaintDTO(String id, String userName,
                                  String title, String category,
                                  String status) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.category = category;
        this.status = status;
    }

    public String getId() { return id; }
    public String getUserName() { return userName; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
}