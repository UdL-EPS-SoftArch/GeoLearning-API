Feature: CreateMatchResult
  In order to use the app
  As a user
  I want to see the result of a Match

  Background:
    Given There is a player "user1" with password "password"
    And There is a match with  id 1

  Scenario: Play a Match
    Given  There is no registered matchResult for this match attached to the player
    When  The match is finished with a matchResult with result "result1" and time "time1"
    Then  There is a registered matchResult with result "result1" and time "time1" for this match attached to the player

  Scenario: Play a Match
    Given  There is a registered matchResult for this match attached to the player with result "result1" and time "time1"
    When  The match is finished with a matchResult with result "result2" and time "time2"
    Then  There is a registered matchResult with result "result2" and time "time2" for this match attached to the player

  Scenario: Play a Match
    Given  There is a registered matchResult for this match attached to the player with result "result1" and time "time1"
    When  The match is finished with a matchResult with result "result2" and time "time2"
    Then  There is a registered matchResult with result "result1" and time "time1" for this match attached to the player