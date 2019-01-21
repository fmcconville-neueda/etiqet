package com.neueda.etiqet.selenium;

import com.neueda.etiqet.selenium.browsers.Browser;
import cucumber.api.junit.Cucumber;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import java.io.IOException;
import java.util.ArrayList;

public class CucumberRunner extends Cucumber {
    public CucumberRunner(Class clazz) throws InitializationError, IOException {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        try{
            super.run(notifier);
        } finally {
            for (Browser browser: new ArrayList<>(Browser.getActiveBrowsers())){
                browser.close();
            }
        }
    }

}
