package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

public class RequestObject {

    private String policyName;

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @Override
    public String toString() {
        return "RequestObject [policyName=" + policyName + "]";
    }

}