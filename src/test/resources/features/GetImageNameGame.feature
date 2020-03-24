Feature: ImageName
  In order to use the app
  As a user
  I want to view an ImageName Game

  Scenario: Get without a login
    Given I'm not logged in
    And There is an ImageName with instructions "Relacionar bandera con paises"
    When I request the previous ImageName
    Then The response code is 401

  Scenario: Get with normal user
    Given I login as "demo" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises"
    When I request the ImageName with instructions "Relacionar bandera con paises"
    Then The response code is 200

  Scenario: Get with Content Creator
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises"
    When I request the ImageName with instructions "Relacionar bandera con paises"
    Then The response code is 200

  Scenario: Get with normal user
    Given I login as "demo" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I request the ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    Then The response code is 200

  Scenario: Get with Content Creator
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I request the ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    Then The response code is 200