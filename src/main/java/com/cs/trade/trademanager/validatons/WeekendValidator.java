package com.cs.trade.trademanager.validatons;


import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.HashSet;
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
public class WeekendValidator implements TradeValidaton{

    @Autowired
    private ValidationResult validationResult;
	@Autowired
	private ValidationError validationError;
	
    private static Logger LOG = LoggerFactory.getLogger(WeekendValidator.class);

    private Set<DayOfWeek> weekendDays = new HashSet<>();

    public WeekendValidator() {
        weekendDays.add(DayOfWeek.SATURDAY);
        weekendDays.add(DayOfWeek.SUNDAY);
    }

    @Override
    public ValidationResult validate(Trade trade) {

        if (trade.getValueDate() == null) {
			LOG.warn("valueDate is missing for trade {}", trade);
			validationError.setField("valueDate");
			validationError.setMessage("valueDate is missing");
		} else {
			boolean isWeekendDay = weekendDays.contains(
					trade.getValueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
			if (!isWeekendDay)
				return validationResult;
			LOG.warn("valueDate is holiday date for trade {}", trade);
			validationError.setField("valueDate");
			validationError.setMessage("valueDate is holiday date");
		}
		validationResult.withError(validationError);
		return validationResult;
    }

    public String getWeekendDaysStr() {
        return StringUtils.join(",", weekendDays);
    }

    public void setWeekendDaysStr(String weekendDaysStr) {
        weekendDays.clear();
        for(String rawDay : weekendDaysStr.split(","))
			weekendDays.add(DayOfWeek.valueOf(rawDay));
    }

}
