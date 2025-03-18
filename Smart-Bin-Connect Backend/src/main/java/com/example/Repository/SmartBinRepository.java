package com.example.Repository;

import java.util.List;

import com.example.Entity.SmartBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SmartBinRepository extends JpaRepository<SmartBin, Long> {
    @Query("SELECT b FROM SmartBin b WHERE b.fillLevel >= :threshold")
    List<SmartBin> findByFillLevelGreaterThanEqual(@Param("threshold") double threshold);

    // These derived query methods can stay the same
    List<SmartBin> findByStatus(SmartBin.BinStatus status);

    List<SmartBin> findByBinType(SmartBin.BinType binType);
}