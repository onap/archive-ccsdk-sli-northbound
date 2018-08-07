package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolicyEngineResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("policyConfigMessage")
    private String policyConfigMessage;

    @JsonProperty("policyConfigStatus")
    private String policyConfigStatus;

    @JsonProperty("type")
    private String type;

    @JsonProperty("config")
    private String config;

    @JsonProperty("policyName")
    private String policyName;

    @JsonProperty("policyType")
    private String policyType;

    @JsonProperty("policyVersion")
    private String policyVersion;

    @JsonProperty("matchingConditions")
    private HashMap<String, String> matchingConditions;

    @JsonProperty("responseAttributes")
    private HashMap<String, String> responseAttributes;

    @JsonProperty("property")
    private String property;

    public PolicyEngineResponse() {

    }

    public String getPolicyConfigMessage() {
        return policyConfigMessage;
    }

    public void setPolicyConfigMessage(String policyConfigMessage) {
        this.policyConfigMessage = policyConfigMessage;
    }

    public String getPolicyConfigStatus() {
        return policyConfigStatus;
    }

    public void setPolicyConfigStatus(String policyConfigStatus) {
        this.policyConfigStatus = policyConfigStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfig() {
        return config;
    }

    @JsonProperty("config")
    public void setConfig(String config) {
        this.config = config;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String type) {
        this.policyType = type;
    }

    public String getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }

    public HashMap<String, String> getMatchingConditions() {
        return matchingConditions;
    }

    @JsonProperty("matchingConditions")
    public void setMatchingConditions(HashMap<String, String> matchingConditions) {
        this.matchingConditions = matchingConditions;
    }

    public HashMap<String, String> getResponseAttributes() {
        return responseAttributes;
    }

    public void setResponseAttributes(HashMap<String, String> responseAttributes) {
        this.responseAttributes = responseAttributes;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "PolicyEngineResponse [policyConfigMessage=" + policyConfigMessage + ", policyConfigStatus="
                + policyConfigStatus + ", type=" + type + ", config=" + config + ", policyName=" + policyName
                + ", policyType=" + policyType + ", policyVersion=" + policyVersion + ", matchingConditions="
                + matchingConditions + ", responseAttributes=" + responseAttributes + ", property=" + property + "]";
    }

}
