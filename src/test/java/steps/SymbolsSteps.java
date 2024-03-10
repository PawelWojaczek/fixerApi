package steps;

import config.StepsBase;
import config.TestContext;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.EnumUtils;
import org.junit.jupiter.api.Assertions;
import org.pwojaczek.enums.CurrencySymbol;
import org.pwojaczek.fixer.responses.symbols.ResSymbolsData;

public class SymbolsSteps extends StepsBase {

    public SymbolsSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("Response contains every symbol data")
    public void responseContainsEverySymbolData() {
        ResSymbolsData resSymbolsData = getResponseObject();
        resSymbolsData.getSymbols().forEach((key, value) -> {
            Assertions.assertTrue(EnumUtils.isValidEnum(CurrencySymbol.class, key), "No currency found in enums: " + key);
        });
    }
}
