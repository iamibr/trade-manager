package com.cs.trade.trademanager.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trade.trademanager.model.Trade;
import com.cs.trade.trademanager.model.JsonResponse;
import com.cs.trade.trademanager.validatons.TradeValidaton;


@Service
public class ValidationService {

	@Autowired
	private JsonResponse jsonResult;

	private List<TradeValidaton> tradeValidatons;
	
	@Autowired
	public ValidationService(List<TradeValidaton> tradeValidatons) {
		this.tradeValidatons = tradeValidatons;
	}

	public JsonResponse validate(Trade trade) {

		tradeValidatons.parallelStream().map(validator -> validator.validate(trade)) 
				.filter(validationResult -> validationResult != null) 
				.filter(validationResult -> validationResult.hasErrors()) 
				.forEach(validationError -> validationError.errors()
						.forEach(error -> jsonResult.addInvalidField(error.getField(), error.getMessage()))); 
																															

		return jsonResult;
	}

	public List<JsonResponse> bulkValidate(Collection<Trade> trades) {

		return trades.parallelStream().map(this::validate).collect(Collectors.toList());
	}

}
