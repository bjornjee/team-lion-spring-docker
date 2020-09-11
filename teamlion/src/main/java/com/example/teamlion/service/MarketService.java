package com.example.teamlion.service;

import com.example.teamlion.entity.AccountEntity;
import com.example.teamlion.entity.Stock;
import com.example.teamlion.model.request.MarketQueryRequestModel;
import com.example.teamlion.model.response.MarketQueryResponseModel;
import com.example.teamlion.repository.AccountRepository;
import com.example.teamlion.repository.MarketRepository;
import com.example.teamlion.utils.LionUtil;
import com.example.teamlion.utils.model.UtilModel;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MarketService {


	@Autowired
	private MarketRepository marketRepository;
	@Autowired
	private LionUtil utils;
	@Autowired
	private AccountRepository accountRepository;

	@Value("${spring.data.mongodb.uri}")
	private String mongoUrl;
	@Value("${pythonFilename}")
	private String pythonFilename;
	@Value("${pythonExeFilePath}")
	private String pythonExe;

	public List<MarketQueryResponseModel> getStockDataPython(MarketQueryRequestModel model) {
		String symbol = model.getSymbol();
		String start = model.getStart();
		String end = model.getEnd();
		String token = model.getToken();
		// Check if user is registered
		UtilModel m = utils.decoder(token);
		if (!registered(m)) {
			log.info("User not registered!");
			return null;
		}
		//Check if stock exists in database
		List<Stock> dates = marketRepository.getStockBySymbolInRange(symbol,start,end);
		log.info(dates.toString());
		if (dates.isEmpty()) {
			//if range does not exist, fetch everything
			runPythonScript(symbol,start,end);
		} else {
			String dbStartDate = String.valueOf(dates.get(0).getDate());
			String dbEndDate = String.valueOf(dates.get(dates.size()-1).getDate());
			//If database has request, fetch and return
			if (dbStartDate.equals(start) && dbEndDate.equals(end)) {
				return marketRepository.getStockBySymbolInRange(symbol,start,end).stream().map(MarketQueryResponseModel::new).distinct().collect(Collectors.toList());
			}
			//fetch missing data
			if (!dbStartDate.equals(start)) {
				//Decrement date to prevent duplicates
				runPythonScript(symbol,start,decrementDate(dbStartDate));
			}
			if (!dbEndDate.equals(end)) {
				//Increment date to prevent duplicates
				runPythonScript(symbol,incrementDate(dbEndDate),end);
			}
		}
		//Sleep to make sure python script runs before fetching
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Fetch data
		return marketRepository.getStockBySymbolInRange(symbol,start,end).stream().map(MarketQueryResponseModel::new).distinct().collect(Collectors.toList());

	}

	private boolean registered(UtilModel m) {
		if (m ==null) {
			return false;
		}
		String username = m.getUsername();
		String password = m.getPassword();
		List<AccountEntity> arr = accountRepository.getOneByUsernameAndPassword(username, password);
		if (arr.isEmpty()) {
			return false;
		}
		return true;
	}

	private void runPythonScript(String symbol, String start, String end) {
		log.info("In runPythonScript()");
		try  {
			String cmd = String.format("%s %s %s %s %s %s",
					pythonExe,
					pythonFilename,
					mongoUrl,
					symbol,
					start,
					end);
			log.info(cmd);
			Process p = Runtime.getRuntime().exec(cmd);
//


		} catch (IOException ex) {
			log.info(ex.toString());
		}
	}
	private String incrementDate(String date) {
		return LocalDate.parse(date).plusDays(1).toString();
	}

	private String decrementDate(String date) {
		return LocalDate.parse(date).minusDays(1).toString();
	}

	private void readPythonDataFromStdin(Process p) {
		// try to read data
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String s;
		// put data in array
		ArrayList<String> data = new ArrayList<>();
		try {
			while ((s = stdInput.readLine()) != null) {
				data.add(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// retrieve data
		String stockData = data.get(data.size() - 1);
		if (stockData != "[]") {
			log.info(stockData);
			stockData = stockData.substring(2, stockData.length() - 2);
			log.info(stockData);
			if (stockData.contains("},{")) {
				Arrays.stream(stockData.split("},{")).forEach(System.out::println);
			}

			// List<String> arr =
			// Arrays.stream(stockData.split("},")).map(x->x+"}").collect(Collectors.toList());
		}
	}

}
