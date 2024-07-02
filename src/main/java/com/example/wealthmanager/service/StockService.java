package com.example.wealthmanager.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wealthmanager.entity.Stock;
import com.example.wealthmanager.entity.Transactions;
import com.example.wealthmanager.repo.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRep;
    
    @Autowired
    private TransactServ transactService;

    private OkHttpClient client;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public StockService() {
        this.client = new OkHttpClient();
    }
    
    public String addStock(Stock stock,String email) {
    	String message;
    	if(transactService.checkBalance(email, stock.getTotalPrice())) {
    		double marketPrice=getCurrentStockPrice(stock.getStockName());
    	
    		stock.setEmail(email);	
    		stock.setTotalPrice(stock.getQuantity() * stock.getOriginalPrice());
    	
    		stock.setMarketPrice(marketPrice);
    		stock.setAmount(marketPrice*stock.getQuantity());

			Transactions transaction=new Transactions();
			transaction.setAmount(stock.getTotalPrice());
			transaction.setSubject("Stocks "+stock.getStockName());
			transaction.setType("Debit");
			transaction.setDate(LocalDate.now());
			message=transactService.addTransaction(email,transaction);
        
			stockRep.save(stock);
		}
		else
			message="Insufficient Balance";
		
        return message;
    }
    
    public void updateStockPrice(Stock stock) {
        stockRep.save(stock);
    }

    public String deleteStock(String email, Stock stock) {
        Transactions transaction=new Transactions();
        transaction.setAmount(stock.getAmount());
        transaction.setSubject("Stocks "+stock.getStockName());
        transaction.setType("Credit");
        transaction.setDate(LocalDate.now());
        String message=transactService.addTransaction(email,transaction);
        
        stockRep.deleteById(stock.getId());
        return message;
    }
    
    public double getInvestment(String email) {
    	List<Stock> stocks = getStocksByUser(email);
    	double totalInvest = stocks.stream().mapToDouble(Stock::getTotalPrice).sum();
    	return totalInvest;
    }
    
    public double getReturns(String email) {
    	List<Stock> stocks = getStocksByUser(email);
    	double totalReturns=0.0;
    	for(Stock stock:stocks) {
    		totalReturns+=(getCurrentStockPrice(stock.getStockName())*stock.getQuantity());
    	}
    	return totalReturns;
    }

    public Stock getStockByStockName(String stockName, String email) {
		Optional<Stock> stock=stockRep.getStockByStockNameAndEmail(stockName,email);
		return stock.get();
	}
    public List<Stock> getStocksByUser(String email) {
        return stockRep.findByEmail(email);
    }
    
    public double getCurrentStockPrice(String stockSymbol) {
        String apiUrl = "https://realstonks.p.rapidapi.com/stocks/" + stockSymbol + "/advanced";
        Request request = new Request.Builder()
                .url(apiUrl).get()
                .addHeader("x-rapidapi-key", "04e82fdf70msh07adc6b7aec0fb4p189413jsne01b6243b4ee")
                .addHeader("x-rapidapi-host", "realstonks.p.rapidapi.com").build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
            	JsonNode jsonResponse = objectMapper.readTree(response.body().string());
            	double price = jsonResponse.path("lastPrice").asDouble();
            	BigDecimal priceBigDecimal = new BigDecimal(price).setScale(3, RoundingMode.HALF_UP);
            	double limitedPrice = priceBigDecimal.doubleValue();
            	
                return limitedPrice;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}