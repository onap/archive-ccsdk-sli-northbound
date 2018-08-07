package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ResourceModel.class)
public class ResourceModel implements PolicyData {

    @JsonProperty("key-type")
    private String keyType;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("resource-resolution-recipe")
    private String resourceResolutionRecipe;

    @JsonProperty("resource-name")
    private String resourceName;

    @JsonProperty("data-store-object")
    private String dataStoreObject;

    @JsonProperty("data-store")
    private String dataStore;

    @JsonProperty("elements")
    private List<Elements> elements;

    @JsonProperty("resource-vlan-role")
    private String resourceVlanRole;

    @JsonProperty("vlan-type")
    private String vlanType;

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getResourceResolutionRecipe() {
        return resourceResolutionRecipe;
    }

    public void setResourceResolutionRecipe(String resourceResolutionRecipe) {
        this.resourceResolutionRecipe = resourceResolutionRecipe;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDataStoreObject() {
        return dataStoreObject;
    }

    public void setDataStoreObject(String dataStoreObject) {
        this.dataStoreObject = dataStoreObject;
    }

    public String getDataStore() {
        return dataStore;
    }

    public void setDataStore(String dataStore) {
        this.dataStore = dataStore;
    }

    public List<Elements> getElements() {
        return elements;
    }

    public void setElements(List<Elements> elements) {
        this.elements = elements;
    }

    public String getResourceVlanRole() {
        return resourceVlanRole;
    }

    public void setResourceVlanRole(String resourceVlanRole) {
        this.resourceVlanRole = resourceVlanRole;
    }

    public String getVlanType() {
        return vlanType;
    }

    public void setVlanType(String vlanType) {
        this.vlanType = vlanType;
    }

    @Override
    public String toString() {
        return "ResourceModel [keyType=" + keyType + ", scope=" + scope + ", resourceResolutionRecipe="
                + resourceResolutionRecipe + ", resourceName=" + resourceName + ", dataStoreObject=" + dataStoreObject
                + ", dataStore=" + dataStore + ", elements=" + elements + ", resourceVlanRole=" + resourceVlanRole
                + ", vlanType=" + vlanType + "]";
    }

}
