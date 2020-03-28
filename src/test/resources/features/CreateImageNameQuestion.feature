Feature: ImageNameQuestion
  In order to use the app
  As a content creator
  I want to create an ImageNameWrite Question

  Scenario: Create without a login
    Given I'm not logged in
    When I create a new ImageNameQuestion with image "/image/spain" and solution "spain"
    Then The response code is 401
    And It has not been created a ImageNameQuestion

  Scenario: Create with normal user
    Given I login as "demo" with password "password"
    When I create a new ImageNameQuestion with image "/image/spain" and solution "spain"
    Then The response code is 403
    And It has not been created a ImageNameQuestion

  Scenario: Create with Content Creator and no image but with solution
    Given I login as "creator" with password "password"
    When I create a new ImageNameQuestion with image "" and solution "spain"
    Then The response code is 400
    And It has not been created a ImageNameQuestion

  Scenario: Create with Content Creator and image but no solution
    Given I login as "creator" with password "password"
    When I create a new ImageNameQuestion with image "/image/spain" and solution ""
    Then The response code is 400
    And It has not been created a ImageNameQuestion

  Scenario: Create with Content Creator and image with solution
    Given I login as "creator" with password "password"
    When I create a new ImageNameQuestion with image "/image/spain" and solution "spain"
    Then The response code is 201
    And It has been created a ImageNameQuestion with image "/image/spain" and solution "spain"