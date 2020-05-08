Feature: ImageNameQuestion
  In order to use the app
  As a user
  I want to view an ImageNameWrite Question

  Scenario: Get without a login
    Given I'm not logged in
    And There is an ImageNameQuestion with image "/image/spain" and solution "spain"
    When I request the previous ImageNameQuestion
    Then The response code is 401

  Scenario: Get with normal user
    Given I login as "demo" with password "password"
    And There is an ImageNameQuestion with image "/image/spain" and solution "spain"
    When I request the ImageNameQuestion with image "/image/spain" and solution "spain"
    Then The response code is 200

  Scenario: Get with Content Creator
    Given I login as "creator" with password "password"
    And There is an ImageNameQuestion with image "/image/spain" and solution "spain"
    When I request the ImageNameQuestion with image "/image/spain" and solution "spain"
    Then The response code is 200