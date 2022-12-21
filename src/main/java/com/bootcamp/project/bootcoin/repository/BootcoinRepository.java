package com.bootcamp.project.bootcoin.repository;

import com.bootcamp.project.bootcoin.entity.BootcoinEntity;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BootcoinRepository extends ReactiveCrudRepository<BootcoinEntity, ObjectId> {
}
