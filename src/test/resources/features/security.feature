Feature: Fixer API - security features

  Scenario Outline: Unauthorized user should not get data from server
    Given I am unauthorized user
    And Endpoint is "<endpoint>"
    When I send "<method>" request
    Then Status code is 401
    And Response message equals "No API key found in request"

    Examples:
      | endpoint     | method |
      | /convert     | GET    |
      | /convert     | POST   |
      | /fluctuation | GET    |
      | /fluctuation | POST   |
      | /latest      | GET    |
      | /latest      | POST   |
      | /symbols     | GET    |
      | /symbols     | POST   |
      | /timeseries  | GET    |
      | /timeseries  | POST   |
      | /2023-12-31  | GET    |
      | /2023-12-31  | POST   |

  Scenario Outline: Authorized user has exceeded daily/monthly rate limit
    Given I am authorized user with rate limit exceeded
    And Endpoint is "<endpoint>"
    When I send "<method>" request
    Then Status code is 429
    And Response message equals "You have exceeded your daily/monthly API rate limit. Please review and upgrade your subscription plan at https://promptapi.com/subscriptions to continue."

    Examples:
      | endpoint     | method |
      | /convert     | GET    |
      | /fluctuation | GET    |
      | /latest      | GET    |
      | /symbols     | GET    |
      | /timeseries  | GET    |
      | /2023-12-31  | GET    |

  Scenario Outline: Authorized user tries to make invalid requests
    Given I am authorized user
    And Endpoint is "<endpoint>"
    When I send "<method>" request
    Then Status code is <status_code>
    And Response message equals "<message>"

    Examples:
      | endpoint     | method | status_code | message                            |
      | /convert     | POST   | 403         | You cannot consume this service    |
      | /fluctuation | POST   | 403         | You cannot consume this service    |
      | /symbols     | POST   | 403         | You cannot consume this service    |
      | /fluctuation | POST   | 403         | You cannot consume this service    |
      | /timeseries  | POST   | 403         | You cannot consume this service    |
      | /2023-12-31  | POST   | 403         | You cannot consume this service    |
      | /            | GET    | 404         | no Route matched with those values |