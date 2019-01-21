package com.neueda.etiqet.selenium.fixture.steps;

import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.selenium.fixture.SeleniumHandlers;
import cucumber.api.java.en.When;

public class Navigation {
    private final SeleniumHandlers handlers;

    public Navigation(SeleniumHandlers handlers) {
        this.handlers = handlers;
    }

    @When("^browsing \"([^\"]+)\"$")
    public void stepBrowseToUrl(String url) throws EtiqetException {
        handlers.getDriver().get(url);
    }
}
