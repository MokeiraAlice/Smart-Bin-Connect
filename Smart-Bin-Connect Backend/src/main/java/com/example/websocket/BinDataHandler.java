package com.example.websocket;

import com.example.Entity.SmartBin;
import com.example.Service.SmartBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BinDataHandler {

    @Autowired
    private SmartBinService binService;

    @MessageMapping("/bin-updates")
    @SendTo("/topic/bins")
    public BinUpdateMessage handleBinUpdates(BinUpdateMessage message) {
        // Find the bin by ID
        SmartBin bin = binService.getBinById(message.getId());

        // Update bin properties
        bin.setFillLevel(message.getFillLevel());
        bin.setStatus(message.getStatus());
        bin.setLastUpdated(message.getLastUpdated());

        // Save the updated bin
        // This will update both fill level and status based on the message
        binService.updateBinFillLevel(message.getId(), message.getFillLevel());


        // Return the message to be broadcast to subscribers
        return message;
    }
}