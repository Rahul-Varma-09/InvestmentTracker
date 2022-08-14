package com.investment.app.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "FNOReports")
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class FNOReport {
	
	@Id
	private String id;
	private LocalDate tradedDate;
    private String tradeResult;
    private Double grossAmount;
    private Double tradeCharges;
    private Double netAmount;
    private Double thirtyPercentShare;
    private Double seventyPercentShare;
    private Double finalInHandProfit;
    private String portfolioId;
    @CreatedDate
    private LocalDate createdDate;
    @LastModifiedDate
    private LocalDate modifiedDate;
    
	
	
}
