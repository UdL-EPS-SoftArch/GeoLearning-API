Feature: Create ImageOptionQuestion
  In order to use the app
  As a content creator
  I want to create a game

  Scenario: Create new ImageOptionQuestion
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "", optionE "Belgium"
    Then The response code is 201
    And It has been created a ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"

  Scenario: Create new ImageOptionQuestion without role Content Creator
    Given I login as "demo" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"
    Then The response code is 403
    And It has not been created a ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"

  Scenario: Create new ImageOptionQuestion without authenticating
    Given I'm not logged in
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"
    Then The response code is 401
    And It has not been created a ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"

  Scenario: Create new ImageOptionQuestion without empty image
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image ""  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create new ImageOptionQuestion without empty image
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create new ImageOptionQuestion without empty image
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create new ImageOptionQuestion without empty image
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "", optionC "germany", optionD "holland", optionE "Belgium"
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create new ImageOptionQuestion without empty image
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "", optionD "holland", optionE "Belgium"
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create new ImageOptionQuestion without empty image
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "", optionE "Belgium"
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create new ImageOptionQuestion without empty image
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE ""
    Then The response code is 400
    And The error message is "must not be blank"
