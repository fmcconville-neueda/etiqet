package com.neueda.etiqet.core.config.dtos;

import com.neueda.etiqet.core.common.EtiqetConstants;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="browser", namespace=EtiqetConstants.NAMESPACE)
public class BrowserImpl implements Serializable {
    private String name;
    private String driver;
    private String impl;
    private BrowserOptions options;

    @XmlAttribute(required=true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(required=true)
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @XmlAttribute(name="impl", required=true)
    public String getImpl() {
        return impl;
    }

    public void setImpl(String impl) {
        this.impl = impl;
    }

    @XmlElement(name="options", namespace=EtiqetConstants.NAMESPACE)
    public BrowserOptions getOptions(){
        if (options == null)
            return new BrowserOptions();
        return options;
    }

    public void setOptions(BrowserOptions options){
        this.options = options;
    }

}
