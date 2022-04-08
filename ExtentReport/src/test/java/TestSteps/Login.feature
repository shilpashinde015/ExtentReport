Feature: Login Feature
  Scenario: Successful Login with Valid Credentials
    Given User is on Home Page
    When User Navigate to LogIn Page
    And User enters username and password
    And Message displayed Login Successfully
    Then write to the file
