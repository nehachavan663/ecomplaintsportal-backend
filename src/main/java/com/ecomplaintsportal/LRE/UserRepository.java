package com.ecomplaintsportal.LRE;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

    User findByEmail(String email);
    User findByEmailOrPhoneOrFullName(String email,String phone,String fullName);

}