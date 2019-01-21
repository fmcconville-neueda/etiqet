package com.neueda.etiqet.selenium.fixture.steps;

import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.selenium.fixture.SeleniumHandlers;
import cucumber.api.java.en.Then;


public class Assertion {

    private final SeleniumHandlers handlers;
    public Assertion(SeleniumHandlers handlers) {
        this.handlers = handlers;
    }

    @Then("^check occurrence \"([^\"]+)\" of \"([^\"]+)\" element with attributes \"([^\"]+)\" exists within \"([^\"]+)\" seconds$")
    public void checkNthWebElementMatchingAttributesExistsWithinTimeFrame(String occurrence, String elementType, String attributes, String secondsToWait) throws EtiqetException{
        handlers.waitForPresenceOf(occurrence, elementType, attributes, secondsToWait);
    }

    @Then("^check the first \"([^\"]+)\" element with attributes \"([^\"]+)\" exists within \"([^\"]+)\" seconds$")
    public void checkWebElementMatchingAttributesExistsWithinTimeFrame(String elementType, String attributes, String secondsToWait) throws EtiqetException{
        checkNthWebElementMatchingAttributesExistsWithinTimeFrame("1", elementType, attributes, secondsToWait);
    }

    @Then("check the element with alias \"([^\"]+)\" has attributes \"([^\"]+)\" within \"([^\"]+)\" seconds")
    public void checkNamedWebElementHasAttributesWithinTimeFrame(String alias, String attributes, String seconds) throws EtiqetException{
        checkNthWebElementMatchingAttributesExistsWithinTimeFrame("1", "*", String.format("etiqet-alias=%s, %s", alias, attributes), seconds);
    }

    @Then("^check any element with attributes \"([^\"]+)\" exists within \"([^\"]+)\" seconds$")
    public void checkAnyWebElementHasAttributesWithinTimeFrame(String attributes, String secondsToWait) throws EtiqetException{
        checkNthWebElementMatchingAttributesExistsWithinTimeFrame("1", "*", attributes, secondsToWait);
    }
}
