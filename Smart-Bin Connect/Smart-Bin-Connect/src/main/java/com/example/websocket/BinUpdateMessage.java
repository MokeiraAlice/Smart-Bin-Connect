package com.example.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.smartbin.model.SmartBin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinUpdateMessage {
    private String id;
    private double fillLevel;
    private LocalDateTime lastUpdated;
    private SmartBin.BinStatus status;
}