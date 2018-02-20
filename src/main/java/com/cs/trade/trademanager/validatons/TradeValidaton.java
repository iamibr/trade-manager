package com.cs.trade.trademanager.validatons;

import com.cs.trade.trademanager.model.Trade;
import com.cs.trade.trademanager.model.ValidationResult;


public interface TradeValidaton {

    ValidationResult validate(Trade trade);
}
