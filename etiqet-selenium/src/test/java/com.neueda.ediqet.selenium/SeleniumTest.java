package com.neueda.ediqet.selenium;

import com.neueda.etiqet.selenium.CucumberRunner;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberRunner.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber"},
        features = "src/test/resources/scenarios/selenium_example.feature",
        glue = { "com.neueda.etiqet.selenium.fixture" })
public class SeleniumTest { }
