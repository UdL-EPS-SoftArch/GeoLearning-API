Feature: Delete Matches
  In order use the app
  As a content creator
  I want to delete a match

  Scenario: Match creator deletes match
    Given I login as "creator" with password "password"
    When I create a new match with name "match0" and description "one game match" with rating 10
    And I delete match with name "match0" and description "one game match" with rating 10
    Then The response code is 204
    And It does not exist a match with name "match0"

  Scenario: Player tries to delete a match
    Given I login as "creator" with password "password"
    When I create a new match with name "match1" and description "one game match" with rating 10
    And I login as "user" with password "password"
    When I delete match with name "match1" and description "one game match" with rating 10
    Then The response code is 401

  Scenario: Remove a match that does not exist
    Given I login as "creator" with password "password"
    When I delete match with name "match2" and description "one game match" with rating 10, match not exists
    Then The match with name "match2" does not exists




