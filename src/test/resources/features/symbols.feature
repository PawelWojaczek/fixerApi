Feature: Fixer API - symbols endpoint feature

  Background: User has proper authorization
    Given I am authorized user
    And Endpoint is "/symbols"

  Scenario: Authorized user is able to retrieve data for symbols
    When I send "GET" request
    Then Status code is 200
    And Response contains every symbol data