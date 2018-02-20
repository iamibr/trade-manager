package com.cs.trade.trademanager.validatons;

import java.util.Currency;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trade.trademanager.model.Trade;
import com.cs.trade.trademanager.model.ValidationError;
import com.cs.trade.trademanager.model.ValidationResult;

@Service
public class ValidateCurrency implements TradeValidaton {

    private static Logger log = LoggerFactory.getLogger(ValidateCurrency.class);

	@Autowired
	private ValidationResult validationResult;
	
	@Autowired
	private ValidationError validationError;
	
	@Autowired
    private CurrencyHoliday currencyHolidayService;

    @Override
    public ValidationResult validate(Trade trade) {


        String ccyPair = trade.getCcyPair();
        if (StringUtils.isBlank(ccyPair)) {
            log.warn("ccyPair is blank for trade {}", trade);
            validationError.setField("ccyPair");
            validationError.setMessage("ccyPair is blank");
            return validationResult.withError(validationError);
        }

        if (StringUtils.length(ccyPair) != 6) { 
            log.warn("ccyPair length should be 6 for trade {}", trade);
            validationError.setField("ccyPair");
            validationError.setMessage("ccyPair length should be 6");
            return validationResult.withError(validationError);
        }

        boolean valueDateIsPresent = true;

        if (trade.getValueDate() == null) {
            valueDateIsPresent = false;
            log.warn("valueDate is missing for trade {}", trade);
            validationError.setField("valueDate");
            validationError.setMessage("valueDate is missing");
            validationResult.withError(validationError);
        }

        String currency1Str = ccyPair.substring(0, 3), currency2Str = ccyPair.substring(3);
        try {
            if (valueDateIsPresent && isDateHolidayCurrency(trade.getValueDate(), Currency.getInstance(currency1Str))) {
				log.warn("valueDate matches to holiday for Currency 1 for trade {}", trade);
				validationError.setField("ccyPair");
				validationError.setMessage("valueDate matches to holiday for Currency 1");
				validationResult.withError(validationError);
			}
        }catch (IllegalArgumentException e) {
            log.warn("Currency 1 is not valid for trade {}", trade);
            validationError.setField("ccyPair");
            validationError.setMessage("Currency 1 is not valid");
            validationResult.withError(validationError);
        }
        try {
            if (valueDateIsPresent && isDateHolidayCurrency(trade.getValueDate(), Currency.getInstance(currency2Str))) {
				log.warn("valueDate matches to holiday for Currency 2 for trade {}", trade);
				validationError.setField("ccyPair");
				validationError.setMessage("valueDate matches to holiday for Currency 2");
				validationResult.withError(validationError);
			}
        }catch (IllegalArgumentException e) {
            log.warn("Currency 2 is not valid for trade {}", trade);
            validationError.setField("ccyPair");
            validationError.setMessage("Currency 2 is not valid");
            validationResult.withError(validationError);
        }

        return validationResult;
    }

    boolean isDateHolidayCurrency(Date date, Currency currency) {

        if (currencyHolidayService == null)
			log.warn("Currency holiday service not set");
		else {
			Optional<Set<Date>> dates = currencyHolidayService.fetchHolidays(currency);
			if (dates.isPresent())
				return dates.get().contains(date);
			log.warn("Empty holidays dates response for query {}", currency);
		}

        return false;
    }

}
