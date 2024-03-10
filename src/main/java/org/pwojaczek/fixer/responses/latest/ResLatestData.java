package org.pwojaczek.fixer.responses.latest;

import org.pwojaczek.enums.CurrencySymbol;
import org.pwojaczek.fixer.responses.interfaces.ResGenericBase;
import org.pwojaczek.fixer.responses.interfaces.ResGenericRates;

import java.time.LocalDate;
import java.util.Map;

public class ResLatestData implements ResGenericBase, ResGenericRates {
    private String base;
    private LocalDate date;
    private boolean success;
    private Long timestamp;
    private Map<CurrencySymbol, Double> rates;


    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<CurrencySymbol, Double> getRates() {
        return rates;
    }

    public void setRates(Map<CurrencySymbol, Double> rates) {
        this.rates = rates;
    }
}
