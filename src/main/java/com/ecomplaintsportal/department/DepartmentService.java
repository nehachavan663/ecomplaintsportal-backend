package com.ecomplaintsportal.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /* ================= GET ALL ================= */

    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    /* ================= CREATE ================= */

    public Department saveDepartment(Department department) {

        if (department.getDepartment() != null) {
            department.setDepartment(department.getDepartment().trim());
        }

        // Encrypt password
        department.setPassword(
                encoder.encode(department.getPassword())
        );

        return repository.save(department);
    }

    /* ================= UPDATE ================= */

    public Department updateDepartment(String id, Department department) {

        department.setId(id);

        if (department.getDepartment() != null) {
            department.setDepartment(department.getDepartment().trim());
        }

        return repository.save(department);
    }

    /* ================= DELETE ================= */

    public void deleteDepartment(String id) {
        repository.deleteById(id);
    }

    /* ================= LOGIN ================= */

    public Department login(String email, String password) {

        Department staff = repository.findByEmail(email.trim());

        if (staff == null || !staff.getStatus().equals("Active")) {
            return null;
        }

        if (encoder.matches(password, staff.getPassword())) {
            return staff;
        }

        return null;
    }
}