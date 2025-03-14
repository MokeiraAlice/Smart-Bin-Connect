package com.example.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "collection_requests")
public class CollectionRequest {
    @Id
    private String id;

    private String userId;
    private String address;
    private Location location;
    private LocalDateTime requestTime;
    private LocalDateTime scheduledTime;
    private RequestStatus status;
    private String notes;

    public enum RequestStatus {
        PENDING, SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    }

    @Data
    public static class Location {
        private double latitude;
        private double longitude;
    }