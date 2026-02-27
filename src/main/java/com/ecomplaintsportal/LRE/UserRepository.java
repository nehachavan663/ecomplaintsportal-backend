package com.ecomplaintsportal.LRE;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecomplaintsportal.LRE.*;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
