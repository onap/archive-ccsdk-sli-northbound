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

import java.util.HashMap;
import java.util.Map;

import org.openecomp.sdc.tosca.parser.api.ISdcCsarHelper;
import org.openecomp.sdc.tosca.parser.impl.SdcPropertyNames;
import org.openecomp.sdc.toscaparser.api.Group;
import org.openecomp.sdc.toscaparser.api.NodeTemplate;
import org.openecomp.sdc.toscaparser.api.elements.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncBaseModel {

	private static final Logger LOG = LoggerFactory
			.getLogger(SdncBaseModel.class);

	protected String customizationUUID = null;
	protected String invariantUUID = null;
	protected String model_yaml = null;
	protected String version = null;

	protected Map<String, String> params = null;
	protected ISdcCsarHelper sdcCsarHelper = null;

	public SdncBaseModel() {

	}

	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, Metadata metadata) {

		params = new HashMap<>();
		this.sdcCsarHelper = sdcCsarHelper;

		// extract service metadata
		invariantUUID = extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID);
		addParameter("invariant_uuid",invariantUUID);
		addParameter("version",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_VERSION));
		addParameter("name",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_NAME));
		addParameter("description",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_DESCRIPTION));
		addParameter("type",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_TYPE));
		addParameter("category",extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_CATEGORY));
	}

	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate) {

		params = new HashMap<>();
		this.sdcCsarHelper = sdcCsarHelper;

		// extract nodeTemplate metadata
		Metadata metadata = nodeTemplate.getMetaData();
		customizationUUID = extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_CUSTOMIZATIONUUID);
		addParameter("invariant_uuid", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID));
		addParameter("uuid", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_UUID));
		addParameter("version", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VERSION));
	}

	public SdncBaseModel(ISdcCsarHelper sdcCsarHelper, Group group) {

		params = new HashMap<>();
		this.sdcCsarHelper = sdcCsarHelper;

		// extract group metadata
		Metadata metadata = group.getMetadata();
		//customizationUUID = extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VFMODULECUSTOMIZATIONUUID); - returning null
		customizationUUID = extractValue (metadata, "vfModuleModelCustomizationUUID");
		addParameter("invariant_uuid", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELINVARIANTUUID));
		addParameter("uuid", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELUUID));
		addParameter("version", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELVERSION));
	}

	protected void addParameter (String name, String value) {
		if (value != null && !value.isEmpty()) {
			params.put(name, "\"" + value + "\"");
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
			params.put(name, "\"" + value + "\"");
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

	public static String extractValue (ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate, String name) {
		String value = sdcCsarHelper.getNodeTemplatePropertyLeafValue(nodeTemplate, name);
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

	public String getCustomizationUUID() {
		return ("\"" + customizationUUID + "\"");
	}
	public String getCustomizationUUIDNoQuotes() {
		return (customizationUUID);
	}
	public void setCustomizationUUID(String customizationUUID) {
		this.customizationUUID = customizationUUID;
	}

	public String getSql(String tableName, String model_yaml) {

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
		for (Map.Entry<String,String> entry : params.entrySet()) {
			paramCount++;
			String paramValue = entry.getValue();
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
		for (Map.Entry<String,String> entry : params.entrySet()) {
			paramCount++;
			String paramValue = entry.getValue();
		    sb.append(paramValue);
		    if (paramCount < params.size()) sb.append(", ");
		}

		sb.append(");");
		return sb.toString();
	}

}
