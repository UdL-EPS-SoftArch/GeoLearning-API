Feature: Add Questions to ImageOption
  In order to use the app
  As a content creator
  I want to add questions to a ImageOption game

  Scenario: Add ImageOptionQuestions to ImageOption game
    Given I login as "creator" with password "password"
    And  There is a created ImageOption with instructions "Relacionar bandera con paises"
    And There is a ImageOptionQuestion with image "spain.png", solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium"
    When Content Creator add ImageOptionQuestion with image "spain.png", solution "spain" and  optionA "italy", optionB "spain", optionC "germany", optionD "holland", optionE "Belgium" to a ImageOption with instructions "Relacionar bandera con paises"
