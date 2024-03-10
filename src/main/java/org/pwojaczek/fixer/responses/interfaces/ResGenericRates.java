package org.pwojaczek.fixer.responses.interfaces;

import org.pwojaczek.enums.CurrencySymbol;

import java.util.Map;

public interface ResGenericRates {

    Map<CurrencySymbol, Double> getRates();
}
