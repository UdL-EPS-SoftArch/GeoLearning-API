Feature: ImageName
  In order to use the app
  As a content creator
  I want to edit an ImageName Game

  Scenario: Edit without a login
    Given I'm not logged in
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I edit the previous ImageName with instructions "Write bandera con paises"
    Then The response code is 401

  Scenario: Edit with normal user
    Given I login as "demo" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I edit the previous ImageName with instructions "Write bandera con paises"
    Then The response code is 403

  Scenario: Edit with Content Creator
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I edit the previous ImageName with instructions "Write bandera con paises"
    Then The response code is 200
    And ImageName has been updated with intructions "Write bandera con paises"
    