package com.neueda.etiqet.selenium.browsers;

import com.neueda.etiqet.core.config.dtos.BrowserOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeBrowser extends Browser<ChromeOptions> {
    public ChromeBrowser(BrowserOptions options, String driver){
        super(options, driver);
    }

    @Override
    public void setOptions(BrowserOptions options) {
        this.options = new ChromeOptions();
        if (options.getHeadless()){
            this.options.addArguments("--headless");
        }
        this.options.addArguments("--log-level=3", "--silent");
    }

    @Override
    public void setDriverPath(String path) {
        System.setProperty("webdriver.chrome.driver", path);
    }

    @Override
    public void driverInit() {
        driver = new ChromeDriver(options);
    }
}
