@api
Feature: User API

  Scenario: Get user by ID
    Given I have a valid user ID
    When I send GET request to user API
    Then the user response status should be 200
    And the response should contain user details

  Scenario: Create new user
    Given I have user data to create
    When I send POST request to create user
    Then the user response status should be 200
    And the response should contain the created user ID

  @update
  Scenario: Update user
    Given I have an existing user ID and new data
    When I send PUT request to update user
    Then the user response status should be 200
    And the response should contain updated user data

  @delete
  Scenario: Delete user
    Given I have an existing user ID to delete
    When I send DELETE request to delete user
    Then the user response status should be 200
    And the response should confirm deletion
