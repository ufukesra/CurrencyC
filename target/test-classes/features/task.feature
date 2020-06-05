Feature: Currency trade

  Scenario: As an authorized user I should be able to sell currency
    Given login with id and api_key then create an authentication token
    When  user create a GBPUSD pair quote and check all currency balances
    Then  user sell GBP and buy USD the sell side and verify the buy amount is correct to the rate
    And   user create second Quote the buy amount is wrong
    Then  end the API session