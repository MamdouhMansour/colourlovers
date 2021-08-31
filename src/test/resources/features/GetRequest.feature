Feature: GET Request

  Scenario: Check numViews to be greater than 4000
    Given I am authorized user on colourlovers
    When I send request to "http://www.colourlovers.com/api/patterns"
    Then Response status code is 200
    And I get XML response with numViews are greater than 4000
    
