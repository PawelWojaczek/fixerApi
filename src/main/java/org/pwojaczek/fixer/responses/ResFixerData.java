package org.pwojaczek.fixer.responses;

import com.google.gson.annotations.SerializedName;
import org.pwojaczek.enums.CurrencySymbol;

import java.time.LocalDate;
import java.util.Map;

public class ResFixerData {
    private boolean success;
    private boolean timeseries;
    @SerializedName("start_date")
    private LocalDate startDate;
    @SerializedName("end_date")
    private LocalDate endDate;
    private CurrencySymbol base;
    private Map<LocalDate, Map<CurrencySymbol, Double>> rates;

    public boolean isSuccess() {
        return success;
    }

    public boolean isTimeseries() {
        return timeseries;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CurrencySymbol getBase() {
        return base;
    }

    public Map<LocalDate, Map<CurrencySymbol, Double>> getRates() {
        return rates;
    }
}
