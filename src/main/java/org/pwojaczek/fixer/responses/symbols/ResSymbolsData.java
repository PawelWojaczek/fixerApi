package org.pwojaczek.fixer.responses.symbols;

import java.util.Map;

public class ResSymbolsData {
    private boolean success;
    //due to this being response used to check symbols, we map symbols to String, not to CurrencySymbol
    private Map<String, String> symbols;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, String> symbols) {
        this.symbols = symbols;
    }
}
