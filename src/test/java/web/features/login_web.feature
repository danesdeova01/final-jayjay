@web
Feature: Login and Checkout Flow

  Scenario: Successful login
    Given I am on the login page
    When I enter valid username and password
    Then I should see the products page

  Scenario: Failed login with invalid credentials
    Given I am on the login page
    When I enter invalid username and password
    Then I should see an error message

  Scenario: Logout user
    Given I am logged in as a valid user
    When I click logout button
    Then I should be redirected to the login page

  Scenario: Add product to cart
    Given I am logged in as a valid user
    When I add a product to cart
    Then the cart should contain the product

  Scenario: Successful checkout
    Given I am logged in as a valid user
    When I add a product to cart
    And I proceed to checkout with valid information
    Then I should see the order confirmation
