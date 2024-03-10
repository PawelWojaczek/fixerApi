package steps;

import config.StepsBase;
import config.TestContext;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.pwojaczek.fixer.responses.latest.ResLatestData;

import java.time.LocalDate;

public class LatestSteps extends StepsBase {

    public LatestSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("Response contains data for current day")
    public void responseContainsDataForCurrentDay() {
        ResLatestData resLatestData = getResponseObject();
        Assertions.assertEquals(resLatestData.getDate(), LocalDate.now());
    }
}
