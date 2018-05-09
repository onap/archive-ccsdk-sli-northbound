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

import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
import org.onap.sdc.tosca.parser.impl.SdcPropertyNames;
import org.onap.sdc.toscaparser.api.NodeTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncVFCModel extends SdncBaseModel {

	private static final Logger LOG = LoggerFactory
			.getLogger(SdncVFCModel.class);

	private String vmType = null;
	private String vmCount = null;

	public SdncVFCModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate) {

		super(sdcCsarHelper, nodeTemplate);

		// extract properties
		vmType = extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VMTYPE);
		if ((vmType == null) || (vmType.length() == 0)) {
			vmType = extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VMTYPETAG);
		}
		//vmCount = extractValue (sdcCsarHelper, nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VMCOUNT); - need path to vm_count
		vmCount = "1";
		addParameter("vm_type", vmType);
		addParameter("vm_type_tag", extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VMTYPETAG));
		addParameter("ecomp_generated_naming", extractBooleanValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VFCNAMING_ECOMPGENERATEDNAMING));
		addParameter("naming_policy", extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VFCNAMING_NAMINGPOLICY));
		addParameter("nfc_naming_code", extractValue (nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NFCCODE));
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
