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

@Document("User")
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class User {
	
	@Id
	private String id;
	private String email;
	private String otp;
	@Builder.Default
	private Integer requestType = 0;
	@CreatedDate
	private LocalDate createdDate;
	@LastModifiedDate
	private LocalDate lastModifiedDate;

}
