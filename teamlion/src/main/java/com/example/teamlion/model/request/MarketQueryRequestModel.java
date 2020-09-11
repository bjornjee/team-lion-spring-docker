package com.example.teamlion.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketQueryRequestModel {
	private String symbol;
	private String start;
	private String end;
	private String token;
	
}
