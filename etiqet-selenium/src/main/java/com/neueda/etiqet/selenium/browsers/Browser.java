package com.neueda.etiqet.selenium.browsers;

import com.neueda.etiqet.core.browser.BrowserBase;
import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.core.config.dtos.BrowserOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Browser <DriverOptions> extends BrowserBase<WebDriver, DriverOptions> {
    abstract void driverInit();

    public Browser(BrowserOptions options, String driverPath) {
        super(options, driverPath);
    }

    private static List<Browser> activeBrowsers = new ArrayList<>();

    public static List<Browser> getActiveBrowsers(){
        return activeBrowsers;
    }

    public void launchDriver(){
        driverInit();
        activeBrowsers.add(this);
    }

    @Override
    public void takeScreenshot(String filename) throws EtiqetException{
        showEtiqetAliasTooltips();
        TakesScreenshot ts = (TakesScreenshot) driver;
        File output = ts.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(output, new File(String.format("screenshots/%s.png", filename)));
        } catch (IOException e){
            e.printStackTrace();
            throw new EtiqetException(e);
        }
    }

    public void close() {
        driver.quit();
        driver = null;
        activeBrowsers.remove(this);
    }

    public Object executeJavascript(String javascript, long timeout, Object... args){
        try{
            driver.manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            return jse.executeAsyncScript(javascript, args);
        } finally {
            // TODO store default scriptTimeout
            driver.manage().timeouts().setScriptTimeout(30L, TimeUnit.SECONDS);
        }
    }

    public Object executeJavascriptFile(String path, long timeout, Object... args) throws EtiqetException{
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String javascript = "";
            String line;
            while ((line = br.readLine()) != null) {
                javascript += String.format("%s\r\n", line);
            }
            return executeJavascript(javascript, timeout, args);
        } catch (IOException e ) {
            throw new EtiqetException(e);
        }
    }

    public void showEtiqetAliasTooltips() throws EtiqetException{
        executeJavascriptFile(
                "src/main/resources/javascript/load-dependencies.js", 2, Arrays.asList(
                        "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css",
                        "https://code.jquery.com/jquery-3.3.1.slim.min.js",
                        "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.bundle.min.js"
                )
        );
        executeJavascriptFile("src/main/resources/javascript/etiqet-selenium-highlight-aliases.js", 2);
    }

}
