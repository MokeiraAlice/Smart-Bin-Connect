package com.example.websocket;

import com.example.Entity.SmartBin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinUpdateMessage {
    private Long id;
    private double fillLevel;
    private LocalDateTime lastUpdated;
    private SmartBin.BinStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(double fillLevel) {
        this.fillLevel = fillLevel;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public SmartBin.BinStatus getStatus() {
        return status;
    }

    public void setStatus(SmartBin.BinStatus status) {
        this.status = status;
    }
}