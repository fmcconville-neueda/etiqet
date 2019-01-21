package com.neueda.etiqet.selenium.fixture.steps;

import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.selenium.fixture.SeleniumHandlers;
import cucumber.api.java.en.Given;

public class Initialization {
    private final SeleniumHandlers handlers;

    public Initialization(SeleniumHandlers handlers) {
        this.handlers = handlers;
    }

    @Given("^a \"([^\"]+)\" browser")
    public void stepGetBrowser(String browserKey) throws EtiqetException {
        handlers.setBrowser(browserKey);
    }
}
