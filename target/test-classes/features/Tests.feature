Feature: I test API for Gorest

  Scenario: Get a request, check the user list and status code
    Given I get users list
    When I assert to see 200 status code
    Then I assert to see 10 users in list

  Scenario: Create new user
    Given I create new user
    When I assert to see 201 status code
    And I assert that the Location header contains the URL pointing to the newly created resource
    And I get newly created user request
    And I assert to see 200 status code
    And I assert that created user has details
    And I remove the newly created user
    Then I assert to see 204 status code

  Scenario: Update the user name
    Given I Update the user name with PATCH
    When I get newly created user request
    And I assert that the user name has been updated
    And I assert to see 200 status code
    And I remove the newly created user
    Then I assert to see 204 status code

  Scenario: Update the user status
    Given I Update the user status with PATCH
    When I get newly created user request
    And I assert that the user status has been updated
    And I assert to see 200 status code
    And I remove the newly created user
    Then I assert to see 204 status code

  Scenario: Update the user all details
    Given I Update the user all details
    When I assert to see 201 status code
    And I assert that the Location header contains the URL pointing to the newly created resource
    And I get newly created user request
    And I assert that the user status and name has been updated
    And I assert to see 200 status code
    And I remove the newly created user
    Then I assert to see 204 status code

  Scenario:  POST a new user using invalid Authorization
    Given I create the new user with an invalid token
    When I assert to see 401 status code
    Then I assert that the error message

  Scenario: POST a new user with invalid email address
    Given I create a new user with invalid email address
    When I assert to see 422 status code
    Then I assert that the error message for invalid email

  Scenario: Post new user using invalid status
    Given I create a new user with invalid status
    When I assert to see 422 status code
    Then I assert that the error message for invalid status

  Scenario: Post new user using invalid gender
    Given I create a new user with invalid gender
    When I assert to see 422 status code
    Then I assert that the error message for invalid gender

  Scenario: Get a new user details using wrong user id
    Given I create new user
    When I assert to see 201 status code
    And I assert that the Location header contains the URL pointing to the newly created resource
    And I get user with invalid id
    And I assert to see 404 status code
    Then I assert that the error message for an invalid id get request