package org.pwojaczek.fixer.responses.fluctuation;

import com.google.gson.annotations.SerializedName;

public class CurrencyFluctuationData {
    private Double change;
    private Double change_pct;
    @SerializedName("end_rate")
    private Double endRate;
    @SerializedName("start_rate")
    private Double startRate;

    public Double getChange() {
        return change;
    }

    public Double getChange_pct() {
        return change_pct;
    }

    public Double getEndRate() {
        return endRate;
    }

    public Double getStartRate() {
        return startRate;
    }
}
