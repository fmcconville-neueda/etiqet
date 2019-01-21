package com.neueda.etiqet.selenium.fixture.steps;

import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.selenium.fixture.SeleniumHandlers;
import cucumber.api.java.en.Then;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Input {

    private final SeleniumHandlers handlers;
    public Input(SeleniumHandlers handlers) {
        this.handlers = handlers;
    }

    @Then("^enter text \"([^\"]*)\" to \"([^\"]*)\"$")
    public void stepSendKeysToWebElement(String text, String elementKey) throws EtiqetException {
        WebElement element = handlers.getNamedWebElement(elementKey);
        element.sendKeys(text);
    }

    @Then("^click \"([^\"]+)\"$")
    public void stepClickWebElement(String elementKey) throws EtiqetException{
        handlers.getNamedWebElement(elementKey).click();
    }

    @Then("^submit the element \"([^\"]+)\"$")
    public void stepSubmitForm(String elementKey) throws EtiqetException{
        handlers.getNamedWebElement(elementKey).submit();
    }

    @Then("^enter special key \"([^\"]+)\" to \"([^\"]+)\"$")
    public void stepEnterKeyToElement(String key, String elementKey) throws EtiqetException{
        handlers.getNamedWebElement(elementKey).sendKeys(Keys.valueOf(key.toUpperCase()));
    }

}
