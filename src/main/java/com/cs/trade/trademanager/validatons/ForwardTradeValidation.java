package com.cs.trade.trademanager.validatons;

import static java.time.temporal.ChronoUnit.DAYS;

import java.text.ParseException;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trade.trademanager.config.ConfigurationService;
import com.cs.trade.trademanager.model.Trade;
import com.cs.trade.trademanager.model.ValidationError;
import com.cs.trade.trademanager.model.ValidationResult;
@Service
public class ForwardTradeValidation{
	
	private static Logger log = LoggerFactory.getLogger(ForwardTradeValidation.class);
	@Autowired
	private ConfigurationService configService;

	@Autowired
	private ValidationResult validationResult;
	
	@Autowired
	private ValidationError validationError;


	public ValidationResult validate(Trade trade) {

		try {
			if (DAYS.between(
					configService.getTodayDateString().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
					trade.getValueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) > 2)
				return validationResult;
		} catch (ParseException e) {
			log.error("SpotTradeValidation error:{}", e.getMessage());
		}
		log.warn("On forward trades valueDate should be more than 2 days for trade {}", trade);
		validationError.setField("valueDate");
		validationError.setMessage("On forward trades valueDate should be more than 2 days");
		validationResult.withError(validationError);
		return validationResult;
	}
}
