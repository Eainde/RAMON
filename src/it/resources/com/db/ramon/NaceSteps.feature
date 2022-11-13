Feature: Nace service Testing

  Background:
    Given Spring is running
    And Setup database

  @OrderService
  Scenario: Get Order record
    Given path "/order/"
    And Add following orders in database
      | orderId   | _level | code | parent | description         | item_includes | item_also_includes | rulings | item_excludes | ref_to_isic_rev_4 |
      | 1         | 1      | A    | 1      | This is description | This includes | This also includes | ho      | Item excludes | 11                |
      | 2         | 1      | A    | 1      | This is description | This includes | This also includes | ho      | Item excludes | 11                |
      | 3         | 1      | A    | 1      | This is description | This includes | This also includes | ho      | Item excludes | 11                |
      | 4         | 1      | A    | 1      | This is description | This includes | This also includes | ho      | Item excludes | 11                |
    And a request is made using method "GET"
    Then the request should respond with the status code 200
    And the response body should match "allOrderResponseDto"

  @OrderService
  Scenario: Get Order record by id
    Given path "/order/1"
    And Add following orders in database
      | orderId | _level | code | parent | description         | item_includes | item_also_includes | rulings | item_excludes | ref_to_isic_rev_4 |
      | 1       | 1      | A    | 1      | This is description | This includes | This also includes | ho      | Item excludes | 11                |
      | 2       | 1      | A    | 1      | This is description | This includes | This also includes | ho      | Item excludes | 11                |
      | 3       | 1      | A    | 1      | This is description | This includes | This also includes | ho      | Item excludes | 11                |
      | 4       | 1      | A    | 1      | This is description | This includes | This also includes | ho      | Item excludes | 11                |
    And a request is made using method "GET"
    Then the request should respond with the status code 200
    And the response body should match "orderResponseDto"

  @OrderService
  Scenario: Add new order
    Given path "/order/"
    And passing csv file as query param
    Then the request should respond with the status code 200
