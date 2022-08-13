package com.investment.app.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.investment.app.entities.Portfolio;

@Repository 
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
	
	Portfolio findByPortfolioNameAndCreatedByAndPortfolioType(String portFolioName,String createdBy,String portfolioType);

	List<Portfolio> findByPortfolioOwnersIn(String ownersEmail);

}
