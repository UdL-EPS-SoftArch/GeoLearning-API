Feature: ImageName
  In order to use the app
  As a content creator
  I want to create an ImageName Game

  Scenario: Create with a login
    Given I'm not logged in
    When I create a new ImageName with instructions "Relacionar bandera con paises"
    Then The response code is 401
    And It has not been created an ImageName

  Scenario: Create with normal user
    Given I login as "demo" with password "password"
    When I create a new ImageName with instructions "Relacionar bandera con paises"
    Then The response code is 401
    And It has not been created an ImageName

  Scenario: Create with Content Creator and no instructions
    Given I login as "creator" with password "password"
    When I create a new ImageName without instructions
    Then The response code is 403
    And It has not been created an ImageName

  Scenario: Create with Content Creator and instructions
    Given I login as "creator" with password "password"
    When I create a new ImageName with instructions "Relacionar bandera con paises"
    Then The response code is 200
    And It has been created a ImageName with instructions "Relacionar bandera con paises"

  Scenario: Create with Content Creator, instructions and questions list
    Given I login as "creator" with password "password"
    When I create a new ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    Then The response code is 200
    And It has been created a ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"