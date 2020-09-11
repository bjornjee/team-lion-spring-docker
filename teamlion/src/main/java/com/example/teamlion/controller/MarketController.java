package com.example.teamlion.controller;

import com.example.teamlion.model.request.MarketQueryRequestModel;
import com.example.teamlion.model.response.MarketQueryResponseModel;
import com.example.teamlion.service.MarketService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market/")
public class MarketController {
	private Logger logger = LoggerFactory.getLogger(MarketController.class);
	
    @Autowired
    MarketService marketService;

    @GetMapping("/")
    // Date should have the format YYYY-MM-DD
    public ResponseEntity<?> getStock( @RequestParam(required=true) String symbol, @RequestParam(required=true) String start, @RequestParam(required=true) String end, @RequestParam(required=true) String token) {
        List<MarketQueryResponseModel> stocks = marketService.getStockDataPython(symbol, start,end,token);
        return ResponseEntity.ok().body(stocks);
    }
    
    @PostMapping("/dummy")
    // Date should have the format YYYY-MM-DD
    public ResponseEntity<?> test(@RequestBody MarketQueryRequestModel model) {
    	logger.info("In /api/market/dummy");
    	logger.info(model.toString());
        return ResponseEntity.ok().body(model.toString());
    }
    
    @GetMapping("/test")
    // Date should have the format YYYY-MM-DD
    public ResponseEntity<?> runTest(@RequestBody MarketQueryRequestModel model) {
    	String symbol = model.getSymbol();
    	String start = model.getStart();
    	String end = model.getEnd();
        marketService.runPythonScriptRest(symbol, start, end);
        return ResponseEntity.ok().build();
    }
    
    
}
