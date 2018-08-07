package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolicyDataExtendedParam implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("param-name")
    String paramName;

    @JsonProperty("param-value")
    String paramValue;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "PolicyDataExtendedParam [paramName=" + paramName + ", paramValue=" + paramValue + "]";
    }

}