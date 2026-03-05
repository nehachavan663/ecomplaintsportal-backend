package com.ecomplaintsportal.ComplaintForm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "complaints")
public class Complaint {

    @Id
    private String id;

	
    private String studentId;   // link to student
    private String title;
    private String description;
    private String status;  // Pending / In Progress / Resolved

    private String area;
    private String category;
    private String date;   // form date (optional)
    private String image;
    private String department;
    private String response;

    // 🔥 NEW FIELDS (Lifecycle)
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime resolvedAt;

    // Default constructor
    public Complaint() {
        this.status = "Pending";
        this.createdAt = LocalDateTime.now(); // auto set
    }


    // ================= GETTERS & SETTERS =================

 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	public LocalDateTime getResolvedAt() {
		return resolvedAt;
	}

	public void setResolvedAt(LocalDateTime resolvedAt) {
		this.resolvedAt = resolvedAt;
	}

}