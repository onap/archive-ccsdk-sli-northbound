package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Elements {

    @JsonProperty("recycle-vlantag-range")
    String recycleVlantagRange;

    @JsonProperty("overwrite")
    String overwrite;

    @JsonProperty("vlantag-name")
    String vlantagName;

    @JsonProperty("allowed-range")
    private List<AllowedRanges> allowedRanges;

    @JsonProperty("element-vlan-role")
    String elementVlanRole;

    public String getRecycleVlantagRange() {
        return recycleVlantagRange;
    }

    public void setRecycleVlantagRange(String recycleVlantagRange) {
        this.recycleVlantagRange = recycleVlantagRange;
    }

    public String getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(String overwrite) {
        this.overwrite = overwrite;
    }

    public String getVlantagName() {
        return vlantagName;
    }

    public void setVlantagName(String vlantagName) {
        this.vlantagName = vlantagName;
    }

    public List<AllowedRanges> getAllowedRanges() {
        return allowedRanges;
    }

    public void setAllowedRanges(List<AllowedRanges> allowedRanges) {
        this.allowedRanges = allowedRanges;
    }

    public String getElementVlanRole() {
        return elementVlanRole;
    }

    public void setElementVlanRole(String elementVlanRole) {
        this.elementVlanRole = elementVlanRole;
    }

    @Override
    public String toString() {
        return "Elements [recycleVlantagRange=" + recycleVlantagRange + ", overwrite=" + overwrite + ", vlantagName="
                + vlantagName + ", allowedRanges=" + allowedRanges + ", elementVlanRole=" + elementVlanRole + "]";
    }

}
