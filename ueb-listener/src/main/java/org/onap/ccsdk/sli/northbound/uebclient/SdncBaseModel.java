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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
import org.onap.sdc.tosca.parser.impl.SdcPropertyNames;
import org.onap.sdc.toscaparser.api.CapabilityAssignment;
import org.onap.sdc.toscaparser.api.CapabilityAssignments;
import org.onap.sdc.toscaparser.api.Group;
import org.onap.sdc.toscaparser.api.NodeTemplate;
import org.onap.sdc.toscaparser.api.Policy;
import org.onap.sdc.toscaparser.api.Property;
import org.onap.sdc.toscaparser.api.elements.Metadata;
import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncBaseModel {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SdncBaseModel.class);
	
	protected String customizationUUID = null;
	protected String invariantUUID = null;
	protected String UUID = null;
	protected String model_yaml = null;	
	protected String version = null;	
	protected String name = null;	

	protected Map<String, String> params = null;
	protected Map<String, String> attributeValueParams = null;
	protected ISdcCsarHelper sdcCsarHelper = null;
	protected static DBResourceManager jdbcDataSource = null;
	protected static SdncUebConfiguration config = null;
	protected NodeTemplate nodeTemplate = null;
	
	public SdncBaseModel(DBResourceManager jdbcDataSource) {
		this.jdbcDataSource = jdbcDataSource;		
	}
	
	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate, DBResourceManager jdbcDataSource) {
		this (sdcCsarHelper, nodeTemplate);
		this.sdcCsarHelper = sdcCsarHelper;
		this.nodeTemplate = nodeTemplate;
		this.jdbcDataSource = jdbcDataSource;		
	}

	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate, DBResourceManager jdbcDataSource, SdncUebConfiguration config) throws IOException {
		this (sdcCsarHelper, nodeTemplate);
		this.sdcCsarHelper = sdcCsarHelper;
		this.nodeTemplate = nodeTemplate;
		this.jdbcDataSource = jdbcDataSource;		
		this.config = config;
	}

	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, Metadata metadata) {

		params = new HashMap<String, String>();
		this.sdcCsarHelper = sdcCsarHelper;

		// extract service metadata
		invariantUUID = extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID);
		addParameter("invariant_uuid",invariantUUID);
		addParameter("version",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_VERSION));
		name = extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_NAME);
		addParameter("name",name);
		addParameter("description",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_DESCRIPTION));
		addParameter("type",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_TYPE));
		addParameter("category",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_CATEGORY));
	}

	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate) {

		params = new HashMap<String, String>();
		attributeValueParams = new HashMap<String, String>();
		this.sdcCsarHelper = sdcCsarHelper;
		this.nodeTemplate = nodeTemplate;

		// extract common nodeTemplate metadata
		Metadata metadata = nodeTemplate.getMetaData();
		customizationUUID = extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_CUSTOMIZATIONUUID);
		invariantUUID = extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID);
		addParameter("invariant_uuid", invariantUUID);
		UUID = extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_UUID);
		addParameter("uuid", UUID);
		addParameter("version", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VERSION)); 
		
		// extract common nodeTemplate properties
		//addParameter("ecomp_generated_naming", extractValue (nodeTemplate, "naming#ecompnaming")); // should be extractBooleanValue?
		//addParameter("naming_policy", extractValue (nodeTemplate, "naming#namingpolicy"));
		
	}
	
	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, Group group, SdncUebConfiguration config, DBResourceManager jdbcDataSource) throws IOException {
		this (sdcCsarHelper, group);
		this.sdcCsarHelper = sdcCsarHelper;
		this.config = config;
		this.jdbcDataSource = jdbcDataSource;		
	}

	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, Group group) {

		params = new HashMap<String, String>();
		this.sdcCsarHelper = sdcCsarHelper;
		attributeValueParams = new HashMap<String, String>();

		// extract group metadata
		Metadata metadata = group.getMetadata();
		//customizationUUID = extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VFMODULECUSTOMIZATIONUUID); - returning null
		customizationUUID = extractValue (metadata, "vfModuleModelCustomizationUUID");
		addParameter("invariant_uuid", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELINVARIANTUUID));			
		addParameter("uuid", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELUUID));			
		addParameter("version", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELVERSION));			
	}
	
