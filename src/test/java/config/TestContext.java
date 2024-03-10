package config;

import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.pwojaczek.adapters.LocalDateTypeAdapter;

import java.time.LocalDate;

public class TestContext {

    private static final String URL = "https://api.apilayer.com/fixer";

    private static final ObjectMapperConfig config = createMapperConfig();

    private static ObjectMapperConfig createMapperConfig() {
        System.out.println("new mapperConfig");
        return new ObjectMapperConfig(ObjectMapperType.GSON)
                .gsonObjectMapperFactory(
                        (type, s) -> new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                                .create());
    }


    private RequestSpecification requestSpecification;
    private Response response;

    public RequestSpecification createRequestSpecification(String apiKey) {
        RequestSpecification requestSpecification1 = new RequestSpecBuilder()
                .setBaseUri(URL)
                .log(LogDetail.ALL)
                .addHeader("apikey", apiKey)
                .setConfig(RestAssuredConfig.config().objectMapperConfig(config))
                .build();
        this.requestSpecification = RestAssured.given().spec(requestSpecification1);
        return requestSpecification;
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
