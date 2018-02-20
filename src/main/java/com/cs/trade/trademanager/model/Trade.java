package com.cs.trade.trademanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Trade {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tradeDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date valueDate;

    private String customer;

    private String ccyPair;

    private String type;

    private String style;
    
    private String legalEntity;
    
    private String direction;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date excerciseStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date premiumDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

}
