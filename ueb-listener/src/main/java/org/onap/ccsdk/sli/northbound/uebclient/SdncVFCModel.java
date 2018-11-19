/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 - 2018 AT&T Intellectual Property. All rights
 * 						reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.ccsdk.sli.northbound.uebclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
import org.onap.sdc.tosca.parser.impl.SdcPropertyNames;
import org.onap.sdc.toscaparser.api.NodeTemplate;
import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncVFCModel extends SdncBaseModel {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SdncVFCModel.class);
	
	private String vmType = null;
	private String vmCount = null;

	public SdncVFCModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate, DBResourceManager jdbcDataSource) {

		super(sdcCsarHelper, nodeTemplate, jdbcDataSource);
		
		// extract properties
		addParameter("ecomp_generated_naming", extractBooleanValue (nodeTemplate, "nfc_naming#ecomp_generated_naming"));
		addParameter("naming_policy", extractValue (nodeTemplate, "nfc_naming#naming_policy"));
		vmCount = extractValue (nodeTemplate, "service_template_filter#count"); // need path to vm_count, extracted as service_template_filter#count
		if (vmCount.isEmpty()) {
			vmCount = "0"; // vm_count can not be null
		}
		vmType = extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VMTYPETAG);
		addParameter("vm_type", vmType); // populate vm_type with vm_type_tag value
		addParameter("vm_type_tag", vmType);
		addParameter("nfc_naming_code", extractValue (nodeTemplate, "nfc_naming_code"));
		addParameter("nfc_function", extractValue (nodeTemplate, "nfc_function"));
		addParameter("high_availability", extractValue (nodeTemplate, "high_availablity"));
		addParameter("vm_image_name", extractValue (nodeTemplate, "vm_image_name"));
		addParameter("vm_flavor_name", extractValue (nodeTemplate, "vm_flavor_name"));
		addParameter("nfc_naming", extractValue (nodeTemplate, "nfc_naming"));
		addParameter("min_instances", extractValue (nodeTemplate, "min_instances"));
		addParameter("max_instances", extractValue (nodeTemplate, "max_instances"));
	}

	public void insertVFCModelData () throws IOException {
		try {
			cleanUpExistingToscaData("VFC_MODEL", "customization_uuid", getCustomizationUUID());
			LOG.info("Call insertToscaData for VFC_MODEL where customization_uuid = " + getCustomizationUUID());
			insertToscaData(buildSql("VFC_MODEL", model_yaml), null);
		} catch (IOException e) {
			LOG.error("Could not insert Tosca CSAR data into the VFC_MODEL table");
			throw new IOException (e);
		}

	}
	
	public void insertVFCtoNetworkRoleMappingData (NodeTemplate vfcNode) throws IOException {
		
		// For each VFC node, get CP properties to insert into VFC_TO_NETWORK_ROLE_MAPPING
		// VFC_TO_NETWORK_ROLE_MAPPING: vfc_customization_uuid, network_role, network_role_tag, vm_type, ipv4_count, ipv6_count,
		// ipv4_use_dhcp, ipv6_use_dhcp, ipv4_ip_version, ipv6_ip_version, extcp_subnetpool_id
		Map<String,Map<String,Object>> cpPropertiesMap = sdcCsarHelper.getCpPropertiesFromVfcAsObject(vfcNode);
		
		// DEBUG only
		if (cpPropertiesMap != null && !cpPropertiesMap.toString().contentEquals("{}")) {
			LOG.info("getCpPropertiesFromVfcAsObject for vfc_customization_uuid " + this.getCustomizationUUID() + ": "  + cpPropertiesMap.toString());
		}
		
		// Clean up all VFC_TO_NETWORK_ROLE_MAPPING data for this VFC node
		try {
			cleanUpExistingToscaData("VFC_TO_NETWORK_ROLE_MAPPING", "vfc_customization_uuid", getCustomizationUUID());
		} catch (IOException e) {
			LOG.error("Could not clean up data in VFC_TO_NETWORK_ROLE_MAPPING table ", e);
		}
		
		// There will be a cpPropertiesMap entry for each CP which will contain a map of properties to be inserted into VFC_TO_NETWORK_ROLE_MAPPING
		// There can be multiple insertions per CP:
		// 		Insert once for each unique IP Version / Subnet Role combination per CP (network_role)
		//		If there are IPV4 and IPV6 ip_requirements elements that have the same subnet_role (within a CP) combine those parameters for one insert 
		for (String nodeMapKey :  cpPropertiesMap.keySet()) {  // there will be one entry in this map per CP (network_role)
			LOG.debug("node key = " + nodeMapKey);
			Map<String,Object>  propsMap = cpPropertiesMap.get(nodeMapKey);
			Map<String, String> commonParams = new HashMap<String, String>();	// non-IP Version specific parameters
			
			// Get vm_type from VFC node
			SdncBaseModel.addParameter("vm_type", getVmType(), commonParams);
			
			// Extract non-IP Version specific parameters
			String networkRole = nullCheck(propsMap.get("network_role")).isEmpty() ? "default-network-role" : nullCheck(propsMap.get("network_role"));
			SdncBaseModel.addParameter("network_role", networkRole, commonParams); // can not be null
			SdncBaseModel.addParameter("network_role_tag", nullCheck(propsMap.get("network_role_tag")), commonParams);
			SdncBaseModel.addParameter("extcp_subnetpool_id", nullCheck(propsMap.get("subnetpoolid")), commonParams);

			// Loop thru all CPs using getNodeTemplateChildren and match the network_role on the CP with network_role from 
			// getCpPropertiesFromVfcAsObject output, then get subinterface_indicator for this CP
			List<NodeTemplate> cpNodesList = sdcCsarHelper.getNodeTemplateChildren(vfcNode);
			for (NodeTemplate cpNode : cpNodesList){
				String cpNetworkRole = extractValue(cpNode, "network_role");
				
				if (cpNetworkRole == networkRole) {
					String subinterfaceIndicator = extractBooleanValue (cpNode, "subinterface_indicator");
					addParameter("subinterface_indicator", subinterfaceIndicator, commonParams);
				}									
			}

			// Extract IP Version specific parameters
			String ipRequirementsString = nullCheck(propsMap.get("ip_requirements"));
			//ArrayList<Map<String, Object>>  ipPropsList =  (ArrayList<Map<String, Object>>) propsMap.get("ip_requirements");
			ArrayList<Map<String, Object>>  ipPropsList = new ArrayList<Map<String, Object>>(); 

			if (!ipRequirementsString.equals("{}")) {
				ipPropsList =  (ArrayList<Map<String, Object>>) propsMap.get("ip_requirements");
			}
			
			// Build lists of all IPV4 and IPV6 ip_requirements elements
			ArrayList<Map<String, String>> ipv4PropParamsList = new ArrayList<Map<String, String>>();
			ArrayList<Map<String, String>> ipv6PropParamsList = new ArrayList<Map<String, String>>();
			
			if (ipPropsList != null) {
				for (Map<String, Object> ipPropMap :  ipPropsList) {
					//LOG.info("ip_requirements prop map = " + nullCheck(ipPropMap));
	
					String ipVersion = nullCheck(ipPropMap.get("ip_version"));
					if (ipVersion == null) {
						LOG.error("SdncVFCModel: ipVersion not included in ip_requirements element");	
						continue;
					}

					String subnetRole = nullCheck(ipPropMap.get("subnet_role"));
					
					if (ipVersion.contains("4")) {

						// If we have already encountered this subnetRole for IPV4, skip this ip_requirements element
						if (!ipPropParamsMapContainsSubnetRole (ipv4PropParamsList, subnetRole)) {
						
							Map<String, String> ipv4PropParams = new HashMap<String, String>();
							SdncBaseModel.addParameter("ipv4_ip_version", ipVersion, ipv4PropParams);
							SdncBaseModel.addParameter("ipv4_use_dhcp", nullCheck(ipPropMap.get("dhcp_enabled")).contains("true") ? "Y" : "N", ipv4PropParams);
							Map<String, Object> ipCountRequired = (Map<String, Object>)ipPropMap.get("ip_count_required");
							if (ipCountRequired != null && ipCountRequired.get("count") != null) {
								SdncBaseModel.addParameter("ipv4_count", nullCheck(ipCountRequired.get("count")), ipv4PropParams);
							}
							Map<String, Object> floatingIpCountRequired = (Map<String, Object>)ipPropMap.get("floating_ip_count_required");
							if (floatingIpCountRequired != null && floatingIpCountRequired.get("count") != null) {
								SdncBaseModel.addParameter("ipv4_floating_count", nullCheck(floatingIpCountRequired.get("count")), ipv4PropParams);
							}
							SdncBaseModel.addParameter("ipv4_address_plan_name", nullCheck(ipPropMap.get("ip_address_plan_name")), ipv4PropParams);
							SdncBaseModel.addParameter("ipv4_vrf_name", nullCheck(ipPropMap.get("vrf_name")), ipv4PropParams);
							SdncBaseModel.addParameter("subnet_role", nullCheck(ipPropMap.get("subnet_role")), ipv4PropParams);
							
							ipv4PropParamsList.add(ipv4PropParams);
							
						} else {
							LOG.error("SdncVFCModel: Additional V4 ip-requirements element encountered for this subnet_role: ", subnetRole);
						}

					} else if (ipVersion.contains("6")) {

						// If we have already encountered this subnetRole for IPV6, skip this ip_requirements element
						if (!ipPropParamsMapContainsSubnetRole (ipv6PropParamsList, subnetRole)) { 
						
							Map<String, String> ipv6PropParams = new HashMap<String, String>();
							SdncBaseModel.addParameter("ipv6_ip_version", ipVersion, ipv6PropParams);
							SdncBaseModel.addParameter("ipv6_use_dhcp", nullCheck(ipPropMap.get("dhcp_enabled")).contains("true") ? "Y" : "N", ipv6PropParams);
							Map<String, Object> ipCountRequired = (Map<String, Object>)ipPropMap.get("ip_count_required");
							if (ipCountRequired != null && ipCountRequired.get("count") != null) {
								SdncBaseModel.addParameter("ipv6_count", nullCheck(ipCountRequired.get("count")), ipv6PropParams);
							}
							Map<String, Object> floatingIpCountRequired = (Map<String, Object>)ipPropMap.get("floating_ip_count_required");
							if (floatingIpCountRequired != null && floatingIpCountRequired.get("count") != null) {
								SdncBaseModel.addParameter("ipv6_floating_count", nullCheck(floatingIpCountRequired.get("count")), ipv6PropParams);
							}
							SdncBaseModel.addParameter("ipv6_address_plan_name", nullCheck(ipPropMap.get("ip_address_plan_name")), ipv6PropParams);
							SdncBaseModel.addParameter("ipv6_vrf_name", nullCheck(ipPropMap.get("vrf_name")), ipv6PropParams);
							SdncBaseModel.addParameter("subnet_role", nullCheck(ipPropMap.get("subnet_role")), ipv6PropParams);
							
							ipv6PropParamsList.add(ipv6PropParams);
							
						} else {							
							LOG.error("SdncVFCModel: Additional V6 ip-requirements element encountered for this subnetRole: ", subnetRole);							
						}
							
					} else {
						LOG.error("SdncVFCModel: invalid IP version encountered: ", ipVersion);
					}					
					
				} // for each ip-requirements element
				
			} // ipPropsList null check		
			
			// After all Common and IP Version specific parameters are extracted, insert IPV4 and IPV6 data separately
			// Insert IPV4 data
			for (Map<String, String> ipv4PropParams: ipv4PropParamsList) {
				
				Map<String, String> mappingParams = new HashMap<String, String>();	// final list for single insertion
				addParamsToMap(commonParams, mappingParams);
				addParamsToMap(ipv4PropParams, mappingParams);
								
				// Insert ipv4PropParams into VFC_TO_NETWORK_ROLE_MAPPING
				try {
					LOG.info("Call insertToscaData for VFC_TO_NETWORK_ROLE_MAPPING where vfc_customization_uuid = " + getCustomizationUUID());
					addRequiredParameters(mappingParams);
					insertToscaData(SdncBaseModel.getSql("VFC_TO_NETWORK_ROLE_MAPPING", "vfc_customization_uuid", getCustomizationUUID(), "", mappingParams), null);
				} catch (IOException e) {
					LOG.error("Could not insert Tosca CSAR data into the VFC_TO_NETWORK_ROLE_MAPPING table");
					throw new IOException (e);
				}	

			}
			
			// Insert IPV6 data
			for (Map<String, String> ipv6PropParams: ipv6PropParamsList) {
				
				Map<String, String> mappingParams = new HashMap<String, String>();	// final list for single insertion
				addParamsToMap(commonParams, mappingParams);
				addParamsToMap(ipv6PropParams, mappingParams);
				
				// Insert ipv6PropParams into VFC_TO_NETWORK_ROLE_MAPPING
				try {
					LOG.info("Call insertToscaData for VFC_TO_NETWORK_ROLE_MAPPING where vfc_customization_uuid = " + getCustomizationUUID());
					addRequiredParameters(mappingParams);
					insertToscaData(SdncBaseModel.getSql("VFC_TO_NETWORK_ROLE_MAPPING", "vfc_customization_uuid", getCustomizationUUID(), "", mappingParams), null);
				} catch (IOException e) {
					LOG.error("Could not insert Tosca CSAR data into the VFC_TO_NETWORK_ROLE_MAPPING table");
					throw new IOException (e);
				}	
				
			}
			
		} // Outer map loop - one per ExtCP

	}
		
	protected boolean ipPropParamsMapContainsSubnetRole (ArrayList<Map<String, String>> ipPropParamsList, String subnetRole) {
		
		boolean subnetRoleFound = false;
		
		if (subnetRole != null && !subnetRole.isEmpty()) { 
			for (Map<String, String> ipPropMap :  ipPropParamsList) {
				if (ipPropMap.get("subnet_role").contentEquals(subnetRole)) {
					return true;
				}
			}
		}
		return subnetRoleFound;
	}
	
	private void addRequiredParameters (Map<String, String> mappingParams) {
		
		// Add parameters which can not be null if they have not already been added - network_role, ipv4_count, ipv6_count
		String ipvCountDefault = "0";
		if (!mappingParams.containsKey("ipv4_count")) {
			SdncBaseModel.addParameter("ipv4_count", ipvCountDefault, mappingParams);
		}
		if (!mappingParams.containsKey("ipv6_count")) {
			SdncBaseModel.addParameter("ipv6_count", ipvCountDefault, mappingParams);
		}
	}
	
	public String getVmType() {
		return vmType;
	}

	public void setVmType(String vmType) {
		this.vmType = vmType;
	}

	public String getVmCount() {
		return vmCount;
	}

	public void setVmCount(String vmCount) {
		this.vmCount = vmCount;
	}

}
