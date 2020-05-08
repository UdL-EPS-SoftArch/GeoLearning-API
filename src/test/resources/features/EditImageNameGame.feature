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
    And ImageName has been updated with instructions "Write bandera con paises"

  Scenario: Edit with Content Creator and no instructions
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I edit the previous ImageName with instructions ""
    Then The response code is 400

  Scenario: Edit with Content Creator and instructions with more than 255 characters
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I edit the previous ImageName with instructions "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec."
    Then The response code is 400

  Scenario: Edit with Content Creator and add QuestionList
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises"
    When I edit the previous ImageName with question "image/japan" and "japan" and question with "image/spain" and "spain"
    Then The response code is 204
    And ImageName has been updated with question with "image/japan" and "japan" and question with "image/spain" and "spain"

  Scenario: Edit with Content Creator and add Question to list
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I edit the previous ImageName with question with "image/sweden" and "sweden"
    Then The response code is 204
    And ImageName has been updated with question with "image/sweden" and "sweden"

  Scenario: Edit with Content Creator and delete Question from list
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I edit the previous ImageName deleting question with "image/japan" and "japan"
    Then The response code is 204
    And ImageName has been updated without question with "image/japan" and "japan"

  Scenario: Edit with Content Creator and delete QuestionList
    Given I login as "creator" with password "password"
    And There is an ImageName with instructions "Relacionar bandera con paises" and question with "image/japan" and "japan" and question with "image/spain" and "spain"
    When I edit the previous ImageName deleting QuestionsList
    Then The response code is 204
    And ImageName has been updated without questionList