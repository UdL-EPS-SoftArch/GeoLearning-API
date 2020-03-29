Feature: ImageNameQuestion
  In order to use the app
  As a content creator
  I want to edit an ImageNameWrite Question

  Scenario: Edit without a login
    Given I'm not logged in
    And There is an ImageNameQuestion with image "/image/spain" and solution "spain"
    When I edit the previous ImageNameQuestion with image "/image/spain" and solution "spain"
    Then The response code is 401

  Scenario: Edit with normal user
    Given I login as "demo" with password "password"
    And There is an ImageNameQuestion with image "/image/spain" and solution "spain"
    When I edit the previous ImageNameQuestion with image "/image/spain" and solution "spain"
    Then The response code is 403

  Scenario: Edit with Content Creator
    Given I login as "creator" with password "password"
    And There is an ImageNameQuestion with image "/image/spain" and solution "spain"
    When I edit the previous ImageNameQuestion with image "/image/france" and solution "france"
    Then The response code is 200
    And ImageNameQuestion has been updated with image "/image/france" and solution "france"

  Scenario: Edit with Content Creator and image null
    Given I login as "creator" with password "password"
    And There is an ImageNameQuestion with image "/image/spain" and solution "spain"
    When I edit the previous ImageNameQuestion with image "" and solution "france"
    Then The response code is 400

  Scenario: Edit with Content Creator and solution null
    Given I login as "creator" with password "password"
    And There is an ImageNameQuestion with image "/image/spain" and solution "spain"
    When I edit the previous ImageNameQuestion with image "/image/france" and solution ""
    Then The response code is 400