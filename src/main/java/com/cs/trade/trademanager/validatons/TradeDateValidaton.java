package com.cs.trade.trademanager.validatons;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trade.trademanager.model.Trade;
import com.cs.trade.trademanager.model.ValidationError;
import com.cs.trade.trademanager.model.ValidationResult;


@Service
public class TradeDateValidaton implements TradeValidaton{

    @Autowired
    private ValidationResult validationResult;
	@Autowired
	private ValidationError validationError;
	
    private static Logger log = LoggerFactory.getLogger(TradeDateValidaton.class);


    @Override
    public ValidationResult validate(Trade trade) {

        if (trade.getValueDate() == null) {
            log.warn("Value date is missing for trade {}", trade);
            validationError.setField("valueDate");
    		validationError.setMessage("valueDate is missing");
    		validationResult.withError(validationError);
        }

        if (trade.getTradeDate() == null) {
            log.warn("Trade date is missing for trade {}", trade);
            validationError.setField("tradeDate");
    		validationError.setMessage("tradeDate is missing");
    		validationResult.withError(validationError);
           
        }

        if (validationResult.hasErrors() || !trade.getValueDate().before(trade.getTradeDate()))
			return validationResult;
		log.warn("Value date cannot be before trade date for trade {}", trade);
		validationError.setField("valueDate");
		validationError.setMessage("Value date cannot be before Trade date");
		validationResult.withError(validationError);
		return validationResult;
    }
}
