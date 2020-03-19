Feature: Create ImageImageQuestion
  In order to use the app
  As a content creator
  I want to create a game

  Scenario: Create new ImageImageQuestion
    Given I login as "creator" with password "password"
    When I create a new ImageImageQuestion with image "spain.png" and solution "spain"
    Then The response code is 201
    And It has been created a ImageImageQuestion with image "spain.png" and solution "spain"

  Scenario: Create new ImageImageQuestion without role Content Creator
    Given I login as "demo" with password "password"
    When I create a new ImageImageQuestion with image "spain.png" and solution "spain"
    Then The response code is 403
    And It has not been created a ImageImageQuestion with image "spain.png" and solution "spain"

  Scenario: Create new ImageImageQuestion without authenticating
    Given I'm not logged in
    When I create a new ImageImageQuestion with image "spain.png" and solution "spain"
    Then The response code is 401
    And It has not been created a ImageImageQuestion with image "spain.png" and solution "spain"

  Scenario: Create new ImageImageQuestion with empty image
    Given I login as "creator" with password "password"
    When I create a new ImageImageQuestion with image "" and solution "spain"
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create new ImageImageQuestion with empty solution
    Given I login as "creator" with password "password"
    When I create a new ImageImageQuestion with image "spain.png" and solution ""
    Then The response code is 400
    And The error message is "must not be blank"
