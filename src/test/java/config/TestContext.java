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

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class TestContext {

    private static final String PROPERTIES_PATH = "src/test/resources/config.properties";
    private static final ObjectMapperConfig config = createMapperConfig();
    private static final Properties properties = initProperties();

    private RequestSpecification requestSpecification;
    private Response response;
    private Class responseClass;

    private static ObjectMapperConfig createMapperConfig() {
        return new ObjectMapperConfig(ObjectMapperType.GSON)
                .gsonObjectMapperFactory(
                        (type, s) -> new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                                .create());
    }

    private static Properties initProperties() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(PROPERTIES_PATH));
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Error while opening config properties", e);
        }
    }

    public RequestSpecification createRequestSpecification(String apiKey) {
        RequestSpecification requestSpecification1 = new RequestSpecBuilder()
                .setBaseUri(getProperty("base_url"))
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

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }

    public void setResponseClass(Class responseClass) {
        this.responseClass = responseClass;
    }

    public Class getResponseClass() {
        return responseClass;
    }
}
