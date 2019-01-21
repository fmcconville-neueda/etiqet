Feature: Percentage Calculator website

  Scenario Outline: What is % of x returns correct value
    Given a "<browser>" browser
    When browsing "https://percentagecalculator.net/"
    Then select the first "form" element as "percentage-form"
    Then select the first "input" element with attributes "child-of=percentage-form" as "percentage-input"
    Then select occurrence "2" of "input" element with attributes "child-of=percentage-form" as "value-input"
    Then select the first "input" element with attributes "child-of=percentage-form, value=Calculate" as "submit-button"
    Then select occurrence "4" of "input" element with attributes "child-of=percentage-form" as "result-percentage"
    Then enter text "<percentage>" to "percentage-input"
    Then enter text "<value>" to "value-input"
    Then click "submit-button"
    Then check the element with alias "result-percentage" has attributes "input-val()=<output>" within "2" seconds
    Examples:
      | browser         | percentage  | value | output |
      | firefox-headless| -75         | 400   | -300   |
      | firefox-headless| 0           | 100   | 0      |
      | firefox-headless| 5           | 0     | 0      |
      | firefox-headless| -0.5        | 50    | -0.25  |
      | firefox-headless| 5           | 0.5   | 0.025  |


  Scenario Outline: x is what percent of y returns correct value
    Given a "<browser>" browser
    When browsing "https://percentagecalculator.net/"
    Then select occurrence "2" of "form" element as "form"
    Then select the first "input" element with attributes "child-of=form" as "input-1"
    Then select occurrence "2" of "input" element with attributes "child-of=form" as "input-2"
    Then select the first "input" element with attributes "child-of=form, value=Calculate" as "submit-button"
    Then select occurrence "4" of "input" element with attributes "child-of=form" as "result"
    Then enter text "<result>" to "input-1"
    Then enter text "<value>" to "input-2"
    Then click "submit-button"
    Then check the element with alias "result" has attributes "input-val()=<percentage>" within "1" seconds
    Examples:
      | browser          | result      | value | percentage  |
      | firefox-headless | -75         | 400   | -18.75      |
      | firefox-headless | 0           | 100   | fail        |
      | firefox-headless | 5           | 0     | Infinity    |
      | firefox-headless | -0.5        | 50    | -1          |
      | firefox-headless | 5           | 0.5   | 1000        |


  Scenario Outline: percentage increase/decrease returns correct value
    Given a "<browser>" browser
    When browsing "https://percentagecalculator.net/"
    Then select occurrence "3" of "form" element as "form"
    Then select the first "input" element with attributes "child-of=form" as "input-1"
    Then select occurrence "2" of "input" element with attributes "child-of=form" as "input-2"
    Then select the first "input" element with attributes "child-of=form, value=Calculate" as "submit-button"
    Then select occurrence "4" of "input" element with attributes "child-of=form" as "result"
    Then enter text "<result>" to "input-1"
    Then enter text "<value>" to "input-2"
    Then click "submit-button"
    Then check the element with alias "result" has attributes "input-val()=<percentage>" within "1" seconds
    Examples:
      | browser           | result      | value | percentage          |
      | firefox-headless  | -75         | 400   | -633.3333333333333  |
      | firefox-headless  | 0           | 100   | Infinity            |
      | firefox-headless  | 5           | 0     | fail                |
      | firefox-headless  | -0.5        | 50    | -10100              |
      | firefox-headless  | 5           | 0.5   | -90                 |
