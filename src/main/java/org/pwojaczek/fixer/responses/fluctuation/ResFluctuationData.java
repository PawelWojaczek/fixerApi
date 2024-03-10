package org.pwojaczek.fixer.responses.fluctuation;

import com.google.gson.annotations.SerializedName;
import org.pwojaczek.enums.CurrencySymbol;
import org.pwojaczek.fixer.responses.interfaces.ResGenericBase;
import org.pwojaczek.fixer.responses.interfaces.ResGenericTimeFrame;

import java.time.LocalDate;
import java.util.Map;

public class ResFluctuationData implements ResGenericBase, ResGenericTimeFrame {
    private boolean success;
    private boolean fluctuation;
    @SerializedName("start_date")
    private LocalDate startDate;
    @SerializedName("end_date")
    private LocalDate endDate;
    private String base;
    private Map<CurrencySymbol, CurrencyFluctuationData> rates;

    public boolean isSuccess() {
        return success;
    }

    public boolean isFluctuation() {
        return fluctuation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String getBase() {
        return base;
    }

    public Map<CurrencySymbol, CurrencyFluctuationData> getRates() {
        return rates;
    }
}
