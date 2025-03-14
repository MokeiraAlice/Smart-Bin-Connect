package com.example.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.smartbin.model.SmartBin;

public interface SmartBinRepository extends MongoRepository<SmartBin, String> {
    @Query("{ 'fillLevel': { $gte: ?0 } }")
    List<SmartBin> findByFillLevelGreaterThanEqual(double threshold);

    List<SmartBin> findByStatus(SmartBin.BinStatus status);

    List<SmartBin> findByBinType(SmartBin.BinType binType);
}