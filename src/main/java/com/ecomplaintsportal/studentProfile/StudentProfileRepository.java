package com.ecomplaintsportal.studentProfile;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentProfileRepository  extends MongoRepository <StudentProfile , String>{

}
