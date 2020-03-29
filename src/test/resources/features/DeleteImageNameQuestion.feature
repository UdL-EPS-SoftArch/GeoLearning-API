Feature: ImageNameQuestion
  In order to use the app
  As a content creator
  I want to delete an ImageNameQuestion

  Scenario: Delete without a login
    Given I'm not logged in
    And There is an ImageNameQuestion with image "/image/japan" and solution "japan"
    When I delete the previous ImageNameQuestion
    Then The response code is 401

  Scenario: Delete with normal user
    Given I login as "demo" with password "password"
    And There is an ImageNameQuestion with image "/image/japan" and solution "japan"
    When I delete the previous ImageNameQuestion
    Then The response code is 403

  Scenario: Delete with Content Creator
    Given I login as "creator" with password "password"
    And There is an ImageNameQuestion with image "/image/japan" and solution "japan"
    When I delete the previous ImageNameQuestion
    Then The response code is 204
    And Previous ImageNameQuestion has been deleted