package com.ecomplaintsportal.department;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {

    Department findByEmailAndPasswordAndStatus(String email, String password, String status);

}
