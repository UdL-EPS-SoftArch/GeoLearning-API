Feature: ImageName
  In order to use the app
  As a content creator
  I want to delete an ImageName Game

  Scenario: Delete without a login
    Given I'm not logged in
    And There is an ImageName game with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I delete the previous ImageName
    Then The response code is 401
    And Previous ImageName is still in the database

  Scenario: Delete with normal user
    Given I login as "demo" with password "password"
    And There is an ImageName game with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I delete the previous ImageName
    Then The response code is 403
    And Previous ImageName is still in the database

  Scenario: Delete with Content Creator
    Given I login as "creator" with password "password"
    And There is an ImageName game with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I delete the previous ImageName
    Then The response code is 204
    And Previous ImageName has been deleted