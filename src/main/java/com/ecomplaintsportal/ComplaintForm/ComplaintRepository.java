package com.ecomplaintsportal.ComplaintForm;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComplaintRepository extends MongoRepository<Complaint, String> {

    List<Complaint> findTop5ByOrderByIdDesc();

    List<Complaint> findByStudentId(String studentId);

    List<Complaint> findByDepartmentIgnoreCase(String department);

    long countByStudentIdAndStatus(String studentId, String status);

    long countByStatus(String status);
}