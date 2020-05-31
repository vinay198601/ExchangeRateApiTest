#Author: Vinay Verma
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Background: List of steps run before each of the scenarios
@Acceptance
Feature: Validate API’s are fit for the purpose in the use of the exchange rate for financial
reasons

  Background: 
    Given Foreign Exchange Rates API is up and running

  Scenario: To assert the success status code  of the latest date
    When Hit the API with end point as "/latest"
    Then API should return the status code as 200

  Scenario: assert the correct response - content of the latest date data response
    When Hit the API with end point as "/latest?base=USD"
    Then base value should be "USD"

  Scenario: Hit the API with incorrect endpoint "latest?base=XXX" and validate the message
    When Hit the API with end point as "latest?base=XXX"
    Then API should return the status code as 400
    And Error message should displayed as "Base 'XXX' is not supported."

  Scenario: To assert the success status of past conversion rates response
    When Hit the API with end point as "/2010-01-12"
    Then API should return the status code as 200

  Scenario: assert the correct response - content of the past conversion rates response
    When Hit the API with end point as "/2010-01-12?base=INR"
    Then base value should be "INR"

  Scenario: Hit the API with future date and validate current date data should present
    When Hit the API with end point as "/2021-05-12"
    Then API should return the current date rates
