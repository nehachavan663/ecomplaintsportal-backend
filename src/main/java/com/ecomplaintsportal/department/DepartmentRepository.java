package com.ecomplaintsportal.department;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {

    Department findByEmail(String email);

}