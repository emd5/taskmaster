package com.lizmahoney401.Taskmaster;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface TaskmasterRepository extends CrudRepository<Taskmaster, String> {
    Optional<Taskmaster> findById(String id);
}
