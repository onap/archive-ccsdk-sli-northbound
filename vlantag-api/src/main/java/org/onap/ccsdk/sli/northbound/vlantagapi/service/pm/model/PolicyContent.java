package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolicyContent {

    @JsonProperty("resource-models")
    private List<ResourceModel> resourceModels;

    @JsonProperty("policy-instance-name")
    private String policyInstanceName;

    public String getPolicyInstanceName() {
        return policyInstanceName;
    }

    public void setPolicyInstanceName(String policyInstanceName) {
        this.policyInstanceName = policyInstanceName;
    }

    public List<ResourceModel> getResourceModels() {
        return resourceModels;
    }

    public void setResourceModels(List<ResourceModel> resourceModels) {
        this.resourceModels = resourceModels;
    }

    @Override
    public String toString() {
        return "PolicyContent [resourceModels=" + resourceModels + ", policyInstanceName=" + policyInstanceName + "]";
    }

}
