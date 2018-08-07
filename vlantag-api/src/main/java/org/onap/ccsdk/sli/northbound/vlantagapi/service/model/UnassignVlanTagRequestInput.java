package org.onap.ccsdk.sli.northbound.vlantagapi.service.model;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UnassignVlanTagRequestInput {

    private @Valid String policyInstanceName = null;
    private @Valid String vlanType = null;
    private @Valid String key = null;

    /**
     **/
    public UnassignVlanTagRequestInput policyInstanceName(String policyInstanceName) {
        this.policyInstanceName = policyInstanceName;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("policy-instance-name")
    public String getPolicyInstanceName() {
        return policyInstanceName;
    }

    public void setPolicyInstanceName(String policyInstanceName) {
        this.policyInstanceName = policyInstanceName;
    }

    /**
     **/
    public UnassignVlanTagRequestInput vlanType(String vlanType) {
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
    public UnassignVlanTagRequestInput key(String key) {
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnassignVlanTagRequestInput unassignVlanTagRequestInput = (UnassignVlanTagRequestInput) o;
        return Objects.equals(policyInstanceName, unassignVlanTagRequestInput.policyInstanceName)
                && Objects.equals(vlanType, unassignVlanTagRequestInput.vlanType)
                && Objects.equals(key, unassignVlanTagRequestInput.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyInstanceName, vlanType, key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UnassignVlanTagRequestInput {\n");

        sb.append("    policyInstanceName: ").append(toIndentedString(policyInstanceName)).append("\n");
        sb.append("    vlanType: ").append(toIndentedString(vlanType)).append("\n");
        sb.append("    key: ").append(toIndentedString(key)).append("\n");
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
