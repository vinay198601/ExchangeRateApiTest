#Author: Vinay Verma
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Background: List of steps run before each of the scenarios
@Regression
Feature: Validate API’s are fit for purpose in the use of the exchange rate for financial reasons

  Background: 
    Given Foreign Exchange Rates API is up and running

  Scenario Outline: To assert the success status code  of the latest date
    When Hit the API with end point as "<endpoint>"
    Then API should return the status code as <status>

    Examples: 
      | endpoint                     | status |
      | /latest                      |    200 |
      | /latest?symbols=USD,GBP      |    200 |
      | /latest?base=USD             |    200 |
      | /latest?base=USD&symbols=GBP |    200 |
