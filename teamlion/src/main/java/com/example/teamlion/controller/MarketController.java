package com.example.teamlion.controller;

import com.example.teamlion.model.request.MarketQueryRequestModel;
import com.example.teamlion.model.response.MarketQueryResponseModel;
import com.example.teamlion.service.MarketService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market/")
public class MarketController {
    @Autowired
    MarketService marketService;

    @GetMapping("/")
    // Date should have the format YYYY-MM-DD
    public ResponseEntity<?> getStock(@RequestBody MarketQueryRequestModel model) {
        List<MarketQueryResponseModel> stocks = marketService.getStockDataPython(model);
        return ResponseEntity.ok().body(stocks);
    }
}
