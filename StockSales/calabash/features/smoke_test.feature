Feature: Check single product 

  Scenario: Checks transactions for a single product
    When I see "StockSales"
    Then I press "J4064"
    When I see "Transactions for:"
    Then I wait for 1 seconds
    * I see "9968.86"
    * I go back
