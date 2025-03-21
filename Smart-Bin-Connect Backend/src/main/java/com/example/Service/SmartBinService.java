package com.example.Service;


import java.time.LocalDateTime;
import java.util.List;

import com.example.Entity.SmartBin;
import com.example.Repository.SmartBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartBinService {

    @Autowired
    private SmartBinRepository binRepository;

    public List<SmartBin> getAllBins() {
        return binRepository.findAll();
    }

    public SmartBin getBinById(Long id) {
        return binRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bin not found with id: " + id));
    }

    public List<SmartBin> getBinsByFillLevelThreshold(double threshold) {
        return binRepository.findByFillLevelGreaterThanEqual(threshold);
    }

    public List<SmartBin> getBinsByStatus(SmartBin.BinStatus status) {
        return binRepository.findByStatus(status);
    }

    public SmartBin updateBinFillLevel( Long id, double fillLevel) {
        SmartBin bin = getBinById(id);
        bin.setFillLevel(fillLevel);
        bin.setLastUpdated(LocalDateTime.now());

        // Update bin status based on fill level
        if (fillLevel >= 90) {
            bin.setStatus(SmartBin.BinStatus.FULL);
        } else if (fillLevel >= 70) {
            bin.setStatus(SmartBin.BinStatus.WARNING);
        } else {
            bin.setStatus(SmartBin.BinStatus.NORMAL);
        }

        return binRepository.save(bin);
    }

    public SmartBin updateBinStatus(Long id, SmartBin.BinStatus status) {
        SmartBin bin = getBinById(id);
        bin.setStatus(status);
        bin.setLastUpdated(LocalDateTime.now());
        return binRepository.save(bin);
    }

    public SmartBin createBin(SmartBin bin) {
        bin.setLastUpdated(LocalDateTime.now());

        // Set initial status based on fill level
        if (bin.getFillLevel() >= 90) {
            bin.setStatus(SmartBin.BinStatus.FULL);
        } else if (bin.getFillLevel() >= 70) {
            bin.setStatus(SmartBin.BinStatus.WARNING);
        } else {
            bin.setStatus(SmartBin.BinStatus.NORMAL);
        }

        return binRepository.save(bin);
    }

    public void deleteBin(Long id) {
        binRepository.deleteById(id);
    }
}
