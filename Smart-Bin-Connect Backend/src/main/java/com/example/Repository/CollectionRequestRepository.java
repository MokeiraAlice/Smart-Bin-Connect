package com.example.Repository;


import java.util.List;

import com.example.Entity.CollectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CollectionRequestRepository extends JpaRepository<CollectionRequest, String> {
    List<CollectionRequest> findByStatus(CollectionRequest.RequestStatus status);

    List<CollectionRequest> findByUserId(String userId);
}
