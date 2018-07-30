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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
import org.onap.sdc.tosca.parser.impl.SdcPropertyNames;
import org.onap.sdc.toscaparser.api.Group;
import org.onap.sdc.toscaparser.api.NodeTemplate;
import org.onap.sdc.toscaparser.api.Policy;
import org.onap.sdc.toscaparser.api.elements.Metadata;
import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncVFModel extends SdncBaseModel {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SdncVFModel.class);

	private String vendor = null;
	private String vendorModelDescription = null;
	private String nfNamingCode = null;
	private String serviceUUID = null;
	private String serviceInvariantUUID = null;

	public SdncVFModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate, DBResourceManager jdbcDataSource, SdncUebConfiguration config) throws IOException {

		super(sdcCsarHelper, nodeTemplate, jdbcDataSource, config);

		// extract metadata
		Metadata metadata = nodeTemplate.getMetaData();
		addParameter("name", extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_NAME));
		vendor = extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_RESOURCEVENDOR);
		addParameter("vendor", vendor); 
		vendorModelDescription = extractValue (metadata, "description");
		addParameter("vendor_version", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_RESOURCEVENDORRELEASE)); 
		
		// extract properties
		addParameter("ecomp_generated_naming", extractBooleanValue(nodeTemplate, "nf_naming#ecomp_generated_naming"));
		addParameter("naming_policy", extractValue(nodeTemplate, "nf_naming#naming_policy"));
		addParameter("nf_type", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NFTYPE));
		addParameter("nf_role", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NFROLE));
		nfNamingCode = extractValue(nodeTemplate, "nf_naming_code");
		addParameter("nf_code", nfNamingCode);
		addParameter("nf_function", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NFFUNCTION));
		addIntParameter("avail_zone_max_count", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_AVAILABILITYZONEMAXCOUNT));
		addParameter("sdnc_model_name", extractValue(nodeTemplate, "sdnc_model_name"));
		addParameter("sdnc_model_version", extractValue(nodeTemplate, "sdnc_model_version"));
		addParameter("sdnc_artifact_name", extractValue(nodeTemplate, "sdnc_artifact_name"));
		
		// store additional properties in ATTRIBUTE_VALUE_PAIR
		// additional complex properties are extracted via VfcInstanceGroup
		
		List<Group> vfcInstanceGroupListForVf = sdcCsarHelper.getGroupsOfOriginOfNodeTemplateByToscaGroupType(nodeTemplate, "org.openecomp.groups.VfcInstanceGroup");
		for (Group group : vfcInstanceGroupListForVf){

			String vfcInstanceGroupFunction = extractGetInputValue(group, nodeTemplate, "vfc_instance_group_function");
			addParameter(extractGetInputName (group, "vfc_instance_group_function"), vfcInstanceGroupFunction, attributeValueParams);
			String networkCollectionFunction = extractGetInputValue(group, nodeTemplate, "network_collection_function");
			addParameter(extractGetInputName (group, "network_collection_function"), networkCollectionFunction, attributeValueParams);
			String initSubinterfaceQuantity = extractGetInputValue(group, nodeTemplate, "init_subinterface_quantity");
			addParameter(extractGetInputName (group, "init_subinterface_quantity"), initSubinterfaceQuantity, attributeValueParams);
		}
	}
	
	public void insertData() throws IOException {
		
		insertVFModelData();
		insertVFModuleData(nodeTemplate, jdbcDataSource);
		insertVFtoNetworkRoleMappingData();
		insertVFCData();
		insertVFCInstanceGroupData();
		insertVFPolicyData();
	}
	
	private void insertVFModelData () throws IOException {

		try {
			cleanUpExistingToscaData("VF_MODEL", "customization_uuid", getCustomizationUUID()) ;
			//cleanUpExistingToscaData("SERVICE_MODEL_TO_VF_MODEL_MAPPING", "service_uuid", serviceUUID, "vf_uuid", getUUID());
			
			// insert into VF_MODEL/ATTRIBUTE_VALUE_PAIR and SERVICE_MODEL_TO_VF_MODEL_MAPPING
			LOG.info("Call insertToscaData for VF_MODEL where customization_uuid = " + getCustomizationUUID());
			insertToscaData(buildSql("VF_MODEL", model_yaml), null);
			//insertRelevantAttributeData();
			
			Map<String, String> mappingParams = new HashMap<String, String>();
			addParameter("service_invariant_uuid", serviceInvariantUUID, mappingParams);
			addParameter("vf_uuid", getUUID(), mappingParams);
			addParameter("vf_customization_uuid", getCustomizationUUIDNoQuotes(), mappingParams);
			//insertToscaData(buildSql("SERVICE_MODEL_TO_VF_MODEL_MAPPING", "service_uuid", serviceUUID, model_yaml, mappingParams), null);

		} catch (IOException e) {
			LOG.error("Could not insert Tosca CSAR data into the VF_MODEL table");
			throw new IOException (e);
		}
		
	}

	private void insertVFModuleData (NodeTemplate nodeTemplate, DBResourceManager jdbcDataSource) throws IOException {
		
		List<Group> vfModules = sdcCsarHelper.getVfModulesByVf(getCustomizationUUIDNoQuotes());
		for (Group group : vfModules){
			SdncVFModuleModel vfModuleModel = new SdncVFModuleModel(sdcCsarHelper, group, this);

			try {
				cleanUpExistingToscaData("VF_MODULE_MODEL", "customization_uuid", vfModuleModel.getCustomizationUUID());
				cleanUpExistingToscaData("VF_MODULE_TO_VFC_MAPPING", "vf_module_customization_uuid", vfModuleModel.getCustomizationUUID());
				LOG.info("Call insertToscaData for VF_MODULE_MODEL where vf_module_customization_uuid = " + vfModuleModel.getCustomizationUUID());
				insertToscaData(vfModuleModel.buildSql("VF_MODULE_MODEL", model_yaml), null);
			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the VF_MODULE_MODEL table ");
				throw new IOException (e);
			}

			// For each VF Module, get the VFC list, insert VF_MODULE_TO_VFC_MAPPING data
			// List<NodeTemplate> groupMembers = sdcCsarHelper.getMembersOfGroup(group); - old version
			// For each vfcNode (group member) in the groupMembers list, extract vm_type and vm_count.
			// Insert vf_module.customizationUUID, vfcNode.customizationUUID and vm_type and vm_count into VF_MODULE_TO_VFC_MAPPING
			List<NodeTemplate> groupMembers = sdcCsarHelper.getMembersOfVfModule(nodeTemplate, group); 
			for (NodeTemplate vfcNode : groupMembers){
				SdncVFCModel vfcModel = new SdncVFCModel(sdcCsarHelper, vfcNode, jdbcDataSource);

				try {
					LOG.info("Call insertToscaData for VF_MODULE_TO_VFC_MAPPING where vf_module_customization_uuid = " + vfModuleModel.getCustomizationUUID());
					insertToscaData("insert into VF_MODULE_TO_VFC_MAPPING (vf_module_customization_uuid, vfc_customization_uuid, vm_type, vm_count) values (" +
							vfModuleModel.getCustomizationUUID() + ", " + vfcModel.getCustomizationUUID() + ", \"" + vfcModel.getVmType() + "\", \"" + vfcModel.getVmCount() + "\")", null);
				} catch (IOException e) {
					LOG.error("Could not insert Tosca CSAR data into the VF_MODULE_TO_VFC_MAPPING table");
					throw new IOException (e);
				}

			}

		}
		
	}
	
	private void insertVFtoNetworkRoleMappingData () throws IOException {
		
		// For each VF, insert VF_TO_NETWORK_ROLE_MAPPING data
		List<NodeTemplate> cpNodes = sdcCsarHelper.getCpListByVf(getCustomizationUUIDNoQuotes());
		for (NodeTemplate cpNode : cpNodes){

			// Insert into VF_TO_NETWORK_ROLE_MAPPING vf_customization_uuid and network_role
			String cpNetworkRole = sdcCsarHelper.getNodeTemplatePropertyLeafValue(cpNode, "network_role");

			try {
				cleanUpExistingToscaData("VF_TO_NETWORK_ROLE_MAPPING", "vf_customization_uuid", getCustomizationUUID());
				LOG.info("Call insertToscaData for VF_TO_NETWORK_ROLE_MAPPING where vf_customization_uuid = " + getCustomizationUUID());
				insertToscaData("insert into VF_TO_NETWORK_ROLE_MAPPING (vf_customization_uuid, network_role) values (" +
				getCustomizationUUID() + ", \"" + cpNetworkRole + "\")", null);
			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the VF_TO_NETWORK_ROLE_MAPPING table");
				throw new IOException (e);
			}

		} // CP loop

	}
	
	private void insertVFCData() throws IOException {
		
		// For each VF, insert VFC_MODEL data
		List<NodeTemplate> vfcNodes = sdcCsarHelper.getVfcListByVf(getCustomizationUUIDNoQuotes());
		for (NodeTemplate vfcNode : vfcNodes){
			
			try {
				SdncVFCModel vfcModel = new SdncVFCModel(sdcCsarHelper, vfcNode, jdbcDataSource);
				
				vfcModel.insertVFCModelData();
				vfcModel.insertVFCtoNetworkRoleMappingData(vfcNode);
			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR VFC data");
				throw new IOException (e);
			}	
		}
	}
	
	public void insertVFCInstanceGroupData () throws IOException {
		
		// Insert Group data in RESOURCE_GROUP
		// Store group capabilities and capability properties in NODE_CAPABILITY and NODE_CAPABILITY_PROPERTY table
		
		// For each VF, insert CFVC data - 1806
		List<Group> vfcInstanceGroupListForVf = sdcCsarHelper.getGroupsOfOriginOfNodeTemplateByToscaGroupType(nodeTemplate, "org.openecomp.groups.VfcInstanceGroup");
		for (Group group : vfcInstanceGroupListForVf){

			SdncGroupModel groupModel = new SdncGroupModel (sdcCsarHelper, group, nodeTemplate, config, jdbcDataSource);	
			groupModel.insertGroupData(nodeTemplate);
		
			// For each group, populate NODE_CAPABILITY/NODE_CAPABILITY_PROPERTY
			insertNodeCapabilitiesData(group.getCapabilities());
			
			// Store relationship between VfcInstanceGroup and node-type=VFC in RESOURCE_GROUP_TO_TARGET_NODE_MAPPING table
			// target is each VFC in targets section of group
			List<NodeTemplate> targetNodeList = group.getMemberNodes();
			for (NodeTemplate targetNode : targetNodeList) {
				
				String targetNodeUuid = targetNode.getMetaData().getValue("UUID");
				
				// insert RESOURCE_GROUP_TO_TARGET_NODE_MAPPING
				try {
					Map<String, String> mappingCleanupParams = new HashMap<String, String>();
					addParameter("group_uuid", groupModel.getUUID(), mappingCleanupParams); 
					addParameter("parent_uuid", getUUID(), mappingCleanupParams);
					addParameter("target_node_uuid", targetNodeUuid, mappingCleanupParams);
					cleanupExistingToscaData("RESOURCE_GROUP_TO_TARGET_NODE_MAPPING", mappingCleanupParams);
					
					Map<String, String> mappingParams = new HashMap<String, String>();
					addParameter("parent_uuid", getUUID(), mappingParams);
					addParameter("target_node_uuid", targetNodeUuid, mappingParams);
					String targetType = extractValue(targetNode.getMetaData(), "type");
					addParameter("target_type", targetType, mappingParams);
					String tableName = "";
					switch (targetType) {
					case "CVFC":
						tableName = "VFC_MODEL";
						break;
					case "VL":
						tableName = "NETWORK_MODEL";
						break;
					}					
					addParameter("table_name", tableName, mappingParams); 
					LOG.info("Call insertToscaData for RESOURCE_GROUP_TO_TARGET_NODE_MAPPING where group_uuid = " + groupModel.getUUID() + " and target_node_uuid = " + targetNodeUuid);
					insertToscaData(buildSql("RESOURCE_GROUP_TO_TARGET_NODE_MAPPING", "group_uuid", groupModel.getUUID(), model_yaml, mappingParams), null);
				} catch (IOException e) {
					LOG.error("Could not insert Tosca CSAR data into the RESOURCE_GROUP_TO_TARGET_NODE_MAPPING");
					throw new IOException (e);
				}			
				
				// For each target node, get External policies 				
				insertPolicyData(nodeTemplate, targetNode, "org.openecomp.policies.External");
			}							
		}
	}

	private void insertVFPolicyData() throws IOException {
		
		// For each VF node, ingest External Policy data
		insertPolicyData (nodeTemplate, jdbcDataSource, serviceUUID, "org.openecomp.policies.External");
	}	

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVendorModelDescription() {
		return vendorModelDescription;
	}

	public void setVendorModelDescription(String vendorModelDescription) {
		this.vendorModelDescription = vendorModelDescription;
	}

	public String getNfNamingCode() {
		return nfNamingCode;
	}

	public void setNfNamingCode(String nfNamingCode) {
		this.nfNamingCode = nfNamingCode;
	}
	
	public String getServiceUUID() {
		return serviceUUID;
	}
	public void setServiceUUID(String serviceUUID) {
		this.serviceUUID = serviceUUID;
	}

	public String getServiceInvariantUUID() {
		return serviceInvariantUUID;
	}

	public void setServiceInvariantUUID(String serviceInvariantUUID) {
		this.serviceInvariantUUID = serviceInvariantUUID;
	}

}
