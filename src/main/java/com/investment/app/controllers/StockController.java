package com.investment.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.app.entities.Stock;
import com.investment.app.http.GenericResponse;
import com.investment.app.services.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
	
	@Autowired private StockService stockService;
	
	@PostMapping("/add")
	private GenericResponse<?> addStock(@RequestBody Stock stock) {
		return stockService.addStock(stock);
	}

}
