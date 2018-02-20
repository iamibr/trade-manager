package com.cs.trade.trademanager.validatons;


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
public class SpotForwardValidaton implements TradeValidaton {
    private static Logger log = LoggerFactory.getLogger(SpotForwardValidaton.class);

	@Autowired
	private ConfigurationService configService;

	@Autowired
	private ValidationResult validationResult;
	
	@Autowired
	private ValidationError validationError;
	
	@Autowired
	private SpotTradeValidation spotTradeValidation;
	
	@Autowired
	private ForwardTradeValidation forwardTradeValidation;
	

    @Override
    public ValidationResult validate(Trade trade) {


        if (trade.getValueDate() == null) {
        	log.warn("valueDate is missing trade {}", trade);
			validationError.setField("valueDate");
			validationError.setMessage("valueDate is missing");
			validationResult.withError(validationError);
		} else {
			if (configService.getSpotTypes().stream()
					.filter(type -> StringUtils.equalsIgnoreCase(type, trade.getType())).findFirst().isPresent())
				return spotTradeValidation.validate(trade);
			if (configService.getForwardTypes().stream()
					.filter(type -> StringUtils.equalsIgnoreCase(type, trade.getType())).findFirst().isPresent())
				return forwardTradeValidation.validate(trade);
		}

        return validationResult;
    }

 



}
