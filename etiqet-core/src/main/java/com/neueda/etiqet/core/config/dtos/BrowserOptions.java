package com.neueda.etiqet.core.config.dtos;

import com.neueda.etiqet.core.common.EtiqetConstants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(namespace = EtiqetConstants.NAMESPACE, name="options" )
public class BrowserOptions implements Serializable {
    private Boolean headless;
    private Boolean screenshotOnFail;
    private String windowSize;

    @XmlElement(name="headless", namespace = EtiqetConstants.NAMESPACE)
    public Boolean getHeadless(){
        if (this.headless == null)
            return false;
        return headless;
    }

    public void setHeadless(Boolean headless){
        this.headless = headless;
    }

    @XmlElement(name="windowSize", namespace = EtiqetConstants.NAMESPACE)
    public String getWindowSize(){
        return windowSize;
    }

    public void setWindowSize(String windowSize) {
        this.windowSize = windowSize;
    }

    @XmlElement(name="screenshotOnFail", namespace = EtiqetConstants.NAMESPACE)
    public Boolean getScreenshotOnFail(){
        if (this.screenshotOnFail == null)
            return false;
        return screenshotOnFail;
    }

    public void setScreenshotOnFail(Boolean screenshotOnFail){
        this.screenshotOnFail = screenshotOnFail;
    }
}
