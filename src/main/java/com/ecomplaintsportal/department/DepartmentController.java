package com.ecomplaintsportal.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @GetMapping
    public List<Department> getAllDepartments(){
        return service.getAllDepartments();
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department){
        return service.saveDepartment(department);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable String id,
                                       @RequestBody Department department){
        return service.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable String id){
        service.deleteDepartment(id);
    }

    /* ===== LOGIN API ===== */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Department loginData){

        Department staff = service.login(
                loginData.getEmail(),
                loginData.getPassword()
        );

        if(staff == null){
            return ResponseEntity.status(401).body("Invalid Login");
        }

        // Remove password before sending response
        staff.setPassword(null);

        return ResponseEntity.ok(staff);
    }

}