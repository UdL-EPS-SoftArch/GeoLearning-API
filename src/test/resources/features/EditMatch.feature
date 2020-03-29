Feature: Edit Matches
  In order to allow edit matches
  As a content creator
  I want to edit a match

  Scenario: Edit match as creator
     Given I login as "creator" with password "password"
     And I create a new match with name "match0" and description "one game match" with rating 10
     When I edit match with name "match0" and description "one game match"
     Then The response code is 500
     And It has been edited a match with name "match0" and description "one game match"

  Scenario: Edit match as player
      Given I login as "creator" with password "password"
      And I create a new match with name "match01" and description "one game match" with rating 10
      Then I login as "user" with password "password"
      When I edit match with name "match1" and description "one game match"
      Then The response code is 401
      And It has not been edited a match with name "match1" and description "one game match"

  Scenario: Edit match rating as creator
    Given I login as "creator" with password "password"
    And I create a new match with name "match2" and description "one game match" with rating 10
    When I edit match with name "match2" and new rating "1"
    Then The response code is 500
    And It has been edited a match with name "match2" and description "one game match"

  Scenario: Edit match name as creator
    Given I login as "creator" with password "password"
    And I create a new match with name "match3" and description "one game match" with rating 10
    When I edit match with name "match3" and new name "match4"
    Then The response code is 500
    And It has been edited a match with name "match4" and description "one game match"

  Scenario: Edit match description as creator
    Given I login as "creator" with password "password"
    And I create a new match with name "match5" and description "one game match" with rating 10
    When I edit match with name "match5" and new description "no game"
    Then The response code is 500
    And It has been edited a match with name "match5" and description "no game"

  Scenario: Edit match does not exist
    Given I login as "creator" with password "password"
    When I edit match with name "match6" and description "one game match"
    Then The response code is 500
    And It does not exist a match with name "match6"
