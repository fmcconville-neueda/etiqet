Feature: Selenium Etiqet test

    Scenario: Enter invalid email on github registration
        Given a "firefox-1" browser
        When browsing "https://github.com/join?source=header-repo"
        Then select the first "input" element with attributes "id=user_email" as "email-input"
        Then enter text "invalid email" to "email-input"
        Then enter special key "TAB" to "email-input"
        And check any element with attributes "text-icontains=Email is invalid" exists within "2" seconds

    Scenario Outline: Search for Neueda on google with multiple browsers
        Given a "<browser>" browser
        When browsing "https://www.google.com"
        Then select the first "input" element with attributes "id=lst-ib" as "search-input"
        Then enter text "Neueda" to "search-input"
        Then enter special key "ENTER" to "search-input"
        And check any element with attributes "text-icontains=www.neueda.com" exists within "2" seconds
        Examples:
            | browser           |
            | firefox-headless  |
            | chrome-headless   |
