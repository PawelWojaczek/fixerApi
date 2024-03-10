Feature: Fixer API - security features

  Scenario Outline: Unauthorized user should not get data from server
    Given I am unauthorized user
    And Endpoint is "/timeseries"
    And Dates: <start_date> and <end_date> are specified
    When I send "<method>" request
    Then Status code is 401
    And Response message equals "No API key found in request"

    Examples:
      | start_date | end_date   | method |
      | 2023-12-31 | 2023-12-31 | GET    |
      | 2023-12-31 | 2024-01-31 | POST   |

  Scenario Outline: Authorized user has exceeded daily/monthly rate limit
    Given I am authorized user with rate limit exceeded
    And Endpoint is "/timeseries"
    And Dates: <start_date> and <end_date> are specified
    When I send "GET" request
    Then Status code is 429
    And Response message equals "You have exceeded your daily/monthly API rate limit. Please review and upgrade your subscription plan at https://promptapi.com/subscriptions to continue."

    Examples:
      | start_date | end_date   |
      | 2023-12-31 | 2023-12-31 |
