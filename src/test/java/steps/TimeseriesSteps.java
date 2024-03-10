package steps;

import config.StepsBase;
import config.TestContext;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.pwojaczek.enums.CurrencySymbol;
import org.pwojaczek.fixer.responses.timeseries.ResTimeseriesData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TimeseriesSteps extends StepsBase {

    public TimeseriesSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("Response contains data for dates between {date} and {date}")
    public void responseContainsDataForSpecifiedDates(LocalDate startDate, LocalDate endDate) {
        ResTimeseriesData fixerData = getResponse().as(ResTimeseriesData.class);
        Assertions.assertEquals(fixerData.getStartDate(), startDate);
        Assertions.assertEquals(fixerData.getEndDate(), endDate);
    }

    @Then("Response contains data for specified date frame and currencies {string}")
    public void responseHasOnlyDataForCurrencySpecified(String currencies) {
        List<String> currencyList = Arrays.stream(currencies.split(",")).toList();
        List<CurrencySymbol> symbolList = currencyList.stream().map(CurrencySymbol::valueOf).toList();
        ResTimeseriesData fixerData = getResponse().as(ResTimeseriesData.class);
        fixerData.getRates().forEach((date, rates) -> {
            Assertions.assertEquals(rates.size(), symbolList.size());
            Assertions.assertEquals(symbolList, rates.keySet().stream().toList());
        });
    }
}
