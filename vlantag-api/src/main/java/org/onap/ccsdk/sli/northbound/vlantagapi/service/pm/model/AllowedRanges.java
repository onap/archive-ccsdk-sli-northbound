package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllowedRanges {

    @JsonProperty("min")
    String min;

    @JsonProperty("max")
    String max;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "AllowedRanges [min=" + min + ", max=" + max + "]";
    }

}
