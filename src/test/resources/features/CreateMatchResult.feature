Feature: CreateMatchResult
  In order to use the app
  As a user
  I want to see the result of a Match

  Background:
    Given I login as "demo" with password "password"
    And   There is a match with id 1

  Scenario: Play a Match
    Given  There is no registered matchResult for this match attached to my user
    When   I played the match and It has been created a MatchResult with the result 1 and time 1
    Then   It has been created a matchResult with result 1 and time 1 for this match attached to my user

  Scenario: Play a Match again with better result
    Given  I played the match and It has been created a MatchResult with the result 1 and time 1
    And    There is a registered matchResult for this match attached to my user with result 1 and time 1
    When   The match is finished with a matchResult with result 2 and time 1
    Then   The response code is 201
    And    It has been deleted the old MatchResult
    And    It has been created a matchResult with result 2 and time 1 for this match attached to my user


  Scenario: Play a Match again with lesser result
    Given  I played the match and It has been created a MatchResult with the result 1 and time 1
    And    There is a registered matchResult for this match attached to my user with result 1 and time 1
    When   The match is finished with a matchResult with result 0 and time 1
    Then   The response code is 200
    And    There is a registered matchResult for this match attached to my user with result 1 and time 1