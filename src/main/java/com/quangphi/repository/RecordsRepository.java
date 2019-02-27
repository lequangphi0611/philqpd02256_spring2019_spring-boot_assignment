package com.quangphi.repository;

import com.quangphi.entity.Records;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsRepository extends CrudRepository<Records,Integer> {

    
    
}