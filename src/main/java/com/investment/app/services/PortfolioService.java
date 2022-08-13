package com.investment.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investment.app.entities.Portfolio;
import com.investment.app.http.GenericResponse;
import com.investment.app.repositories.PortfolioRepository;

@Service
public class PortfolioService {
	
	@Autowired private PortfolioRepository portfolioRepository;
	
	public GenericResponse<?> createPortfolio(Portfolio portfolio) {
		
		Portfolio existingPortfolio = portfolioRepository.findByPortfolioNameAndCreatedByAndPortfolioType(portfolio.getPortfolioName(),portfolio.getCreatedBy(),portfolio.getPortfolioType());
		
		if(existingPortfolio != null) {
			return GenericResponse.builder().message("Portfolio with Same Name And Type Already Exists").code("ERR").body(existingPortfolio).build();
		}
		
		if (!portfolio.getPortfolioOwners().contains(portfolio.getCreatedBy())) {
			portfolio.getPortfolioOwners().add(portfolio.getCreatedBy());
		}
		
	
		Portfolio savedPortfolio = portfolioRepository.save(portfolio);
		
		if (savedPortfolio == null) {
			return GenericResponse.builder().message("Error in creating portfolio").code("ERR").body(null).build();
		}
		
		return GenericResponse.builder().message("Portfolio created Successfully").code("OK").body(savedPortfolio).build();
	}

	public GenericResponse<?> getPortfolioByOwnersEmail(String email) {
		if (email != null && email != "") {
			
			List<Portfolio> portfolios = portfolioRepository.findByPortfolioOwnersIn(email);
			
			if (portfolios.size() > 0) {
				return GenericResponse.builder().message("Portfolio Fetched Successfully").code("OK").body(portfolios).build();
			}
			
			return GenericResponse.builder().message("Error in fetching portfolios").code("ERR").body(null).build();
		}
		
		return GenericResponse.builder().message("Incorrect Email Id").code("ERR").body(null).build();
	}

}