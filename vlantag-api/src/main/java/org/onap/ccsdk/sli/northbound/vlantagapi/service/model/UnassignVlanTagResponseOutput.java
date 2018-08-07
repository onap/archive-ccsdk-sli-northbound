package org.onap.ccsdk.sli.northbound.vlantagapi.service.model;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UnassignVlanTagResponseOutput {
    private @Valid String vlanType = null;
    private @Valid String key = null;
    private @Valid String vlantagName = null;

    /**
     **/
    public UnassignVlanTagResponseOutput vlanType(String vlanType) {
        this.vlanType = vlanType;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("vlan-type")
    public String getVlanType() {
        return vlanType;
    }

    public void setVlanType(String vlanType) {
        this.vlanType = vlanType;
    }

    /**
     **/
    public UnassignVlanTagResponseOutput key(String key) {
        this.key = key;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     **/
    public UnassignVlanTagResponseOutput vlantagName(String vlantagName) {
        this.vlantagName = vlantagName;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("vlantag-name")
    public String getVlantagName() {
        return vlantagName;
    }

    public void setVlantagName(String vlantagName) {
        this.vlantagName = vlantagName;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnassignVlanTagResponseOutput unassignVlanTagResponseOutput = (UnassignVlanTagResponseOutput) o;
        return Objects.equals(vlanType, unassignVlanTagResponseOutput.vlanType)
                && Objects.equals(key, unassignVlanTagResponseOutput.key)
                && Objects.equals(vlantagName, unassignVlanTagResponseOutput.vlantagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vlanType, key, vlantagName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UnassignVlanTagResponseOutput {\n");

        sb.append("    vlanType: ").append(toIndentedString(vlanType)).append("\n");
        sb.append("    key: ").append(toIndentedString(key)).append("\n");
        sb.append("    vlantagName: ").append(toIndentedString(vlantagName)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
