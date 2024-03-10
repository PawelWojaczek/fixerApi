package org.pwojaczek.fixer.responses.timeseries;

import com.google.gson.annotations.SerializedName;
import org.pwojaczek.enums.CurrencySymbol;
import org.pwojaczek.fixer.responses.interfaces.ResGenericBase;

import java.time.LocalDate;
import java.util.Map;

public class ResTimeseriesData implements ResGenericBase {
    private boolean success;
    private boolean timeseries;
    @SerializedName("start_date")
    private LocalDate startDate;
    @SerializedName("end_date")
    private LocalDate endDate;
    private String base;
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

    public String getBase() {
        return base;
    }

    public Map<LocalDate, Map<CurrencySymbol, Double>> getRates() {
        return rates;
    }
}
