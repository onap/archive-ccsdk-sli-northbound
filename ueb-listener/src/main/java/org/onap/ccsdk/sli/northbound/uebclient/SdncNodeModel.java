/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 * 			reserved.
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

import org.openecomp.sdc.tosca.parser.api.ISdcCsarHelper;
import org.openecomp.sdc.tosca.parser.impl.SdcPropertyNames;
import org.openecomp.sdc.toscaparser.api.NodeTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncNodeModel extends SdncBaseModel {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SdncNodeModel.class);
	
	private String serviceUUID = null;
	private String ecompGeneratedNaming = null;
	private String [] bindingUuids = null; 
	
	// Using ASDC TOSCA Parser 17.07
	public SdncNodeModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate) {
		
		super(sdcCsarHelper, nodeTemplate);

		// extract inputs
		String ecompGeneratedNaming = extractBooleanInputDefaultValue(SdcPropertyNames.PROPERTY_NAME_SERVICENAMING_DEFAULT_ECOMPGENERATEDNAMING);
		addParameter("ecomp_generated_naming",ecompGeneratedNaming);
		addParameter("naming_policy", extractInputDefaultValue(SdcPropertyNames.PROPERTY_NAME_SERVICENAMING_DEFAULT_NAMINGPOLICY));
		
		// extract properties
		addParameter("network_type", extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NETWORKTYPE));
		addParameter("network_role", extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NETWORKROLE));
		addParameter("network_scope", extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NETWORKSCOPE));
		addParameter("network_technology", extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NETWORKTECHNOLOGY));

		// extract properties - network_assignments
		addParameter("is_shared_network", extractBooleanValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NETWORKASSIGNMENTS_ISSHAREDNETWORK));
		addParameter("is_external_network", extractBooleanValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NETWORKASSIGNMENTS_ISEXTERNALNETWORK));

		// extract properties - network_assignments - ipv4_subnet_default_assignment
		String useIpv4 = extractBooleanValue(nodeTemplate, "network_assignments#ipv4_subnet_default_assignment#use_ipv4");
		addParameter("use_ipv4", useIpv4);
		addParameter("ipv4_dhcp_enabled", extractBooleanValue(nodeTemplate, "network_assignments#ipv4_subnet_default_assignment#dhcp_enabled"));
		if (useIpv4.contains("Y")) {
			addParameter("ipv4_ip_version", "ipv4");
		}
		addParameter("ipv4_cidr_mask", extractValue(nodeTemplate, "network_assignments#ipv4_subnet_default_assignment#cidr_mask"));
		addParameter("eipam_v4_address_plan", extractValue(nodeTemplate, "network_assignments#ipv4_subnet_default_assignment#ip_network_address_plan"));
		
		// extract properties - network_assignments - ipv6_subnet_default_assignment
		String useIpv6 = extractBooleanValue(nodeTemplate, "network_assignments#ipv6_subnet_default_assignment#use_ipv6");
		addParameter("use_ipv6", useIpv6);
		addParameter("ipv6_dhcp_enabled", extractBooleanValue(nodeTemplate, "network_assignments#ipv6_subnet_default_assignment#dhcp_enabled"));
		if (useIpv6.contains("Y")) {
			addParameter("ipv6_ip_version", "ipv6");
		}
		addParameter("ipv6_cidr_mask", extractValue(nodeTemplate, "network_assignments#ipv6_subnet_default_assignment#cidr_mask"));
		addParameter("eipam_v6_address_plan", extractValue(nodeTemplate, "network_assignments#ipv6_subnet_default_assignment#ip_network_address_plan"));
		
		// extract properties - provider_network
		addParameter("is_provider_network", extractBooleanValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_PROVIDERNETWORK_ISPROVIDERNETWORK));
		addParameter("physical_network_name", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_PROVIDERNETWORK_PHYSICALNETWORKNAME));

		// extract properties - network_flows
		addParameter("is_bound_to_vpn", extractBooleanValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NETWORKFLOWS_ISBOUNDTOVPN));

		// extract properties - network_flows - vpn_bindings
		String vpnBindingString = extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NETWORKFLOWS_VPNBINDING);
		bindingUuids = vpnBindingString.split(",");

}

	public String getServiceUUID() {
		return serviceUUID;
	}
	public void setServiceUUID(String serviceUUID) {
		this.serviceUUID = serviceUUID;
	}

	public String getEcompGeneratedNaming() {
		return ecompGeneratedNaming;
	}
	public void setEcompGeneratedNaming(String ecompGeneratedNaming) {
		this.ecompGeneratedNaming = ecompGeneratedNaming;
		if (ecompGeneratedNaming != null && !ecompGeneratedNaming.isEmpty()) {
			params.put("ecomp_generated_naming", "\"" + ecompGeneratedNaming + "\"");			
		}
	}

	public String getSql(String model_yaml) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT into NETWORK_MODEL (service_uuid, customization_uuid, model_yaml, ");
		
		int paramCount = 0;
		for (String paramKey :  params.keySet()) {
			paramCount++;
		    sb.append(paramKey);
		    if (paramCount < params.size()) sb.append(", ");
		}
		
		sb.append(") values (" + serviceUUID + ", " + getCustomizationUUID() + ", \"" + model_yaml + "\", ");

		paramCount = 0;
		for (String paramKey :  params.keySet()) {
			paramCount++;
			String paramValue = params.get(paramKey);
		    sb.append(paramValue);
		    if (paramCount < params.size()) sb.append(", ");
		}

		sb.append(");");
		return sb.toString();
	}

	public String getVpnBindingsSql() {
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < bindingUuids.length; i++) {
			sb.append("INSERT into VPN_BINDINGS (network_customization_uuid, binding_uuid) values (" + getCustomizationUUID() + ", \"" + bindingUuids[i] + "\"); ");
		}

		return sb.toString();
	}
	
}
