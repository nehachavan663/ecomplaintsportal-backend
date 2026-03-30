package com.ecomplaintsportal.ComplaintForm;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;

public interface ComplaintRepository extends MongoRepository<Complaint, String> {

    List<Complaint> findTop5ByOrderByIdDesc();

    List<Complaint> findByStudentId(String studentId);

    List<Complaint> findByDepartmentIgnoreCase(String department);

    long countByStudentIdAndStatus(String studentId, String status);

    long countByStatus(String status);
    
    long countByCategory(String category);

    // ✅ NEW: Aggregation for department stats (FAST 🚀)
    @Aggregation(pipeline = {
        "{ $group: { _id: '$department', count: { $sum: 1 } } }"
    })
    List<Map<String, Object>> countByDepartment();
}