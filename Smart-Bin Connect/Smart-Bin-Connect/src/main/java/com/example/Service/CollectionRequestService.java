package com.example.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbin.model.CollectionRequest;
import com.smartbin.repository.CollectionRequestRepository;

@Service
public class CollectionRequestService {

    @Autowired
    private CollectionRequestRepository requestRepository;

    public List<CollectionRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public CollectionRequest getRequestById(String id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection request not found with id: " + id));
    }

    public List<CollectionRequest> getRequestsByStatus(CollectionRequest.RequestStatus status) {
        return requestRepository.findByStatus(status);
    }

    public List<CollectionRequest> getRequestsByUser(String userId) {
        return requestRepository.findByUserId(userId);
    }

    public CollectionRequest createRequest(CollectionRequest request) {
        request.setRequestTime(LocalDateTime.now());
        request.setStatus(CollectionRequest.RequestStatus.PENDING);
        return requestRepository.save(request);
    }

    public CollectionRequest updateRequestStatus(String id, CollectionRequest.RequestStatus status) {
        CollectionRequest request = getRequestById(id);
        request.setStatus(status);
        return requestRepository.save(request);
    }

    public CollectionRequest scheduleRequest(String id, LocalDateTime scheduledTime) {
        CollectionRequest request = getRequestById(id);
        request.setScheduledTime(scheduledTime);
        request.setStatus(CollectionRequest.RequestStatus.SCHEDULED);
        return requestRepository.save(request);
    }

    public void deleteRequest(String id) {
        requestRepository.deleteById(id);
    }
}
