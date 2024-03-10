package steps;

import config.StepsBase;
import config.TestContext;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.StringUtils;
import org.pwojaczek.enums.CurrencySymbol;
import org.pwojaczek.fixer.responses.ResFixerError;
import org.pwojaczek.fixer.responses.interfaces.ResGenericBase;
import org.pwojaczek.fixer.responses.interfaces.ResGenericRates;
import org.pwojaczek.fixer.responses.objects.FixerErrorDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommonSteps extends StepsBase {

    private static final String API_KEY = TestContext.getProperty("api_key");
    private static final String API_KEY_RATE_LIMITED = TestContext.getProperty("api_key_rate_limited");
    private static final String RESPONSES_PACKAGE_NAME = "org.pwojaczek.fixer.responses.";

    public CommonSteps(TestContext testContext) {
        super(testContext);
    }

    @ParameterType(value = "\\d{4}\\-\\d{2}\\-\\d{2}", name = "date")
    public LocalDate dateParameter(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Given("I am unauthorized user")
    public void imUnauthorizedUser() {
        testContext.createRequestSpecification("");
    }

    @Given("I am authorized user")
    public void iAmAuthorizedUser() {
        testContext.createRequestSpecification(API_KEY);
    }

    @Given("I am authorized user with rate limit exceeded")
    public void iAmAuthorizedUserWithRateLimitExceeded() {
        testContext.createRequestSpecification(API_KEY_RATE_LIMITED);
    }

    @Given("Endpoint is {string}")
    public void endpointIs(String endpoint) {
        getRequestSpecification().basePath(endpoint);
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

    //TODO: check if there is better way to use same response chunks other than defining in featue files
    @Given("Response class is {string}")
    public void responseClassIs(String classPath) {
        try {
            testContext.setResponseClass(Class.forName(RESPONSES_PACKAGE_NAME + classPath));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found for name: " + classPath, e);
        }
    }

    @When("I send {string} request")
    public void iSendRequest(String method) {
        testContext.setResponse(getRequestSpecification().when().request(Method.valueOf(method)));
    }

    @Then("Status code is {int}")
    public void statusCodeIs(int statusCode) {
        getResponse().then().statusCode(statusCode);
    }

    @Then("Response message equals {string}")
    public void responseCheckInfoTypeCode(String message) {
        Assertions.assertEquals(message, getResponse().then().extract().jsonPath().getString("message"));
    }

    @Then("Response is failed, error code is {int}, type is {string} and info is {string}")
    public void responseCheckInfoTypeCode(int errorCode, String type, String info) {
        ResFixerError responseMessage = getResponse().as(ResFixerError.class);
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

    @Then("Response has base currency converted to {string}")
    public void responseHasBaseCurrencyConvertedToBase(String baseCurrency) {
        ResGenericBase resGenericBase = getResponseObject();
        Assertions.assertEquals(resGenericBase.getBase(), baseCurrency);
    }

    @Then("Response contains rates for currencies {string}")
    public void responseContainsRatesForCurrencies(String currencies) {
        List<String> currencyList = Arrays.stream(currencies.split(",")).toList();
        List<CurrencySymbol> symbolList = currencyList.stream().map(CurrencySymbol::valueOf).toList();
        ResGenericRates resGenericRates = getResponseObject();
        Map<CurrencySymbol, Double> rates = resGenericRates.getRates();
        Assertions.assertEquals(rates.size(), symbolList.size());
        Assertions.assertEquals(symbolList, rates.keySet().stream().toList());
    }

}
