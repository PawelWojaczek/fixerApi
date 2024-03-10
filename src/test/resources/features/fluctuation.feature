Feature: Fixer API - timeseries endpoint feature

  Background: User has proper authorization
    Given I am authorized user
    And Endpoint is "/fluctuation"
    And Response class is "fluctuation.ResFluctuationData"

  Scenario Outline: Authorized user is able to retrieve data for specified currency and base currency
    Given Dates: <start_date> and <end_date> are specified
    And Currencies "<symbols>" are specified
    And Base currency "<base>" is specified
    When I send "GET" request
    Then Status code is 200
    And Response contains data for dates between <start_date> and <end_date>
    And Response has base currency converted to "<base>"
    And Response contains data about fluctuation for currencies "<symbols>"

    Examples:
      | start_date | end_date   | symbols | base |
      | 2023-01-01 | 2023-01-31 | EUR,USD | USD  |
      | 2023-01-01 | 2023-01-01 | USD,JPY | EUR  |
      | 2023-01-01 | 2023-12-31 | GBP,USD | PLN  |
      | 2022-12-31 | 2023-01-01 | JPY,AMD | EUR  |
      | 2023-01-02 | 2023-01-01 | JPY,AMD | EUR  |

      # Fluctuation also works backwards

  Scenario Outline: Authorized user tries to make requests with invalid data
    Given Dates: <start_date> and <end_date> are specified
    And Currencies "<symbols>" are specified
    And Base currency "<base>" is specified
    When I send "GET" request
    Then Status code is <status_code>
    And Response is failed, error code is <error_code>, type is "<type>" and info is "<info>"

    Examples:
      | start_date | end_date   | symbols | base | status_code | error_code | type                   | info                                                                                                |
      | 2023-12-31 | 2023-12-31 |         | AAA  | 200         | 201        | invalid_base_currency  |                                                                                                     |
      | 2023-12-31 | 2023-12-31 | AAA     | EUR  | 200         | 202        | invalid_currency_codes | You have provided one or more invalid Currency Codes. [Required format: currencies=EUR,USD,GBP,...] |

  Scenario Outline: Authorized user tries to make requests with invalid dates
    Given Invalid dates: "<start>" and "<end>" are specified
    And Currencies "<symbols>" are specified
    And Base currency "<base>" is specified
    When I send "GET" request
    Then Status code is <status_code>
    And Response is failed, error code is <error_code>, type is "<type>" and info is "<info>"

    Examples:
      | start      | end        | symbols | base | status_code | error_code | type                  | info                                                                                                                                           |
      |            | 2023-12-31 |         |      | 200         | 501        | no_timeframe_supplied | You have not specified a Time-Frame. [Required format: ...&start_date=YYYY-MM-DD&end_date=YYYY-MM-DD]                                          |
      |            |            |         |      | 200         | 501        | no_timeframe_supplied | You have not specified a Time-Frame. [Required format: ...&start_date=YYYY-MM-DD&end_date=YYYY-MM-DD]                                          |
      | 2023-12-31 |            |         |      | 200         | 501        | no_timeframe_supplied | You have not specified a Time-Frame. [Required format: ...&start_date=YYYY-MM-DD&end_date=YYYY-MM-DD]                                          |
      | 20230102   | 202301-01  |         |      | 200         | 502        | invalid_start_date    | You have specified an invalid start date. Please try again or refer to the API documentation available at https://serpstack.com/documentation. |
      | 2023-01-02 | 202301-01  |         |      | 200         | 503        | invalid_end_date      | You have specified an invalid end date. Please try again or refer to the API documentation available at https://serpstack.com/documentation.   |

#    Server does not return 400 status codes with invalid data
# If no dates are specified (example #2, in docs marked as required) - server takes latest day - that's probably a bug
