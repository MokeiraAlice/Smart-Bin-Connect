package com.example.Repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartbin.model.CollectionRequest;

public interface CollectionRequestRepository extends MongoRepository<CollectionRequest, String> {
    List<CollectionRequest> findByStatus(CollectionRequest.RequestStatus status);

    List<CollectionRequest> findByUserId(String userId);
}
