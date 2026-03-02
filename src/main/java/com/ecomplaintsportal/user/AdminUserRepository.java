package com.ecomplaintsportal.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminUserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    
}
