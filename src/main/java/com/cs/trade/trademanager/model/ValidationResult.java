package com.cs.trade.trademanager.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Component
public class ValidationResult {

    private List<ValidationError> errors = new ArrayList<>();

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<ValidationError> errors() {
        return errors;
    }

    public ValidationResult withError(ValidationError error) {
        errors.add(error);
        return this;
    }


    @Override
    public String toString() {
        return "ValidationResult{" +
                "errors=" + errors +
                '}';
    }

    public List<ValidationError> getErrors() {
        return errors;
    }


}
