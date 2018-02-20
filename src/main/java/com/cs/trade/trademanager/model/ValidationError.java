package com.cs.trade.trademanager.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Component
public class ValidationError {

    private String field;
    private String message;
    
}
