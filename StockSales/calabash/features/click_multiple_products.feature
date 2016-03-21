Feature: Checks multiple products

  Scenario: Checks transactions for multiple products
    When I see "StockSales"
    Then I press "J4064"
    When I see "Transactions for:"
    Then I wait for 1 seconds
    * I see "9968.86"
    * I go back

    When I see "StockSales"
    Then I press "C7156"
    When I see "Transactions for:"
    Then I wait for 1 seconds
    * I see "10147.41"
    * I go back

    When I see "StockSales"
    Then I press "X1893"
    When I see "Transactions for:"
    Then I wait for 1 seconds
    * I see "9615.56"
    * I go back
