package com.cs.trade.trademanager.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cs.trade.trademanager.model.Trade;
import com.cs.trade.trademanager.model.JsonResponse;
import com.cs.trade.trademanager.service.ValidationService;


@RequestMapping("/cs")
@RestController
public class ValidationController {
	@Autowired
	private ValidationService validationService;

	@RequestMapping(path = "/validateData", method = RequestMethod.POST)
	public JsonResponse validate(@RequestBody Trade trade) {
		return validationService.validate(trade);
	}

	@RequestMapping(path = "/validateBulkData", method = RequestMethod.POST)
	public Collection<JsonResponse> validateBulk(@RequestBody List<Trade> trades) {
		return validationService.bulkValidate(trades);
	}
}
