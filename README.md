# Fixer API tests BDD

## Stack:
- Java
- RestAssured
- Cucumber
- JUnit

## Configuration

1. Acquire api token from [Fixer API Documentation](https://apilayer.com/marketplace/fixer-api)
2. Put api token inside **config.properties** file _(src/test/resources)_

- Note: in order for security features to work properly, you also need to specify api token with used requests into **API_KEY_RATE_LIMITED**

## Running tests
- Directly from .feature file - run file
- Via JUnit Runner - run "TestRunner" class _(src/test/java)_
- Maven:


    mvn clean compile test
