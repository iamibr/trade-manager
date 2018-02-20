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
public class CustomerValidaton implements TradeValidaton {
    private static Logger log = LoggerFactory.getLogger(CustomerValidaton.class);

	@Autowired
	private ValidationResult validationResult;
	
	@Autowired
	private ValidationError validationError;
	
	@Autowired
	private ConfigurationService configService;

    @Override
    public ValidationResult validate(Trade trade) {


        if (StringUtils.isBlank(trade.getCustomer())) {
			log.warn("Customer blank for trade {}", trade);
			validationError.setField("customer");
			validationError.setMessage("Customer blank");
			validationResult.withError(validationError);
		} else if (!configService.getValidCustomers().contains(trade.getCustomer())) {
			log.warn("Customer is not in approved list for trade {}", trade);
			validationError.setField("customer");
			validationError.setMessage("Invalid Customer");
			validationResult.withError(validationError);
		}

        return validationResult;
    }

  
}
