package org.onap.ccsdk.sli.northbound.vlantagapi.service.model;

import javax.validation.Valid;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class AssignVlanTagRequestInput {
    private @Valid String policyInstanceName = null;
    private @Valid String resourceName = null;
    private @Valid String resourceValue = null;
    private @Valid String scopeId = null;
    private @Valid String key = null;
    private @Valid String vlanType = null;

    /**
     **/
    public AssignVlanTagRequestInput policyInstanceName(String policyInstanceName) {
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
    public AssignVlanTagRequestInput resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("resource-name")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     **/
    public AssignVlanTagRequestInput resourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("resource-value")
    public String getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    /**
     **/
    public AssignVlanTagRequestInput scopeId(String scopeId) {
        this.scopeId = scopeId;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("scope-id")
    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    /**
     **/
    public AssignVlanTagRequestInput key(String key) {
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
    public AssignVlanTagRequestInput vlanType(String vlanType) {
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssignVlanTagRequestInput assignVlanTagRequestInput = (AssignVlanTagRequestInput) o;
        return Objects.equals(policyInstanceName, assignVlanTagRequestInput.policyInstanceName)
                && Objects.equals(resourceName, assignVlanTagRequestInput.resourceName)
                && Objects.equals(resourceValue, assignVlanTagRequestInput.resourceValue)
                && Objects.equals(scopeId, assignVlanTagRequestInput.scopeId)
                && Objects.equals(key, assignVlanTagRequestInput.key)
                && Objects.equals(vlanType, assignVlanTagRequestInput.vlanType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyInstanceName, resourceName, resourceValue, scopeId, key, vlanType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AssignVlanTagRequestInput {\n");

        sb.append("    policyInstanceName: ").append(toIndentedString(policyInstanceName)).append("\n");
        sb.append("    resourceName: ").append(toIndentedString(resourceName)).append("\n");
        sb.append("    resourceValue: ").append(toIndentedString(resourceValue)).append("\n");
        sb.append("    scopeId: ").append(toIndentedString(scopeId)).append("\n");
        sb.append("    key: ").append(toIndentedString(key)).append("\n");
        sb.append("    vlanType: ").append(toIndentedString(vlanType)).append("\n");
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
