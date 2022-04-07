Feature: Login Feature
  Scenario Outline: Successful Login with Valid Credentials
    Given User is on Home Page
    When User Navigate to LogIn Page
    And User enters "<username>" and "<password>"
    And Message displayed Login Successfully
    Then write to the file


    Examples:
      | username   | password |
      | testuser_1 | Test@153 |
      | testuser_2 | Test@153 |
