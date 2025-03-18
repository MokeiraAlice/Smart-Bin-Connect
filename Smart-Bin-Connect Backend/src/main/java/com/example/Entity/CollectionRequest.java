package com.example.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Embedded;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Data;

@Data
@Entity
@Table(name = "collection_requests")
public class CollectionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String address;

    @Embedded
    private Location location;

    private LocalDateTime requestTime;
    private LocalDateTime scheduledTime;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String notes;

    public enum RequestStatus {
        PENDING, SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    }

    @Data
    @Embeddable
    public static class Location {
        private double latitude;
        private double longitude;
    }
}