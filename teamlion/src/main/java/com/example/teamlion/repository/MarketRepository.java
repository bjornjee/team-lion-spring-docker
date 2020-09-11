package com.example.teamlion.repository;

import com.example.teamlion.entity.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MarketRepository extends MongoRepository<Stock,Long> {
    
	@Query("{symbol:?0,date:{$gte:?1,$lte:?2}}")
    List<Stock> getStockBySymbolInRange(String symbol, String start, String end);
}
