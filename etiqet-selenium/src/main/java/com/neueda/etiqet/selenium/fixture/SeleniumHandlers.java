package com.neueda.etiqet.selenium.fixture;

import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.core.config.GlobalConfig;
import com.neueda.etiqet.core.util.StringUtils;
import com.neueda.etiqet.selenium.browsers.Browser;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

public class SeleniumHandlers {
    private Map<String, WebElement> namedWebElements = new HashMap<>();
    private Browser browser;
    private WebElementSearchMethods searchMethods = WebElementSearchMethods.getInstance();

    @After
    public void completeScenario(Scenario scenario) throws EtiqetException{
        browser.completeScenario(scenario);
    }

    public void setBrowser(String browserKey) throws EtiqetException{
        browser = (Browser) GlobalConfig.getInstance().getBrowser(browserKey);
    }

    public void nameWebElement(String occurrence, String elementType, String attributes, String alias) throws EtiqetException {
        WebElement element = search(occurrence, elementType, attributes);
        setElementAttribute(element, "etiqet-alias", alias);
        namedWebElements.put(alias, element);
    }

    public WebDriver getDriver() {
        return (WebDriver) browser.getDriver();
    }

    public WebElement getNamedWebElement(String key) throws EtiqetException {
        if (!namedWebElements.containsKey(key))
            throw new EtiqetException(String.format("Can't find WebElement with alias '%s'", key));
        return namedWebElements.get(key);
    }

    public WebElement search(String occurrence, String elementType, String attributes) throws EtiqetException {
        return searchMethods.search(this, occurrence, elementType, attributes);
    }

    public void waitForPresenceOf(String occurrence, String elementType, String attributes, String secondsToWait) throws EtiqetException{
        int seconds = Integer.parseInt(secondsToWait);
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        try {
            wait.until(webDriver -> {
                try {
                    return searchMethods.search(this, occurrence, elementType, attributes);
                } catch (EtiqetException e) {
                    return null;
                }
            });
        }
        catch (TimeoutException e){
            searchMethods.search(this, occurrence, elementType, attributes);
        }
    }

    public void appendElementAttribute(WebElement element, String attribute, String value){
        String existingValue = element.getAttribute(attribute);
        if (!StringUtils.isNullOrEmpty(existingValue)){
            value = String.format("%s %s", existingValue, value);
        }
        setElementAttribute(element, attribute, value);
    }

    public void setElementAttribute(WebElement element, String attribute, String value){
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attribute, value);
    }

}
