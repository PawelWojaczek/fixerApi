package steps;

import config.StepsBase;
import config.TestContext;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.pwojaczek.enums.CurrencySymbol;
import org.pwojaczek.fixer.responses.fluctuation.ResFluctuationData;

import java.util.Arrays;
import java.util.List;

public class FluctuationSteps extends StepsBase {

    public FluctuationSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("Response contains data about fluctuation for currencies {string}")
    public void responseContainsDataAboutFluctuationForCurrencies(String currencies) {
        List<String> currencyList = Arrays.stream(currencies.split(",")).toList();
        List<CurrencySymbol> symbolList = currencyList.stream().map(CurrencySymbol::valueOf).toList();
        ResFluctuationData fluctuationData = getResponse().as(ResFluctuationData.class);
        fluctuationData.getRates().forEach((symbol, fluctuation) -> {
            Assertions.assertTrue(symbolList.contains(symbol));
            Assertions.assertNotNull(fluctuation.getChange());
            Assertions.assertNotNull(fluctuation.getEndRate());
            Assertions.assertNotNull(fluctuation.getStartRate());
            Assertions.assertNotNull(fluctuation.getChange_pct());
        });
    }
}
