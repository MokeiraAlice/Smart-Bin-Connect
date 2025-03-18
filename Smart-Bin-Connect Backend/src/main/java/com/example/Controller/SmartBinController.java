package com.example.Controller;

import java.util.List;

import com.example.Entity.SmartBin;
import com.example.Service.SmartBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/bins")
@CrossOrigin(origins = "*")
public class SmartBinController {

    @Autowired
    private SmartBinService binService;

    @GetMapping
    public ResponseEntity<List<SmartBin>> getAllBins() {
        return ResponseEntity.ok(binService.getAllBins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmartBin> getBinById(@PathVariable Long id) {
        return ResponseEntity.ok(binService.getBinById(id));
    }

    @GetMapping("/fill-level")
    public ResponseEntity<List<SmartBin>> getBinsByFillLevel(@RequestParam double threshold) {
        return ResponseEntity.ok(binService.getBinsByFillLevelThreshold(threshold));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SmartBin>> getBinsByStatus(@PathVariable SmartBin.BinStatus status) {
        return ResponseEntity.ok(binService.getBinsByStatus(status));
    }

    @PostMapping
    public ResponseEntity<SmartBin> createBin(@RequestBody SmartBin bin) {
        return new ResponseEntity<>(binService.createBin(bin), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/fill-level")
    public ResponseEntity<SmartBin> updateBinFillLevel(
            @PathVariable Long id,
            @RequestBody double fillLevel) {
        return ResponseEntity.ok(binService.updateBinFillLevel(id, fillLevel));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SmartBin> updateBinStatus(
            @PathVariable Long id,
            @RequestBody SmartBin.BinStatus status) {
        return ResponseEntity.ok(binService.updateBinStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBin(@PathVariable Long id) {
        binService.deleteBin(id);
        return ResponseEntity.noContent().build();
    }
}