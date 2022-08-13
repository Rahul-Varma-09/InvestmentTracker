package com.investment.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investment.app.entities.FNOReport;
import com.investment.app.entities.Stock;
import com.investment.app.http.GenericResponse;
import com.investment.app.repositories.StockRepository;

@Service
public class StockService {
	
	@Autowired private StockRepository stockRepo;
	
	public GenericResponse<?> addStock(Stock stock) {
		
		try {
			Stock existingStock = stockRepo.findByNameAndPortfolioType(stock.getName(),stock.getPortfolioType());
			
			if (existingStock != null) {
				return GenericResponse.builder().code("ERR").body(new Stock()).message("Stock Already Exist in The Portfolio").build();
			}
			
			Stock savedStock = stockRepo.save(stock);
			
			if (savedStock == null) {
				return GenericResponse.builder().code("ERR").body(savedStock).message("ERROR in Saving Stock").build();
			}
			
			return GenericResponse.builder().code("OK").body(savedStock).message("Stock Saved SuccessFully").build();

		} catch (Exception e) {
			return GenericResponse.builder().code("ERR").body(new FNOReport()).message("Something Went Wrong").build();
		}
	
		
	}

}
