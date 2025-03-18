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
@Table(name = "smart_bins")
public class SmartBin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Location location;

    private double fillLevel;
    private LocalDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    private BinStatus status;

    @Enumerated(EnumType.STRING)
    private BinType binType;

    public enum BinStatus {
        NORMAL, WARNING, FULL
    }

    public enum BinType {
        GENERAL, RECYCLABLE, ORGANIC
    }

    @Data
    @Embeddable
    public static class Location {
        private double latitude;
        private double longitude;
        private String address;
    }
}