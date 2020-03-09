Feature: Create Matches
  In order use the app
  As a content creator
  I want to create a match

  Scenario: Create new match
    Given I login as "user" with password "password"
    And There is no registered match with name "match"
    When I register a new match with name "match" and description "one game match" with List<Game> "game"
    Then The response code is 201
    And It has been created a match with name "match" and description "one game match" with games

  Scenario: Create new match with existing name
    Given I login as "user" with password "password"
    And There is a registered match with name "match"
    When I register a new match with name "match" and description "existing match" with List<Game> "game"
    Then The response code is 409
    And I cannot create a match with name "match" and description "one game match" with List<Game> "game"

  Scenario: Create new match with blank name
    Given I login as "user" with password "password"
    When I register a new match with name "" and description "blank name" with List<Game> "game"
    Then The response code is 400
    And I cannot create a match with blank name

  Scenario: Create new team without authentication
    Given I'm not logged in
    And There is no registered match with name "match"
    When I register a new match with name "match", and description "no authentication" with List<Game> "game"
    Then The response code is 401
    And I cannot create a match with name "match"

  Scenario: Create new match with no game(s)
    Given I login as "user" with password "password"
    When I register a new match with name "match" and description "no game" with no List<Game> "game"
    Then The response code is 400
    And The error message is "must not be blank"
    And I cannot create a team with name "match"