package com.ecomplaintsportal.adminlogin;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
public class Admin {

    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private String role;

    private String department;
    private String phone;
    private String employeeId;
    private String location;
    private String joined;

    private String bio;   // ✅ ADD THIS

    private boolean twoFactorEnabled;
    private String twoFactorSecret;
    
    public Admin() {}

    /* ================= GETTERS ================= */

    public String getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getRole() { return role; }

    public String getDepartment() { return department; }

    public String getPhone() { return phone; }

    public String getEmployeeId() { return employeeId; }

    public String getLocation() { return location; }

    public String getJoined() { return joined; }

    public String getBio() { return bio; }   // ✅ ADD THIS

    public boolean isTwoFactorEnabled() { return twoFactorEnabled; }

    /* ================= SETTERS ================= */

    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setRole(String role) { this.role = role; }

    public void setDepartment(String department) { this.department = department; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public void setLocation(String location) { this.location = location; }

    public void setJoined(String joined) { this.joined = joined; }

    public void setBio(String bio) { this.bio = bio; }   

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }
    public String getTwoFactorSecret() {
        return twoFactorSecret;
    }

    public void setTwoFactorSecret(String twoFactorSecret) {
        this.twoFactorSecret = twoFactorSecret;
    }
    
}