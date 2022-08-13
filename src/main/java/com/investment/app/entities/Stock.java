package com.investment.app.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "Stock")
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class Stock {
	
	@Id
	private String id;
	private String name;
	private Long buyAvgPrice;
	private Long quantity;
	private Long investedAmtInStock;
	private String portfolioType;

}
