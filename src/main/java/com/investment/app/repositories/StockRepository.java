package com.investment.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.investment.app.entities.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock, String>{

	Stock findByNameAndPortfolioType(String name,String type);
	
	Stock findByPortfolioType(String type);
	
}
