package com.cs.trade.trademanager.validatons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trade.trademanager.config.ConfigurationService;
import com.cs.trade.trademanager.model.Trade;
import com.cs.trade.trademanager.model.ValidationError;
import com.cs.trade.trademanager.model.ValidationResult;

@Service
public class LegalEntityValidaton implements TradeValidaton {

    private static Logger log = LoggerFactory.getLogger(LegalEntityValidaton.class);


	@Autowired
	private ConfigurationService configService;
    
	@Autowired
	private ValidationResult validationResult;
	
	@Autowired
	private ValidationError validationError;

    @Override
    public ValidationResult validate(Trade trade) {

        if (configService.getLegalEntities().contains(trade.getLegalEntity()))
			return validationResult;
		log.warn("Legal entity is invalid for trade {}", trade);
		validationError.setField("legalEntity");
		validationError.setMessage("Legal entity is invalid");
		validationResult.withError(validationError);
		return validationResult;
    }

  

}
