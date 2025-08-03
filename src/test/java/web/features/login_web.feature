@web
Feature: Login Web on SauceDemo

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
