package com.cs.trade.trademanager.validatons;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trade.trademanager.config.ConfigurationService;
import com.cs.trade.trademanager.model.Trade;
import com.cs.trade.trademanager.model.ValidationError;
import com.cs.trade.trademanager.model.ValidationResult;

@Service
public class OptionTypeValidator implements TradeValidaton {

	 private static Logger log = LoggerFactory.getLogger(OptionTypeValidator.class);


	@Autowired
	private ConfigurationService configService;
	
	@Autowired
	private ValidationResult validationResult;
	
	@Autowired
	private ValidationError validationError;

    @Override
    public ValidationResult validate(Trade trade) {


        if (!configService.getOptionType().stream().filter(type -> StringUtils.equalsIgnoreCase(type, trade.getType())).findFirst()
				.isPresent())
			return validationResult;
		if (!Stream.concat(configService.getEuropeanStyles().stream(), configService.getAmericanStyles().stream())
				.filter(style -> StringUtils.equalsIgnoreCase(style, trade.getStyle())).findFirst().isPresent()) {
			log.warn("Option style is not valid for trade {}", trade);
			validationError.setField("style");
			validationError.setMessage("Option style is not valid");
			validationResult.withError(validationError);
		} else {
			if (trade.getDeliveryDate() == null) {
				log.warn("deliveryDate is missing for trade {}", trade);
				validationError.setField("deliveryDate");
				validationError.setMessage("deliveryDate is missing");
				validationResult.withError(validationError);
			} else {
				if (trade.getExpiryDate() == null) {
					log.warn("expiry date is missing for trade {}", trade);
					validationError.setField("expiryDate");
					validationError.setMessage("expiry date is missing");
					validationResult.withError(validationError);
				} else if (!trade.getExpiryDate().before(trade.getDeliveryDate())) {
					log.warn("expiry date should be before delivery date for trade {}", trade);
					validationError.setField("expiryDate");
					validationError.setMessage("expiry date should be before delivery date");
					validationResult.withError(validationError);
				}
				if (trade.getPremiumDate() == null) {
					log.warn("premium date is missing for trade {}", trade);
					validationError.setField("premiumDate");
					validationError.setMessage("premium date is missing");
					validationResult.withError(validationError);
				} else if (!trade.getPremiumDate().before(trade.getDeliveryDate())) {
					log.warn("premium date should be before delivery date for trade {}", trade);
					validationError.setField("premiumDate");
					validationError.setMessage("premium date should be before delivery date");
					validationResult.withError(validationError);
				}
			}
			if (configService.getAmericanStyles().contains(trade.getStyle()))
				if (trade.getExcerciseStartDate() == null) {
					log.warn("excerciseStartDate is missing for trade {}", trade);
					validationError.setField("excerciseStartDate");
					validationError.setMessage("excerciseStartDate is missing");
					validationResult.withError(validationError);
				} else {
					if (trade.getTradeDate() == null) {
						log.warn("tradeDate is missing for trade {}", trade);
						validationError.setField("tradeDate");
						validationError.setMessage("tradeDate is missing");
						validationResult.withError(validationError);
					} else if (!trade.getExcerciseStartDate().after(trade.getTradeDate())) {
						log.warn("excerciseStartDate should be after trade date for trade {}", trade);
						validationError.setField("excerciseStartDate");
						validationError.setMessage("excerciseStartDate should be after trade date");
						validationResult.withError(validationError);
					}
					if (trade.getExpiryDate() == null) {
						log.warn("expiryDate is missing for trade {}", trade);
						validationError.setField("expiryDate");
						validationError.setMessage("expiryDate is missing");
						validationResult.withError(validationError);
					} else if (!trade.getExcerciseStartDate().before(trade.getExpiryDate())) {
						log.warn("excerciseStartDate should be before trade date for trade {}", trade);
						validationError.setField("excerciseStartDate");
						validationError.setMessage("excerciseStartDate should be before trade date");
						validationResult.withError(validationError);
					}
				}
		}
		return validationResult;
    }

   
}
