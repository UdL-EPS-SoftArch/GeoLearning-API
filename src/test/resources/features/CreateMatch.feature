Feature: Create Matches
  In order use the app
  As a content creator
  I want to create a match

  Scenario: Create new match
    Given I login as "creator" with password "password"
    And There is no registered match with name "match0"
    When I register a new match with name "match0" and description "one game match" with rating 10
    Then The response code is 201
    And It has been created a match with and name "match0" and description "one game match" with rating 10

  Scenario: Create new match with existing name
    Given There is a created match with name "match1" and description "existing match" with rating 5
    And I login as "creator" with password "password"
    When I register a new match with name "match1" and description "new existing match" with rating 10
    Then The response code is 409
    And I cannot create a match with name "match1" and description "new existing match" with rating 10

  Scenario: Create new match with blank name
    Given I login as "creator" with password "password"
    When I register a new match with name "" and description "blank name" with rating 6
    Then The response code is 400
    And The error message is "must not be blank"
    And I cannot create a match with blank name

  Scenario: Create new match without authentication
    Given I'm not logged in
    And There is no registered match with name "match2"
    When I register a new match with name "match2" and description "no authentication" with rating 10
    Then The response code is 401
    And I cannot create a match with name "match2" and description "no authentication" with rating 10

  Scenario: Create new match with no rating
    Given I login as "creator" with password "password"
    When I register a new match with name "match3" and description "no game" with no rating
    Then The response code is 400
    And The error message is "must not be null"
    And I cannot create a match with name "match3" and description "no game" with no rating