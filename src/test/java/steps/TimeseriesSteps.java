package steps;

import config.StepsBase;
import config.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.StringUtils;
import org.pwojaczek.enums.CurrencySymbol;
import org.pwojaczek.fixer.responses.ResFixerError;
import org.pwojaczek.fixer.responses.objects.FixerErrorDetails;
import org.pwojaczek.fixer.responses.timeseries.ResTimeseriesData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TimeseriesSteps extends StepsBase {

    public TimeseriesSteps(TestContext testContext) {
        super(testContext);
    }

    @Given("Dates: {date} and {date} are specified")
    public void datesAreSpecified(LocalDate startDate, LocalDate endDate) {
        getRequestSpecification()
                .param("start_date", startDate.toString())
                .param("end_date", endDate.toString());
    }

    @Given("Invalid dates: {string} and {string} are specified")
    public void invalidDatesAreSpecified(String startDate, String endDate) {
        getRequestSpecification()
                .param("start_date", startDate)
                .param("end_date", endDate);
    }


    @Given("Currencies {string} are specified")
    public void currencyIsSpecified(String symbols) {
        getRequestSpecification()
                .param("symbols", symbols.trim());
    }

    @Given("Base currency {string} is specified")
    public void baseCurrencyIsSpecified(String base) {
        getRequestSpecification()
                .param("base", base);
    }

    @Given("Date strings are specified {string} and {string}")
    public void dateStringsAreSpecified(String startDate, String endDate) {
        getRequestSpecification()
                .param("start_date", startDate)
                .param("end_date", endDate);
    }

    @Then("Response contains data for dates between {date} and {date}")
    public void responseContainsDataForSpecifiedDates(LocalDate startDate, LocalDate endDate) {
        ResTimeseriesData fixerData = getResponse().as(ResTimeseriesData.class);
        Assertions.assertEquals(fixerData.getStartDate(), startDate);
        Assertions.assertEquals(fixerData.getEndDate(), endDate);
    }


    @Then("Response has only data for currencies {string}")
    public void responseHasOnlyDataForCurrencySpecified(String currencies) {
        List<String> currencyList = Arrays.stream(currencies.split(",")).toList();
        List<CurrencySymbol> symbolList = currencyList.stream().map(CurrencySymbol::valueOf).toList();
        ResTimeseriesData fixerData = getResponse().as(ResTimeseriesData.class);
        fixerData.getRates().forEach((date, rates) -> {
            Assertions.assertEquals(rates.size(), symbolList.size());
            Assertions.assertEquals(symbolList, rates.keySet().stream().toList());
        });
    }

    @Then("Response has base currency converted to {string}")
    public void responseHasBaseCurrencyConvertedToBase(String baseCurrency) {
        Assertions.assertEquals(getResponse().as(ResTimeseriesData.class).getBase().toString(), baseCurrency);
    }

    @Then("Response is failed, error code is {int}, type is {string} and info is {string}")
    public void responseCheckInfoTypeCode(int errorCode, String type, String info) {
        ResFixerError responseMessage = getResponse().then().extract().as(ResFixerError.class);
        Assertions.assertFalse(responseMessage.isSuccess());
        FixerErrorDetails errorDetails = responseMessage.getError();
        Assertions.assertEquals(errorCode, errorDetails.getCode());
        Assertions.assertEquals(type, errorDetails.getType());
        if (StringUtils.isBlank(info)) {
            Assertions.assertTrue(StringUtils.isBlank(errorDetails.getInfo()));
        } else {
            Assertions.assertEquals(info, errorDetails.getInfo());
        }

    }

    @Then("Response message equals {string}")
    public void responseCheckInfoTypeCode(String message) {
        Assertions.assertEquals(message, getResponse().then().extract().jsonPath().getString("message"));
    }
}
