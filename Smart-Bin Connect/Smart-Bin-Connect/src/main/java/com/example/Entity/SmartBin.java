package com.example.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "smart_bins")
public class SmartBin {
    @Id
    private String id;

    private Location location;
    private double fillLevel;
    private LocalDateTime lastUpdated;
    private BinStatus status;
    private BinType binType;

    public enum BinStatus {
        NORMAL, WARNING, FULL
    }

    public enum BinType {
        GENERAL, RECYCLABLE, ORGANIC
    }

    @Data
    public static class Location {
        private double latitude;
        private double longitude;
        private String address;
    }
}
