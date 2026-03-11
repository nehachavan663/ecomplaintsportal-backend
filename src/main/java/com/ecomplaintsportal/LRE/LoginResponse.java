package com.ecomplaintsportal.LRE;

public class LoginResponse {

    private String message;
    private String role;
    private String department;

    public LoginResponse(String message,
                         String role,
                         String department){
        this.message=message;
        this.role=role;
        this.department=department;
    }

    public String getMessage(){ return message; }
    public String getRole(){ return role; }
    public String getDepartment(){ return department; }
}