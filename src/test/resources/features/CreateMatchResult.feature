Feature: CreateMatchResult
  In order to use the app
  As a user
  I want to see the result of a Match

  Background:
    Given There is a player
    And There is a match


  Scenario: Assign a MatchResult to a match
    Given  There is no registered matchResult for this Match
    And   I login as "demoP" with password "password"
    When  Its registeed a new MatchResult with result "result"
    Then  The response code is 201
    And   There is a registered MatchResult with "result" for this match