package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = VlanTagResourceModelData.class)
public class VlanTagResourceModelData implements PolicyData {

    @JsonProperty("extended-params")
    private List<PolicyDataExtendedParam> extendedParams;

    @JsonProperty("subnet-use")
    private String subnetUse;

    public String getSubnetUse() {
        return subnetUse;
    }

    public void setSubnetUse(String subnetUse) {
        this.subnetUse = subnetUse;
    }

    public List<PolicyDataExtendedParam> getExtendedParams() {
        return extendedParams;
    }

    public void setExtendedParams(List<PolicyDataExtendedParam> extendedParams) {
        this.extendedParams = extendedParams;
    }

    @Override
    public String toString() {
        return "SubnetAssignPolicyData [extendedParams=" + extendedParams + ", subnetUse=" + subnetUse + "]";
    }

}
