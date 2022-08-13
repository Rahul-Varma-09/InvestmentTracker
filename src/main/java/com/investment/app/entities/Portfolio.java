package com.investment.app.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "Portfolio")
@NoArgsConstructor @AllArgsConstructor @Data @Builder
public class Portfolio {
	
	@Id
	private String id;
	private String portfolioName;
	private String portfolioType;
	private String createdBy;
	@Builder.Default
	private List<String> portfolioOwners = new ArrayList();
	@Builder.Default
	List<Object> objList = new ArrayList();
	@CreatedDate
	private LocalDate createdDate;
	@LastModifiedDate
	private LocalDate modifiedDate;
	

}
