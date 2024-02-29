package com.regalgrid.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.regalgrid.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUserName(String username);

    boolean existsByEmailId(String emailId);

    User getByUserName(String userName);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByUserName(String userName);

}
