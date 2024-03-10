package config;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepsBase {

    protected TestContext testContext;

    public StepsBase(TestContext testContext) {
        this.testContext = testContext;
    }

    public Response getResponse() {
        return testContext.getResponse();
    }

    public RequestSpecification getRequestSpecification() {
        return testContext.getRequestSpecification();
    }

    protected <T> T getResponseObject() {
        return (T) getResponse().as(testContext.getResponseClass());
    }
}
