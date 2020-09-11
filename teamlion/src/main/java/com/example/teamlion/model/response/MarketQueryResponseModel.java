package com.example.teamlion.model.response;

import org.springframework.data.mongodb.core.mapping.Field;

import com.example.teamlion.entity.Stock;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarketQueryResponseModel {
    private String symbol;
    private String open;
    private String high;
    private String low;
    private String close;
    @Field("adj close")
    private String adjClose;
    private String volume;
    private String date;
    
    public MarketQueryResponseModel(Stock s) {
    	symbol = s.getSymbol();
    	open = s.getOpen();
    	high = s.getHigh();
    	low = s.getLow();
    	close = s.getClose();
    	adjClose = s.getAdjClose();
    	volume = s.getVolume();
    	date = s.getDate();
    }
}
