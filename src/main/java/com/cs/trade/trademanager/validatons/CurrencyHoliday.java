package com.cs.trade.trademanager.validatons;

import java.util.Currency;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface CurrencyHoliday {

    Optional<Set<Date>> fetchHolidays(Currency currency);

}
