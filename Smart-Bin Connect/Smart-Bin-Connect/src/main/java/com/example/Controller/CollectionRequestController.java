package com.example.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartbin.model.CollectionRequest;
import com.smartbin.service.CollectionRequestService;

@RestController
@RequestMapping("/api/collection-requests")
@CrossOrigin(origins = "*")
public class CollectionRequestController {

    @Autowired
    private CollectionRequestService requestService;

    @GetMapping
    public ResponseEntity<List<CollectionRequest>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionRequest> getRequestById(@PathVariable String id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CollectionRequest>> getRequestsByStatus(
            @PathVariable CollectionRequest.RequestStatus status) {
        return ResponseEntity.ok(requestService.getRequestsByStatus(status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CollectionRequest>> getRequestsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(requestService.getRequestsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<CollectionRequest> createRequest(@RequestBody CollectionRequest request) {
        return new ResponseEntity<>(requestService.createRequest(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CollectionRequest> updateRequestStatus(
            @PathVariable String id,
            @RequestBody Map<String, CollectionRequest.RequestStatus> statusUpdate) {
        return ResponseEntity.ok(
                requestService.updateRequestStatus(id, statusUpdate.get("status")));
    }

    @PatchMapping("/{id}/schedule")
    public ResponseEntity<CollectionRequest> scheduleRequest(
            @PathVariable String id,
            @RequestBody Map<String, String> scheduleUpdate) {
        LocalDateTime scheduledTime = LocalDateTime.parse(scheduleUpdate.get("scheduledTime"));
        return ResponseEntity.ok(requestService.scheduleRequest(id, scheduledTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}