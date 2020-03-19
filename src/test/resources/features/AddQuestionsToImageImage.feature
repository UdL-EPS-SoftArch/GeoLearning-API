Feature: Add Questions to ImageImage
  In order to use the app
  As a content creator
  I want to add questions to a ImageImage game

  Scenario: Add ImageImageQuestions to ImageImage game
    Given I login as "creator" with password "password"
    And  There is a created ImageImage with instructions "Relacionar bandera con paises"
    And There is a ImageImageQuestion with image "spain.png" and solution "spain"
    When Content Creator add ImageImageQuestion with image "spain.png" and solution "spain" to a ImageImage with instructions "Relacionar bandera con paises"
    Then The response code is 204
    And There is a ImageImageQuestion with image "spain.png" and solution "spain" in a ImageImage with instructions "Relacionar bandera con paises"

