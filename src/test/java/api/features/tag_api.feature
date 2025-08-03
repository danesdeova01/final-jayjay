@api
Feature: Tag API

  Scenario: Get list of tags
    When I send GET request to tag API
    Then the tag response status should be 200
    And the response should contain a list of tags
