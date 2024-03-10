package steps;

import config.StepsBase;
import config.TestContext;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Method;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommonSteps extends StepsBase {

    private static final String API_KEY = TestContext.getProperty("api_key");
    private static final String API_KEY_RATE_LIMITED = TestContext.getProperty("api_key_rate_limited");

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

    @When("I send {string} request")
    public void iSendRequest(String method) {
        testContext.setResponse(getRequestSpecification().when().request(Method.valueOf(method)));
    }

    @Then("Status code is {int}")
    public void statusCodeIs(int statusCode) {
        getResponse().then().statusCode(statusCode);
    }

}
