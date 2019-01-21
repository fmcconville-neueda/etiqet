package com.neueda.etiqet.core.browser;

import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.core.config.dtos.BrowserOptions;
import cucumber.api.Scenario;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BrowserBase<WebDriver, Options> {
    protected WebDriver driver;
    protected Options options;
    protected boolean screenshotOnFail = true;

    public BrowserBase(BrowserOptions options, String driverPath) {
        setDriverPath(driverPath);
        setOptions(options);
    }

    public abstract void setOptions(BrowserOptions options);

    public abstract void setDriverPath(String path);

    public abstract void launchDriver();

    public void completeScenario(Scenario scenario) throws EtiqetException{
        if (screenshotOnFail && scenario.isFailed()){
            String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            takeScreenshot(String.format("fails/%s-%s", date, scenario.getId()));
        }
    }

    public abstract void takeScreenshot(String filename) throws EtiqetException;

    public WebDriver getDriver() {
        if (this.driver == null){
            launchDriver();
        }
        return driver;
    }

    public abstract Object executeJavascript(String javascript, long timeout, Object... args);

    public abstract Object executeJavascriptFile(String path, long timeout, Object... args) throws EtiqetException;
}