/*	This is the generic approach Shoujit attempted for 18.06 but can't be implemented without parser API to 
 *  get properties with substring match on the name
 * protected void extractRelevantAttributeData(List<Property> propList, SdncUebConfiguration config) {

		//List<Property> propList = nodeTemplate.getPropertiesObjects();
		for (Property prop : propList) {
			String propName = prop.getName();
			Object propValue = prop.getValue();
			
			if (propValue instanceof Map)
			
			LOG.info("Property: propertyName: " + propName + " propertyValue: " + propValue.toString());
			
			// Compare this property name with each config.relevant-attribute-name
			List<String> attributeNames =  config.getRelevantAttributeNames();
			for (String attributeName : attributeNames) {
				if (prop.getName().contains(attributeName))
				addParameter(prop.getName(), prop.getValue().toString(), attributeValueParams);
			}			

		}

	}*/
	
	protected void insertRelevantAttributeData() throws IOException{
		
		insertRelevantAttributeData("");
	}

	protected void insertRelevantAttributeData(String type) throws IOException{
		
		// type can be passed as "group" or taken from the nodeTemplate
		String metadataType = "";
		if (!type.isEmpty()) metadataType = type;
		else {
			Metadata metadata = nodeTemplate.getMetaData();
			metadataType = sdcCsarHelper.getMetadataPropertyValue(metadata, "type");				
		}
		
		// Clean up all attributes for this resource
		try {
			cleanUpExistingToscaData("ATTRIBUTE_VALUE_PAIR", "resource_uuid", getUUID(), "resource_type", "\"" + metadataType + "\"");
		} catch (IOException e) {
			LOG.error("Could not cleanup Tosca CSAR data from the ATTRIBUTE_VALUE_PAIR table");
			throw new IOException (e);
		}
		
		for (String paramName : attributeValueParams.keySet()) {
			String paramValue = attributeValueParams.get(paramName);
			
			Map<String, String> attributeParams = new HashMap<String, String>();
			addParameter("attribute_name", paramName, attributeParams);
			addParameter("attribute_value", paramValue, attributeParams);			
			addParameter("resource_type", metadataType, attributeParams);
			addParameter("resource_customization_uuid", getCustomizationUUID(), attributeParams);
	
			LOG.info("Call insertToscaData for ATTRIBUTE_VALUE_PAIR where resource_uuid = " + getUUID() + " and attriubute_name = " + paramName);
			try {
				insertToscaData(buildSql("ATTRIBUTE_VALUE_PAIR", "resource_uuid", getUUID(), model_yaml, attributeParams), null);
			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the ATTRIBUTE_VALUE_PAIR table");
				throw new IOException (e);
			}
		}		
	}
	
	protected void insertGroupData (NodeTemplate nodeTemplate, NodeTemplate targetNode, String groupType) throws IOException {
		
		// Get the NetworkCollection groups of the node
		Map<String, String> groupParams = new HashMap<String, String>();
		List<Group> groupList = sdcCsarHelper.getGroupsOfOriginOfNodeTemplateByToscaGroupType(nodeTemplate, groupType);
		//List<Group> groupList2 = sdcCsarHelper.getGroupsOfTopologyTemplateByToscaGroupType(groupType); // returns nothing

		for (Group group : groupList) {
			
			// Insert into RESOURCE_GROUP/ATTRIBUTE_VALUE_PAIR and RESOURCE_GROUP_TO_TARGET_NODE_MAPPING
			// RESOURCE_GROUP (group metadata): resource_uuid (CR node UUID), uuid, customization_uuid, invariant_uuid, name, version
			// ATTRIBUTE_VALUE_PAIR (group properties): group_type, group_role, group_function
			// RESOURCE_GROUP_TO_TARGET_NODE_MAPPING: group_uuid, parent_uuid (CR node UUID), target_node_uuid, target_type, table_name

			SdncGroupModel groupModel = new SdncGroupModel (sdcCsarHelper, group, nodeTemplate, config, jdbcDataSource);	
			groupModel.insertGroupData(nodeTemplate);
			
			// insert RESOURCE_GROUP_TO_TARGET_NODE_MAPPING
			try {
				Map<String, String> mappingCleanupParams = new HashMap<String, String>();
				addParameter("group_uuid", groupModel.getUUID(), mappingCleanupParams); 
				addParameter("parent_uuid", extractValue(nodeTemplate.getMetaData(), "UUID"), mappingCleanupParams);
				addParameter("target_node_uuid", extractValue(targetNode.getMetaData(), "UUID"), mappingCleanupParams);
				cleanupExistingToscaData("RESOURCE_GROUP_TO_TARGET_NODE_MAPPING", mappingCleanupParams);
				
				Map<String, String> mappingParams = new HashMap<String, String>();
				addParameter("parent_uuid", extractValue(nodeTemplate.getMetaData(), "UUID"), mappingParams);
				addParameter("target_node_uuid", extractValue(targetNode.getMetaData(), "UUID"), mappingParams);
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
				LOG.info("Call insertToscaData for RESOURCE_GROUP_TO_TARGET_NODE_MAPPING where group_uuid = " + groupModel.getUUID());
				insertToscaData(buildSql("RESOURCE_GROUP_TO_TARGET_NODE_MAPPING", "group_uuid", groupModel.getUUID(), model_yaml, mappingParams), null);

			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the RESOURCE_GROUP_TO_TARGET_NODE_MAPPING");
				throw new IOException (e);
			}			
		}		
	}
	
	protected void insertPolicyData (NodeTemplate nodeTemplate, NodeTemplate targetNode, String policyType) throws IOException {
		
		// Get External policies of the node	
		List<Policy> policyList = sdcCsarHelper.getPoliciesOfOriginOfNodeTemplateByToscaPolicyType(nodeTemplate, policyType);
		//List<Policy> policyList2 = sdcCsarHelper.getPoliciesOfTopologyTemplateByToscaPolicyType(policyType); // returns nothing
		//List<Policy> policyList3 = sdcCsarHelper.getPoliciesOfTargetByToscaPolicyType(nodeTemplate, policyType); // returns nothing

		String resourceUuid = getUUID();

		for (Policy policy : policyList) {
			
			// extract policy metadata
			String policyUuid = policy.getMetaData().getOrDefault("UUID", "").toString(); 
			String policyInvariantUuid = policy.getMetaData().getOrDefault("invariantUUID", "").toString();
			String policyCustomizationUuid = policy.getMetaData().getOrDefault("customizationUUID", "").toString();
			
			// cleanup existing RESOURCE_POLICY data
			Map<String, String> cleanupParams = new HashMap<String, String>();
			addParameter("resource_uuid", resourceUuid, cleanupParams); 
			addParameter("policy_uuid", policyUuid, cleanupParams);
			addParameter("policy_invariant_uuid", policyInvariantUuid, cleanupParams);
			
			// insert into RESOURCE_POLICY
			Map<String, String> policyParams = new HashMap<String, String>();
			addParameter("policy_uuid", policyUuid, policyParams);
			addParameter("policy_customization_uuid", policyCustomizationUuid, policyParams);
			addParameter("policy_invariant_uuid", policyInvariantUuid, policyParams);
			addParameter("policy_name", policy.getMetaData().getOrDefault("name", "").toString(), policyParams);
			addParameter("version", policy.getMetaData().getOrDefault("version", "").toString(), policyParams);
			addParameter("policy_type", policy.getMetaData().getOrDefault("type", "").toString(), policyParams);
			
			// extract properties
			addParameter("property_type", extractValue(policy, "type"), policyParams);
			addParameter("property_source", extractValue(policy, "source"), policyParams);
			addParameter("property_name", extractValue(policy, "name"), policyParams);			

			// Insert into RESOURCE_POLICY and RESOURCE_POLICY_TO_TARGET_NODE_MAPPING
			// RESOURCE_POLICY: resource_uuid (CR node UUID), uuid, customization_uuid, invariant_uuid, name, version, policy_type, 
			// property_type, property_source, property_name
			
			try {
				
				// insert into RESOURCE_POLICY
				cleanupExistingToscaData("RESOURCE_POLICY", cleanupParams);
				LOG.info("Call insertToscaData for RESOURCE_POLICY where resource_uuid = " + resourceUuid + " and policy_uuid = " + policyUuid);
				insertToscaData(buildSql("RESOURCE_POLICY", "resource_uuid", resourceUuid, model_yaml, policyParams), null);

			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the RESOURCE_POLICY table");
				throw new IOException (e);
			}
			
			// insert RESOURCE_POLICY_TO_TARGET_NODE_MAPPING: policy_uuid, parent_uuid (CR node UUID), target_node_uuid, target_type, table_name
			try {
				Map<String, String> mappingCleanupParams = new HashMap<String, String>();
				addParameter("policy_uuid", policyUuid, mappingCleanupParams); 
				addParameter("parent_uuid", nodeTemplate.getMetaData().getValue("UUID"), mappingCleanupParams);
				addParameter("target_node_uuid", targetNode.getMetaData().getValue("UUID"), mappingCleanupParams);
				cleanupExistingToscaData("RESOURCE_POLICY_TO_TARGET_NODE_MAPPING", mappingCleanupParams);
				
				Map<String, String> mappingParams = new HashMap<String, String>();
				addParameter("parent_uuid", nodeTemplate.getMetaData().getValue("UUID"), mappingParams);
				addParameter("target_node_uuid", targetNode.getMetaData().getValue("UUID"), mappingParams);
				addParameter("target_node_customization_uuid", targetNode.getMetaData().getValue("customizationUUID"), mappingParams);
				addParameter("policy_customization_uuid", policyCustomizationUuid, mappingParams);
				addParameter("target_type", targetNode.getMetaData().getValue("type"), mappingParams);  
				LOG.info("Call insertToscaData for RESOURCE_POLICY_TO_TARGET_NODE_MAPPING where policy_uuid = " + policyUuid);
				insertToscaData(buildSql("RESOURCE_POLICY_TO_TARGET_NODE_MAPPING", "policy_uuid", "\"" + policyUuid + "\"", model_yaml, mappingParams), null);

			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the RESOURCE_POLICY_TO_TARGET_NODE_MAPPING");
				throw new IOException (e);
			}			
		}
	}
	
	public static void insertPolicyData (ISdcCsarHelper sdcCsarHelper, DBResourceManager jdbcDataSource, String resourceUuid, String parentUuid, String policyType) throws IOException {
		
		// Get External policies of the node	
		List<Policy> policyList = sdcCsarHelper.getPoliciesOfTopologyTemplateByToscaPolicyType(policyType);
		
		for (Policy policy : policyList) {
			
			// extract policy metadata
			String policyUuid = policy.getMetaData().getOrDefault("UUID", "").toString();
			String policyInvariantUuid = policy.getMetaData().getOrDefault("invariantUUID", "").toString();
			
			// cleanup existing RESOURCE_POLICY data
			Map<String, String> cleanupParams = new HashMap<String, String>();
			addParameter("resource_uuid", resourceUuid, cleanupParams); 
			addParameter("policy_uuid", policyUuid, cleanupParams);
			addParameter("policy_invariant_uuid", policyInvariantUuid, cleanupParams);
			
			// insert into RESOURCE_POLICY
			Map<String, String> policyParams = new HashMap<String, String>();
			addParameter("policy_uuid", policyUuid, policyParams);
			addParameter("policy_invariant_uuid", policyInvariantUuid, policyParams);
			addParameter("policy_name", policy.getMetaData().getOrDefault("name", "").toString(), policyParams);
			addParameter("version", policy.getMetaData().getOrDefault("version", "").toString(), policyParams);
			addParameter("policy_type", policy.getType(), policyParams);
			
			// extract properties
			addParameter("property_type", extractValueStatic(policy, "type"), policyParams);
			addParameter("property_source", extractValueStatic(policy, "source"), policyParams);
			addParameter("property_name", extractValueStatic(policy, "name"), policyParams);	
			
			try {
				
				// insert into RESOURCE_POLICY
				SdncBaseModel.cleanupExistingToscaData(jdbcDataSource, "RESOURCE_POLICY", cleanupParams);
				LOG.info("Call insertToscaData for RESOURCE_POLICY where resource_uuid = " + resourceUuid);
				insertToscaData(jdbcDataSource, getSql("RESOURCE_POLICY", "resource_uuid", resourceUuid, "", policyParams), null);

			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the RESOURCE_POLICY table");
				throw new IOException (e);
			}
			
			// insert into RESOURCE_POLICY_TO_TARGET_NODE_MAPPING
			List<String> policyTargetNameList = policy.getTargets();
			for (String targetName : policyTargetNameList) {
				NodeTemplate targetNode = sdcCsarHelper.getNodeTemplateByName(targetName);
				
				// insert into RESOURCE_POLICY_TO_TARGET_NODE_MAPPING
				try {
					Map<String, String> mappingCleanupParams = new HashMap<String, String>();
					addParameter("policy_uuid", policyUuid, mappingCleanupParams); 
					addParameter("parent_uuid", parentUuid, mappingCleanupParams);
					addParameter("target_node_uuid", targetNode.getMetaData().getValue("UUID"), mappingCleanupParams);
					SdncBaseModel.cleanupExistingToscaData(jdbcDataSource, "RESOURCE_POLICY_TO_TARGET_NODE_MAPPING", mappingCleanupParams);
					
					Map<String, String> mappingParams = new HashMap<String, String>();
					addParameter("parent_uuid", parentUuid, mappingParams);
					addParameter("target_node_uuid", targetNode.getMetaData().getValue("UUID"), mappingParams);
					addParameter("target_node_customization_uuid", targetNode.getMetaData().getValue("customizationUUID"), mappingParams);
					addParameter("target_type", targetNode.getMetaData().getValue("type"), mappingParams);  // type of the target node
					LOG.info("Call insertToscaData for RESOURCE_POLICY_TO_TARGET_NODE_MAPPING where policy_uuid = " + policyUuid + " and target_node_uuid = " + targetNode.getMetaData().getValue("UUID"));
					SdncBaseModel.insertToscaData(jdbcDataSource, getSql("RESOURCE_POLICY_TO_TARGET_NODE_MAPPING", "policy_uuid", "\"" + policyUuid + "\"", "", mappingParams), null);

				} catch (IOException e) {
					LOG.error("Could not insert Tosca CSAR data into the RESOURCE_POLICY_TO_TARGET_NODE_MAPPING");
					throw new IOException (e);
				}			
			
			}
		}
	}
	
	protected void insertPolicyData (NodeTemplate nodeTemplate, DBResourceManager jdbcDataSource, String parentUuid, String policyType) throws IOException {
		
		// Get External policies of the node	
		List<Policy> policyList = sdcCsarHelper.getPoliciesOfOriginOfNodeTemplateByToscaPolicyType(nodeTemplate, policyType);
		String resourceUuid = "\"" + extractValue (nodeTemplate.getMetaData(), SdcPropertyNames.PROPERTY_NAME_UUID) + "\"";
		
		for (Policy policy : policyList) {
			
			// extract policy metadata
			String policyUuid = policy.getMetaData().getOrDefault("UUID", "").toString();
			String policyInvariantUuid = policy.getMetaData().getOrDefault("invariantUUID", "").toString();
			
			// cleanup existing RESOURCE_POLICY data
			Map<String, String> cleanupParams = new HashMap<String, String>();
			addParameter("resource_uuid", resourceUuid, cleanupParams); 
			addParameter("policy_uuid", policyUuid, cleanupParams);
			addParameter("policy_invariant_uuid", policyInvariantUuid, cleanupParams);
			
			// insert into RESOURCE_POLICY
			Map<String, String> policyParams = new HashMap<String, String>();
			addParameter("policy_uuid", policyUuid, policyParams);
			addParameter("policy_invariant_uuid", policyInvariantUuid, policyParams);
			String policyName = policy.getMetaData().getOrDefault("name", "").toString();
			addParameter("policy_name", policyName, policyParams);
			addParameter("version", policy.getMetaData().getOrDefault("version", "").toString(), policyParams);
			addParameter("policy_type", policy.getType(), policyParams);
			
			// extract properties
			addParameter("property_type", extractValue(policy, "type"), policyParams);
			addParameter("property_source", extractValue(policy, "source"), policyParams);
			addParameter("property_name", extractValue(policy, "name"), policyParams);	
			
			try {
				
				// insert into RESOURCE_POLICY
				cleanupExistingToscaData(jdbcDataSource, "RESOURCE_POLICY", cleanupParams);
				LOG.info("Call insertToscaData for RESOURCE_POLICY where resource_uuid = " + resourceUuid);
				insertToscaData(jdbcDataSource, getSql("RESOURCE_POLICY", "resource_uuid", resourceUuid, "", policyParams), null);

			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the RESOURCE_POLICY table");
				throw new IOException (e);
			}
			
			// insert into RESOURCE_POLICY_TO_TARGET_NODE_MAPPING
			List<NodeTemplate> targetNodeList = sdcCsarHelper.getPolicyTargetsFromOrigin(nodeTemplate, policyName);
			for (NodeTemplate targetNode : targetNodeList) {
				//NodeTemplate targetNode = sdcCsarHelper.getNodeTemplateByName(targetName);
				if (targetNode == null) {					
					LOG.error("Target node for policy " + policyName + " is NULL.  Can't insert into RESOURCE_POLICY_TO_TARGET_NODE_MAPPING");
					continue;
				}
				
				// insert into RESOURCE_POLICY_TO_TARGET_NODE_MAPPING
				try {
					Map<String, String> mappingCleanupParams = new HashMap<String, String>();
					addParameter("policy_uuid", policyUuid, mappingCleanupParams); 
					addParameter("parent_uuid", parentUuid, mappingCleanupParams);
					addParameter("target_node_uuid", targetNode.getMetaData().getValue("UUID"), mappingCleanupParams);
					SdncBaseModel.cleanupExistingToscaData(jdbcDataSource, "RESOURCE_POLICY_TO_TARGET_NODE_MAPPING", mappingCleanupParams);
					
					Map<String, String> mappingParams = new HashMap<String, String>();
					addParameter("parent_uuid", parentUuid, mappingParams);
					addParameter("target_node_uuid", targetNode.getMetaData().getValue("UUID"), mappingParams);
					addParameter("target_node_customization_uuid", targetNode.getMetaData().getValue("customizationUUID"), mappingParams);
					addParameter("target_type", targetNode.getMetaData().getValue("type"), mappingParams);  // type of the target node
					LOG.info("Call insertToscaData for RESOURCE_POLICY_TO_TARGET_NODE_MAPPING where policy_uuid = " + policyUuid + " and target_node_uuid = " + targetNode.getMetaData().getValue("UUID"));
					SdncBaseModel.insertToscaData(jdbcDataSource, getSql("RESOURCE_POLICY_TO_TARGET_NODE_MAPPING", "policy_uuid", "\"" + policyUuid + "\"", "", mappingParams), null);

				} catch (IOException e) {
					LOG.error("Could not insert Tosca CSAR data into the RESOURCE_POLICY_TO_TARGET_NODE_MAPPING table");
					throw new IOException (e);
				}			
			
			}
		}
	}
	
	protected void insertNodeCapabilitiesData (CapabilityAssignments capabilities) throws IOException {		
		
		// Process the capabilities on the node template
		
		List<CapabilityAssignment> capabilityList = capabilities.getAll();
		
		for (CapabilityAssignment capability : capabilities.getAll()) {
							
			// Insert into NODE_CAPABILITY: 
			// capability_id (generated) 
			// capability_provider_uuid - UUID of this node 
			// capability_provider_customization_uuid - customization UUID of this node
			// capability_name - capability.getName()
			// capability_type - ?

			// Check capability name against relevant capabilities
			boolean capabilityIsRelevant = false;
			/*List<String> relevantCapabilities = config.getRelevantCapabilityNames();
			for (String relevantCapabilityName : relevantCapabilities ) {
				
				if (capability.getName().toLowerCase().contains(relevantCapabilityName.toLowerCase())) {
					capabilityIsRelevant = true;
				}
			}*/
			
			if (capabilityIsRelevant == false){
				continue;
			}
			
			String capabilityProviderUuid = getUUID(); 

			Map<String, String> cleanupParams = new HashMap<String, String>();
			addParameter("capability_provider_uuid", capabilityProviderUuid, cleanupParams);  // node customization UUID
			addParameter("capability_provider_customization_uuid", getCustomizationUUIDNoQuotes(), cleanupParams);  // node customization UUID
			addParameter("capability_name", capability.getName(), cleanupParams);

			Map<String, String> nodeCapabilityParams = new HashMap<String, String>();
			addParameter("capability_provider_customization_uuid", getCustomizationUUIDNoQuotes(), nodeCapabilityParams);  // node customization UUID
			addParameter("capability_name", capability.getName(), nodeCapabilityParams);
			addParameter("capability_type", extractValue(capability, "type"), nodeCapabilityParams);
			
			// Insert NODE_CAPABILITY data for each capability
			String capabilityId = "";
			try {

				cleanupExistingToscaData("NODE_CAPABILITY", cleanupParams); // will also delete NODE_CAPABILITY_PROPERTY with same capability_id
				LOG.info("Call insertToscaData for NODE_CAPABILITY where capability_provider_uuid = " + capabilityProviderUuid + " and capability_name = " + capability.getName());
				insertToscaData(buildSql("NODE_CAPABILITY", "capability_provider_uuid", capabilityProviderUuid, model_yaml, nodeCapabilityParams), null);
				
				// Get capabilityId for capability just inserted
				CachedRowSet rowData = getToscaData("NODE_CAPABILITY", nodeCapabilityParams);
				rowData.first();
				int capabilityIdint = rowData.getInt("capability_id");
				capabilityId = capabilityId.valueOf(capabilityIdint);
				
			} catch (IOException | SQLException e) {
				LOG.error("Could not insert Tosca CSAR data into the NODE_CAPABILITY table");
				throw new IOException (e);
			}

			insertNodeCapabilityPropertyData (capability, capabilityId);
		}
	}
	
	protected void insertNodeCapabilityPropertyData(CapabilityAssignment capability, String capabilityId) throws IOException {
		
		// Insert property name / value into NODE_CAPABILITY_PROPERTY
		LinkedHashMap<String, Property> propertiesMap = capability.getProperties();
		Map<String, String> nodeCapabilityPropertyParams = new HashMap<String, String>();
		
		for (String propertyMapKey : propertiesMap.keySet() ) {
			//LOG.info("property map key = " + propertyMapKey);				
			Property property = propertiesMap.get(propertyMapKey);
			
			addParameter ("capability_property_name", property.getName(), nodeCapabilityPropertyParams);
			addParameter ("capability_property_type", property.getValue().toString(), nodeCapabilityPropertyParams);
			
			try {
				// Data from NODE_CAPABILITY_PROPERTY is cleaned up via cascade delete on NODE_CAPABILITY  
				LOG.info("Call insertToscaData for NODE_CAPABILITY_PROPERTY where capability_id = " + capabilityId + " and property_name = " + property.getName() + ", property_value: " + property.getValue().toString());
				insertToscaData(buildSql("NODE_CAPABILITY_PROPERTY", "capability_id", capabilityId, model_yaml, nodeCapabilityPropertyParams), null);
			} catch (IOException e) {
				LOG.error("Could not insert Tosca CSAR data into the NODE_CAPABILITY_PROPERTY table");
				throw new IOException (e);
			}
		}

	}

	protected void addParameter (String name, String value) {
		if (value != null && !value.isEmpty()) {
			// check if value already contain quotes
			if (value.startsWith("\"", 0) && value.endsWith("\"")) {
				params.put(name, value);
			} else {
				params.put(name, "\"" + value + "\"");
			}
		}
	}

	protected void addIntParameter (String name, String value) {
		if (value != null && !value.isEmpty()) {
			params.put(name, value);
		}
	}

	public static void addIntParameter (String name, String value, Map<String, String> params) {
		if (value != null && !value.isEmpty()) {
			params.put(name, value);
		}
	}

	public static void addParameter (String name, String value, Map<String, String> params) {
		if (value != null && !value.isEmpty()) {
			// remove any quotes within the string
			String strippedValue = value.replace("\"","");

			// check if value already contain quotes
			if (strippedValue.startsWith("\"", 0) && value.endsWith("\"")) {
				params.put(name, strippedValue);
			} else {
				params.put(name, "\"" + strippedValue + "\"");
			}
		}
	}

	protected String extractValue (Metadata metadata, String name) {
		String value = sdcCsarHelper.getMetadataPropertyValue(metadata, name);
		if (value != null) {
			return value;
		} else {
			return "";
		}	
	}

	protected String extractBooleanValue (Metadata metadata, String name) {
		String value = sdcCsarHelper.getMetadataPropertyValue(metadata, name);
		if (value != null && !value.isEmpty()) {
			return value.contains("true") ? "Y" : "N";
		} else {
			return "";
		}
	}

	public static String extractValue (ISdcCsarHelper sdcCsarHelper, Metadata metadata, String name) {
		String value = sdcCsarHelper.getMetadataPropertyValue(metadata, name);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractValue (NodeTemplate nodeTemplate, String name) {
		String value = sdcCsarHelper.getNodeTemplatePropertyLeafValue(nodeTemplate, name);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractGetInputValue (Group group, NodeTemplate nodeTemplate, String name) {

		String value = sdcCsarHelper.getNodeTemplatePropertyLeafValue(nodeTemplate, extractGetInputName (group, name));
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractGetInputName (Group group, String name) {
		
		String getInputName = name;
		String groupProperty = sdcCsarHelper.getGroupPropertyLeafValue(group, name);
		if (groupProperty != null) {
		int getInputIndex = groupProperty.indexOf("{get_input=");
			if (getInputIndex > -1) {
				getInputName = groupProperty.substring(getInputIndex+11, groupProperty.length()-1);
			}
		}
		
		return getInputName;

	}
	
	protected String extractGetInputValue (Policy policy, NodeTemplate nodeTemplate, String name) {

		String value = sdcCsarHelper.getNodeTemplatePropertyLeafValue(nodeTemplate, extractGetInputName (policy, name));
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractGetInputName (Policy policy, String name) {
		
		String getInputName = name;
		//String groupProperty = sdcCsarHelper.getPolicyPropertyLeafValue(policy, name);
		Map<String, Object> propMap = policy.getPolicyProperties();
		String groupProperty = nullCheck(propMap.get(name));
		if (!groupProperty.isEmpty()) {
		int getInputIndex = groupProperty.indexOf("{get_input=");
			if (getInputIndex > -1) {
				getInputName = groupProperty.substring(getInputIndex+11, groupProperty.length()-1);
			}
		}
		
		return getInputName;

	}

	protected String extractValue (Policy policy, String name) {
		
		Map<String, Object> propMap = policy.getPolicyProperties();
		if (propMap == null) {
			return "";
		} else {
			return nullCheck(propMap.get(name));
		}
	}

	protected static String extractValueStatic (Policy policy, String name) {
		
		Map<String, Object> propMap = policy.getPolicyProperties();
		if (propMap == null) {
			return "";
		} else {
			return nullCheckStatic(propMap.get(name));
		}
	}

	public static String extractValue (ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate, String name) {
		String value = sdcCsarHelper.getNodeTemplatePropertyLeafValue(nodeTemplate, name);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractValue (CapabilityAssignment capability, String name) {
		String value = sdcCsarHelper.getCapabilityPropertyLeafValue(capability, name);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractBooleanValue (NodeTemplate nodeTemplate, String name) {
		String value = sdcCsarHelper.getNodeTemplatePropertyLeafValue(nodeTemplate, name);
		if (value != null && !value.isEmpty()) {
			return value.contains("true") ? "Y" : "N";
		} else {
			return "";
		}
	}

	public static String extractBooleanValue (ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate, String name) {
		String value = sdcCsarHelper.getNodeTemplatePropertyLeafValue(nodeTemplate, name);
		if (value != null && !value.isEmpty()) {
			return value.contains("true") ? "Y" : "N";
		} else {
			return "";
		}
	}

	protected Object extractObjectValue (NodeTemplate nodeTemplate, String name) {
		Object value = sdcCsarHelper.getNodeTemplatePropertyAsObject(nodeTemplate, name);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractValue (Group group, String name) {
		String value = sdcCsarHelper.getGroupPropertyLeafValue(group, name);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractBooleanValue (Group group, String name) {
		String value = sdcCsarHelper.getGroupPropertyLeafValue(group, name);
		if (value != null && !value.isEmpty()) {
			return value.contains("true") ? "Y" : "N";
		} else {
			return "";
		}
	}

	protected String extractInputDefaultValue (String name) {
		String value = sdcCsarHelper.getServiceInputLeafValueOfDefault(name);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String extractBooleanInputDefaultValue (String name) {
		String value = sdcCsarHelper.getServiceInputLeafValueOfDefault(name);
		if (value != null && !value.isEmpty()) {
			return value.contains("true") ? "Y" : "N";
		} else {
			return "";
		}
	}

	public static String extractInputDefaultValue (ISdcCsarHelper sdcCsarHelper, String name) {
		String value = sdcCsarHelper.getServiceInputLeafValueOfDefault(name);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	public static String extractBooleanInputDefaultValue (ISdcCsarHelper sdcCsarHelper, String name) {
		String value = sdcCsarHelper.getServiceInputLeafValueOfDefault(name);
		if (value != null && !value.isEmpty()) {
			return value.contains("true") ? "Y" : "N";
		} else {
			return "";
		}
	}

	public static String extractSubstitutionMappingTypeName (ISdcCsarHelper sdcCsarHelper) {
		String value = sdcCsarHelper.getServiceSubstitutionMappingsTypeName();
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	protected String getUUID() {
		return ("\"" + UUID + "\"");
	}
	public String getInvariantUUID() {
		return ("\"" + invariantUUID + "\"");
	}
	public String getCustomizationUUID() {
		return ("\"" + customizationUUID + "\"");
	}
	public String getCustomizationUUIDNoQuotes() {
		return (customizationUUID);
	}
	public void setCustomizationUUID(String customizationUUID) {
		this.customizationUUID = customizationUUID;
	}
	public String getName() {
		return name;
	}
	
	public String buildSql(String tableName, String model_yaml) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT into " + tableName + " (customization_uuid, model_yaml, ");

		int paramCount = 0;
		for (String paramKey :  params.keySet()) {
			paramCount++;
		    sb.append(paramKey);
		    if (paramCount < params.size()) sb.append(", ");
		}

		sb.append(") values (" + getCustomizationUUID() + ", \"" + model_yaml + "\", ");

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
	
	public String buildSql(String tableName, String keyName, String keyValue, String model_yaml, Map<String, String> params) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT into " + tableName + " (" + keyName + ", ");
		
		int paramCount = 0;
		for (String paramKey :  params.keySet()) {
			paramCount++;
		    sb.append(paramKey);
		    if (paramCount < params.size()) sb.append(", ");
		}
		
		sb.append(") values (" + keyValue + ", ");

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
	
	public static String getSql(String tableName, String keyName, String keyValue, String model_yaml, Map<String, String> params) {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT into " + tableName + " (" + keyName + ", ");

		int paramCount = 0;
		for (String paramKey :  params.keySet()) {
			paramCount++;
		    sb.append(paramKey);
		    if (paramCount < params.size()) sb.append(", ");
		}

		sb.append(") values (" + keyValue + ", ");

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
	
	 protected void insertToscaData(String toscaDataString, ArrayList<String> arguments) throws IOException
     {
            LOG.debug("insertToscaData: " + toscaDataString);

             try {

 				jdbcDataSource.writeData(toscaDataString, arguments, null);

			} catch (SQLException e) {
				LOG.error("Could not insert Tosca data into the database");
				throw new IOException (e);
			}

     }
	 
	 protected static void insertToscaData(DBResourceManager jdbcDataSource, String toscaDataString, ArrayList<String> arguments) throws IOException
     {
            LOG.debug("insertToscaData: " + toscaDataString);

             try {

 				jdbcDataSource.writeData(toscaDataString, arguments, null);

			} catch (SQLException e) {
				LOG.error("Could not insert Tosca data into the database");
				throw new IOException (e);
			}

     }
	 
	protected void cleanUpExistingToscaData(String tableName, String keyName, String keyValue) throws IOException
     {

             try {
            	int rowCount = 0;
            	CachedRowSet data = jdbcDataSource.getData("SELECT * from " + tableName + " where " + keyName + " = " + keyValue + ";", null, "");
            	if (data != null) {
            		while(data.next()) {
     				rowCount ++;
            		}
            		if (rowCount != 0) {
                    	LOG.debug("cleanUpExistingToscaData from: " + tableName + " for " + keyValue);
               			jdbcDataSource.writeData("DELETE from " + tableName + " where " + keyName + " = " + keyValue + ";", null, null);
            		}
		}

			} catch (SQLException e) {
				LOG.error("Could not clean up existing " + tableName  + " for " + keyValue, e);
			}

     }
	
	protected void cleanUpExistingToscaData(String tableName, String key1Name, String key1Value, String key2Name, String key2Value) throws IOException
    {

            try {
           	int rowCount = 0;
           	CachedRowSet data = jdbcDataSource.getData("SELECT * from " + tableName + " where " + key1Name + " = " + key1Value + " AND " + key2Name + " = " + key2Value + ";", null, "");
           	if (data != null) {
           		while(data.next()) {
    				rowCount ++;
           		}
           		if (rowCount != 0) {
                   	LOG.debug("cleanUpExistingToscaData from : " + tableName + " for " + key1Value + " and " + key2Value);
              			jdbcDataSource.writeData("DELETE from " + tableName + " where " + key1Name + " = " + key1Value + " AND " + key2Name + " = " + key2Value + ";", null, null);
           		}
		}

			} catch (SQLException e) {
				LOG.error("Could not clean up existing " + tableName  + " for " + key1Value  + " and " + key2Value, e);
			}

    }
	
	protected boolean cleanupExistingToscaData(String tableName, Map<String, String> keyParams) throws IOException
    {
			return SdncBaseModel.cleanupExistingToscaData(this.jdbcDataSource, tableName, keyParams);
    }
	
	public static boolean cleanupExistingToscaData(DBResourceManager jdbcDataSource, String tableName, Map<String, String> keyParams) throws IOException
    {
		boolean dataExists = false;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * from " + tableName + " where ");
		
		int paramCount = 0;
		for (String paramKey :  keyParams.keySet()) {
			paramCount++;
			String paramValue = keyParams.get(paramKey);
		    sb.append(paramKey);
		    sb.append(" = ");
		    sb.append(paramValue);
		    if (paramCount < keyParams.size()) sb.append(" AND ");
		}

		sb.append(";");
	
        try {
       	int rowCount = 0;
       	CachedRowSet data = jdbcDataSource.getData(sb.toString(), null, "");
       	while(data.next()) {
				rowCount ++;
				data.deleteRow();
       	}
       	if (rowCount != 0) {
               LOG.debug("cleanupExistingToscaData in " + tableName + ": Data FOUND");
               String deleteStmt = sb.replace(sb.indexOf("SELECT *"), sb.indexOf("SELECT")+8, "DELETE").toString();
       		   jdbcDataSource.writeData(deleteStmt, null, null);
               dataExists = true;
       	}

		} catch (SQLException e) {
			LOG.error("Could not get data in " + tableName, e);
		}

        return dataExists;
    }
	
	protected boolean checkForExistingToscaData(String tableName, Map<String, String> keyParams) throws IOException
    {
			boolean dataExists = false;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * from " + tableName + " where ");
			
			int paramCount = 0;
			for (String paramKey :  keyParams.keySet()) {
				paramCount++;
				String paramValue = keyParams.get(paramKey);
			    sb.append(paramKey);
			    sb.append(" = ");
			    sb.append(paramValue);
			    if (paramCount < keyParams.size()) sb.append(" AND ");
			}

			sb.append(";");
		
            try {
           	int rowCount = 0;
           	CachedRowSet data = jdbcDataSource.getData(sb.toString(), null, "");
           	while(data.next()) {
    				rowCount ++;
           	}
           	if (rowCount != 0) {
                   LOG.info("checkForExistingToscaData in " + tableName + ": Data FOUND");
                   dataExists = true;
           	}

			} catch (SQLException e) {
				LOG.error("Could not get data in " + tableName, e);
			}

            return dataExists;
    }
	
	protected CachedRowSet getToscaData(String tableName, Map<String, String> keyParams) throws IOException
    {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * from " + tableName + " where ");
			
			int paramCount = 0;
			for (String paramKey :  keyParams.keySet()) {
				paramCount++;
				String paramValue = keyParams.get(paramKey);
			    sb.append(paramKey);
			    sb.append(" = ");
			    sb.append(paramValue);
			    if (paramCount < keyParams.size()) sb.append(" AND ");
			}

			sb.append(";");
		
			CachedRowSet data = null;
            try {
	           	int rowCount = 0;
	           	data = jdbcDataSource.getData(sb.toString(), null, "");
	           	while(data.next()) {
	    			rowCount ++;
	           	}
	           	if (rowCount == 0) {
	                LOG.info("getToscaData in " + tableName + ": Data NOT found");
	           	}

			} catch (SQLException e) {
				LOG.error("Could not get data in " + tableName, e);
			}

            return data;
    }
	protected void addParamsToMap (Map<String, String> fromMap, Map<String, String> toMap) {
		for (String key : fromMap.keySet()) {
		    if (!toMap.containsKey(key)) {
		    	toMap.put(key, fromMap.get(key));
		    }
		}
	}
	
	protected String nullCheck (Object extractedObject) {
		String stringValue = "";
		if (extractedObject != null) {
			return extractedObject.toString();
		}
		return stringValue;
	}

	protected static String nullCheckStatic (Object extractedObject) {
		String stringValue = "";
		if (extractedObject != null) {
			return extractedObject.toString();
		}
		return stringValue;
	}

}
