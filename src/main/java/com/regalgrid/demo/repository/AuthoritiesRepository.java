package com.regalgrid.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.regalgrid.demo.model.Authorities;

@Repository
public interface AuthoritiesRepository extends MongoRepository<Authorities,String>{
    
}
