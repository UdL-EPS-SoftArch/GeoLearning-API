Feature: Game
  In order to use the app
  As a content creator
  I want to create a game

  Scenario: Create new ImageName
    Given I login as "demo" with password "password"
    When I create a new ImageName with instructions "Relacionar bandera con paises"
    Then The response code is 201
    And It has been created a ImageName with instructions "Relacionar bandera con paises"
