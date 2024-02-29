@tag
Feature: Product purchasing in azamzon

  @AmazonAddToCart
  Scenario Outline: To validate the prodcut is added to the cart
    Given Launch the url "https://www.amazon.in/"
    When click on menu button
    When Click on "Men's Fashion" in the menu
    And Filter by star as "4"
    And Filter by price as "1,000 - ₹5,000"
    And Select the brand "<Brand1>" and "<Brand2>"
    And Count number of result
    And Select the "2" product
    And Check the item is added to the cart

    Examples: 
      | Brand1 | Brand2      |
      | Puma   | Allen Solly |
      | Puma   | Allen Solly |
      | Puma   | Allen Solly |
