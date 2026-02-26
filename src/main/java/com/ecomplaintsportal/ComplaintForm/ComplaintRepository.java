package com.ecomplaintsportal.ComplaintForm;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ComplaintRepository extends MongoRepository<Complaint, String> {
	 long countByStatus(String status);

	    List<Complaint> findTop5ByOrderByIdDesc();
	    }


