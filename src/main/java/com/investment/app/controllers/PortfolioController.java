package com.investment.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.app.entities.Portfolio;
import com.investment.app.http.GenericResponse;
import com.investment.app.services.PortfolioService;

@RestController 
@RequestMapping(value = "/portfolio")
@CrossOrigin(allowedHeaders = "*")
public class PortfolioController {
	
	@Autowired private PortfolioService portfolioService;
	
	@PostMapping("/create")
	public GenericResponse<?> createPorfolio(@RequestBody Portfolio portfolio) {
		return portfolioService.createPortfolio(portfolio);
		
	}
	
	@GetMapping("/getByEmail/{email}")
	public GenericResponse<?> getPortfoliosByEmail(@PathVariable("email") String email) {
		return portfolioService.getPortfolioByOwnersEmail(email);
	}
	
	@GetMapping("/getInvestment/{id}/{type}")
	public GenericResponse<?> getInvestmentByPortfolioId(@PathVariable("id") String portfolioId,@PathVariable("type") String portfolioType) {
		return portfolioService.getInvestmentByPortfolioIdAndType(portfolioId,portfolioType);
	}
	
	@GetMapping("/deleteInvestment/{id}/{type}")
	public GenericResponse<?> deleteInvestmentById(@PathVariable("id") String id,@PathVariable("type") String portfolioType) {
		return portfolioService.deleteInvestmentById(id,portfolioType);
	}

}
