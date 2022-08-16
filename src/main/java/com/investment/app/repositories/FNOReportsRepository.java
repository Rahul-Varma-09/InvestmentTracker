package com.investment.app.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.investment.app.entities.FNOReport;

@Repository
public interface FNOReportsRepository extends MongoRepository<FNOReport, String> {
	
	@Query("{'tradedDate' : { $gte: ?0, $lte: ?1 } , 'portfolioId' : ?2 } ") 
	List<FNOReport> findByTradedDateAndPortfolioId(LocalDate date1 ,LocalDate date2,String portfolioId);
	
	List<FNOReport> findByTradeResultAndPortfolioId(String result,String portfolioId);
	
	FNOReport findByTradedDateAndPortfolioId(LocalDate tradedDate,String portfolioId);

	List<FNOReport> findByTradedDateAndTradeResultAndPortfolioId(LocalDate date1,String result,String portfolioId);
	
	@Query("{'tradedDate' : { $gte: ?0, $lte: ?1 } , 'portfolioId' : ?2 , 'tradeResult' : ?3 } ") 
	List<FNOReport> findByTradedDateAndPortfolioIdAndTradeResult(LocalDate date1 ,LocalDate date2,String portfolioId,String result);
	
	List<FNOReport> findByPortfolioId(String portfolioId);
	
}
