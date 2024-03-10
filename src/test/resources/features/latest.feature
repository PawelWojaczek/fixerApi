Feature: Fixer API - latest endpoint feature

  Background: User has proper authorization
    Given I am authorized user
    And Endpoint is "/latest"
    And Response class is "latest.ResLatestData"

  Scenario Outline: Authorized user is able to retrieve data for current day
    Given Currencies "<symbols>" are specified
    And Base currency "<base>" is specified
    When I send "GET" request
    Then Status code is 200
    And Response contains data for current day
    And Response has base currency converted to "<base>"
    And Response contains rates for currencies "<symbols>"

    Examples:
      | symbols | base |
      | EUR,USD | USD  |
      | USD,JPY | EUR  |
      | GBP,USD | PLN  |
      | JPY,AMD | EUR  |

  Scenario Outline: Authorized user tries to make invalid requests
    And Endpoint is "<endpoint>"
    When I send "<method>" request
    Then Status code is <status_code>
    And Response message equals "<message>"

    Examples:
      | endpoint | method | status_code | message                            |
      | /latest  | POST   | 403         | You cannot consume this service    |
      | /        | GET    | 404         | no Route matched with those values |

  Scenario Outline: Authorized user tries to make requests with invalid data
    Given Currencies "<symbols>" are specified
    And Base currency "<base>" is specified
    When I send "GET" request
    Then Status code is <status_code>
    And Response is failed, error code is <error_code>, type is "<type>" and info is "<info>"

    Examples:
      | symbols | base | status_code | error_code | type                   | info                                                                                                |
      |         | AAA  | 200         | 201        | invalid_base_currency  |                                                                                                     |
      | AAA     | EUR  | 200         | 202        | invalid_currency_codes | You have provided one or more invalid Currency Codes. [Required format: currencies=EUR,USD,GBP,...] |

#    Server returns proper response based on currencies specified, if at least one currency is in list of currencies - missing ones are not parsed
