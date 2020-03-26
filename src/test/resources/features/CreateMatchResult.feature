Feature: CreateMatchResult
  In order to use the app
  As a user
  I want to see the result of a Match

  Background:
    Given There is a player "user1" with password "password" and "email@example.com"
    And There is a match with id 1

  Scenario: Play a Match
    Given  There is no registered matchResult for this match attached to the player
    When   The match is finished with a matchResult with result 1 and time 1
    Then   There is a registered matchResult with result 1 and time 1 for this match attached to the player

  Scenario: Play a Match again with better result
    Given  We played the match previously
    And    There is a registered matchResult for this match attached to the player with result 1 and time 1
    When   The match is finished with a matchResult with result 2 and time 1
    Then   There is a registered matchResult with result 2 and time 1 for this match attached to the player

  Scenario: Play a Match again with lesser result
    Given  We played the match previously
    And    There is a registered matchResult for this match attached to the player with result 1 and time 1
    When   The match is finished with a matchResult with result 0 and time 1
    Then   There is a registered matchResult with result 1 and time 1 for this match attached to the player