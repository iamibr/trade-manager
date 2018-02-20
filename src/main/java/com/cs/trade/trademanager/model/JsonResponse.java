package com.cs.trade.trademanager.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Component
public class JsonResponse {

    private Trade trade;
    private Map<String, Collection<String>> invalidFields = new ConcurrentHashMap<>();
    public JsonResponse addInvalidField(String field, String message) {

        if (!invalidFields.containsKey(field))
			invalidFields.put(field, ConcurrentHashMap.newKeySet());
        invalidFields.get(field).add(message);
        return this;
    }

    @JsonGetter("haveErrors")
    public boolean haveErrors() {
        return !invalidFields.isEmpty();
    }

}
