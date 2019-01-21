package com.neueda.etiqet.selenium.browsers;

import com.neueda.etiqet.core.config.dtos.BrowserOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxBrowser extends Browser<FirefoxOptions> {
    public FirefoxBrowser(BrowserOptions options, String driver){
        super(options, driver);
    }

    @Override
    public void setOptions(BrowserOptions options) {
        this.options = new FirefoxOptions();
        this.options.setHeadless(options.getHeadless());
        if (options.getWindowSize() != null){
            this.options.setCapability("browserSize", options.getWindowSize());
        }
        this.screenshotOnFail = options.getScreenshotOnFail();
    }

    @Override
    public void setDriverPath(String path) {
        System.setProperty("webdriver.gecko.driver", path);
    }

    @Override
    public void driverInit() {
        driver = new FirefoxDriver(options);
    }
}
