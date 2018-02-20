package com.cs.trade.trademanager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cs.trade.trademanager.validatons.CurrencyHoliday;
@Service
public class CurrencyHolidayService implements CurrencyHoliday{
	
	private static Logger log = LoggerFactory.getLogger(CurrencyHolidayService.class);
	
	@Override
	public Optional<Set<Date>> fetchHolidays(Currency currency) {
			SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd");
            Currency USD = Currency.getInstance("USD");

            if (USD.equals(currency))
				try {
					return Optional.of(new HashSet<>(Arrays.asList(date_formatter.parse("2017-01-01"),
							date_formatter.parse("2017-01-02"), date_formatter.parse("2017-02-20"))));
				} catch (ParseException e) {
					log.error("CurrencyHolidayService Error :{}",e.getMessage());
				}


            return Optional.empty();
        };
	


}
