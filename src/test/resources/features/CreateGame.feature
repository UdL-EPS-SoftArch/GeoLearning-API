Feature: Game
  In order to use the app
  As a content creator
  I want to create a game

  Scenario: Create new ImageName
    Given I login as "creator" with password "password"
    When I create a new ImageName with instructions "Relacionar bandera con paises"
    Then The response code is 201
    And It has been created a ImageName with instructions "Relacionar bandera con paises"

  Scenario: Create new ImageImage without role Content Creator
    Given I login as "demo" with password "password"
    When I create a new ImageImage with instructions "Relacionar bandera con paises"
    Then The response code is 403
    And It has not been created a ImageImage with instructions "Relacionar bandera con paises"

  Scenario: Create new ImageOption without role Content Creator
    Given I login as "demo" with password "password"
    When I create a new ImageOption with instructions "Relacionar bandera con paises"
    Then The response code is 403
    And It has not been created a ImageOption with instructions "Elige la respuesta correcta"

  Scenario: Create new ImageImage
    Given I login as "creator" with password "password"
    When I create a new ImageImage with instructions "Relacionar bandera con paises"
    Then The response code is 201
    And It has been created a ImageImage with instructions "Relacionar bandera con paises"

  Scenario: Create new ImageOption
    Given I login as "creator" with password "password"
    When I create a new ImageOption with instructions "Elige la respuesta correcta"
    Then The response code is 201
    And It has been created a ImageOption with instructions "Elige la respuesta correcta"

  Scenario: Create new ImageImage without authenticating
    Given I'm not logged in
    When I create a new ImageName with instructions "Relacionar bandera con paises"
    Then The response code is 401
    And It has not been created a ImageImage with instructions "Relacionar bandera con paises"

  Scenario: Create new ImageOption without authenticating
    Given I'm not logged in
    When I create a new ImageOption with instructions "Elige la respuesta correcta"
    Then The response code is 401
    And It has not been created a ImageOption with instructions "Elige la respuesta correcta"

  Scenario: Create new ImageImage with empty instructions
    Given I login as "creator" with password "password"
    When I create a new ImageImage with instructions ""
    Then The response code is 400
    And The error message is "must not be blank"

  Scenario: Create new ImageOption with empty instructions
    Given I login as "creator" with password "password"
    When I create a new ImageOption with instructions ""
    Then The response code is 400
    And The error message is "must not be blank"