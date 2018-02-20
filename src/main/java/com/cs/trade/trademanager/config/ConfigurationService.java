package com.cs.trade.trademanager.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;


@Configuration
@Getter
@ConfigurationProperties (prefix = "cs")
public class ConfigurationService {

	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
	
	
	private List<String> validCustomers =new ArrayList<>();
	private List<String> legalEntities =new ArrayList<>();
	private List<String> europeanStyles =new ArrayList<>();
	private List<String> optionType =new ArrayList<>();
	private List<String> americanStyles =new ArrayList<>();
	private List<String> spotTypes =new ArrayList<>();
	private List<String> forwardTypes =new ArrayList<>();
	
	
	@Value("${cs.todayDate}")
	private String todayDate;
	
	private Date formattedTodaysDate;
	
  public Date getTodayDateString() throws ParseException {
	        return dateFormatter.parse(todayDate);
	    }
  
 
}
