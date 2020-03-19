Feature: Create ImageOptionQuestion
  In order to use the app
  As a content creator
  I want to create a game

  Scenario: Create new ImageOptionQuestion
    Given I login as "creator" with password "password"
    When I create a new ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"
    Then The response code is 201
    And It has been created a ImageOptionQuestion with image "spain.png"  solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"
