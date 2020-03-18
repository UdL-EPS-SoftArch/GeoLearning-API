Feature: CreateMatchResult
  In order to use the app
  As a user
  I want to see the result of a Match

  Background:
    Given There is a player "user1" with password "password"
    And There is a match with  id 1

  Scenario: Play a Match
    Given  There is no registered matchResult for match 1 attached to "user1"
    And   I login as "user1" with password "password"
    When  The match "matchName" is finished with a matchResult with result "result1" and time "time1"
    Then  There is a registered matchResult with result "result1" and time "time1" for "matchName" attached to "user1"

  Scenario: Play a Match
    Given  There is a registered matchResult for match "matchName" attached to "user1" with result "result1" and time "time1"
    And   I login as "user1" with password "password"
    When  The match "matchName" is finished with a matchResult with result "result2" and time "time2"
    Then  There is a registered matchResult with result "result2" and time "time2" for "matchName" attached to "user1"

  Scenario: Play a Match
    Given  There is a registered matchResult for match "matchName" attached to "user1" with result "result2" and time "time2"
    And   I login as "user1" with password "password"
    When  The match "matchName" is finished with a matchResult with result "result3" and time "time3"
    Then  There is a registered matchResult with result "result2" and time "time2" for "matchName" attached to "user1"