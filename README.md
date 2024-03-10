# Fixer API tests BDD

## Stack:
- Java
- RestAssured
- Cucumber
- JUnit

## Configuration

1. Acquire api token from [Fixer API Documentation](https://apilayer.com/marketplace/fixer-api)
2. Put api token inside [config.properties](src/test/resources/config.properties) file

- Note: in order for security features to work properly, you also need to specify api token with used requests into **API_KEY_RATE_LIMITED**

## Running tests
- Directly from .feature file - run any **.feature** file from this [directory](src/test/resources/features)
- Via JUnit Runner - run [TestRunner](src/test/java/TestRunner.java) class
- Maven:
```mvn clean compile test```

## Known bugs
1. Due to bug in Cucumber plugin, steps with annotation ParameterType may be seen as not implemented - only visual, tests with those steps work fine.
2. In [fluctuation.feature](src/test/resources/features/fluctuation.feature), when no dates are specified - server returns data for current day. In the documentation, the **start_date** and **end_date** parameters are mandatory. That's a bug! 
