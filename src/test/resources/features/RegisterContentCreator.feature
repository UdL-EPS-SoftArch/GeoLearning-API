Feature: Register
  In order to use the app
  As a content creator
  I want to register myself and get an account

  Scenario: Register new contentcreator
    Given There is no registered contentcreator with username "contentcreator"
    And I'm not logged in
    When I register a new contentcreator with username "contentcreator", email "contentcreator@geolearning.game" and password "password"
    Then The response code is 201
    And It has been created a contentcreator with username "contentcreator" and email "contentcreator@geolearning.game", the password is not returned
    And I can login with username "contentcreator" and password "password"

  Scenario: Register existing username
    Given There is a registered contentcreator with username "contentcreator" and password "password" and email "cc@geolearning.game"
    And I'm not logged in
    When I register a new contentcreator with username "contentcreator", email "contentcreator@geolearning.game" and password "newpassword"
    Then The response code is 409
    And I cannot login with username "contentcreator" and password "newpassword"

  Scenario: Register contentcreator when already authenticated
    Given I login as "demoCC" with password "password"
    When I register a new contentcreator with username "contentcreator", email "contentcreator@geolearning.game" and password "password"
    Then The response code is 403
    And It has not been created a contentcreator with username "contentcreator"

  Scenario: Register contentcreator with empty password
    Given I'm not logged in
    When I register a new contentcreator with username "contentcreator", email "contentcreator@geolearning.game" and password ""
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a contentcreator with username "contentcreator"

  Scenario: Register contentcreator with empty email
    Given I'm not logged in
    When I register a new contentcreator with username "contentcreator", email "" and password "password"
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a contentcreator with username "contentcreator"

  Scenario: Register contentcreator with invalid email
    Given I'm not logged in
    When I register a new contentcreator with username "contentcreator", email "playerageolearning.game" and password "password"
    Then The response code is 400
    And The error message is "must be a well-formed email address"
    And It has not been created a contentcreator with username "contentcreator"

  Scenario: Register contentcreator with password shorter than 8 characters
    Given I'm not logged in
    When I register a new contentcreator with username "contentcreator", email "contentcreator@geolearning.game" and password "pass"
    Then The response code is 400
    And The error message is "length must be between 8 and 256"
    And It has not been created a contentcreator with username "contentcreator"

  Scenario: Register contentcreator with an existing email
    Given There is a registered contentcreator with username "contentcreator" and password "password" and email "cc@geolearning.game"
    And I'm not logged in
    When I register a new contentcreator with username "contentcreator2", email "cc@geolearning.game" and password "password2"
    Then The response code is 409
    And I can login with username "contentcreator" and password "password"
