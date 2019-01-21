package com.neueda.etiqet.selenium.fixture.steps;

import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.selenium.fixture.SeleniumHandlers;
import cucumber.api.java.en.Then;

public class Selection {
    private final SeleniumHandlers handlers;
    public Selection(SeleniumHandlers handlers) {
        this.handlers = handlers;
    }

    @Then("^select occurrence \"([^\"]+)\" of \"([^\"]+)\" element with attributes \"([^\"]+)\" as \"([^\"]+)\"$")
    public void stepSelectNthElementMatchingTypeWithAttributes(String occurrence, String elementType, String attributes, String alias) throws EtiqetException{
        handlers.nameWebElement(occurrence, elementType, attributes, alias);
    }

    @Then("^select occurrence \"([^\"]+)\" of \"([^\"]+)\" element as \"([^\"]+)\"$")
    public void stepSelectNthElementMatchingType(String occurrence, String elementType, String alias) throws EtiqetException{
       stepSelectNthElementMatchingTypeWithAttributes(occurrence, elementType, "", alias);
    }

    @Then("^select the first \"([^\"]+)\" element with attributes \"([^\"]+)\" as \"([^\"]+)\"$")
    public void stepSelectFirstElementMatchingTypeWithAttributes(String elementType, String attributes, String alias) throws EtiqetException{
        stepSelectNthElementMatchingTypeWithAttributes("1", elementType, attributes, alias);
    }

    @Then("^select the first \"([^\"]+)\" element as \"([^\"]+)\"$")
    public void stepSelectFirstElementMatchingType(String elementType, String alias) throws EtiqetException{
        stepSelectNthElementMatchingTypeWithAttributes("1", elementType, "", alias);
    }
}
