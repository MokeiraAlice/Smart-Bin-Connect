package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.smartbin.model.SmartBin;
import com.smartbin.service.SmartBinService;

@Controller
public class BinDataHandler {

    @Autowired
    private SmartBinService binService;

    @MessageMapping("/bin-updates")
    @SendTo("/topic/bins")
    public BinUpdateMessage handleBinUpdates(BinUpdateMessage message) {
        // Update the bin in the database
        SmartBin bin = bin