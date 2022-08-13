package com.investment.app.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.investment.app.entities.FNOReport;

@Repository
public interface FNOReportsRepository extends MongoRepository<FNOReport, String> {
	
	@Query("{'tradedDate' : { $gte: ?0, $lte: ?1 } }") 
	List<FNOReport> findByTradedDate(LocalDate date1 ,LocalDate date2);
	
	List<FNOReport> findByTradeResult(String result);
	
	FNOReport findByTradedDate(LocalDate tradedDate);

	List<FNOReport> findByTradedDateAndTradeResult(LocalDate date1,String result);
	
	@Query("{'tradedDate' : { $gte: ?0, $lte: ?1 } }") 
	List<FNOReport> findByTradedDateAndTradeResult(LocalDate date1 ,LocalDate date2,String result);
	
}
