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
import org.openecomp.sdc.toscaparser.api.elements.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncVFModel extends SdncBaseModel {

	private static final Logger LOG = LoggerFactory
			.getLogger(SdncVFModel.class);

	public SdncVFModel(ISdcCsarHelper sdcCsarHelper, NodeTemplate nodeTemplate) {

		super(sdcCsarHelper, nodeTemplate);

		// extract metadata
		Metadata metadata = nodeTemplate.getMetaData();
		addParameter("name", extractValue(metadata, SdcPropertyNames.PROPERTY_NAME_NAME));
		addParameter("vendor", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_RESOURCEVENDOR));
		addParameter("vendor_version", extractValue (metadata, SdcPropertyNames.PROPERTY_NAME_RESOURCEVENDORRELEASE));

		// extract properties
		addParameter("ecomp_generated_naming", extractBooleanValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VNFECOMPNAMING_ECOMPGENERATEDNAMING));
		addParameter("naming_policy", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_VNFECOMPNAMING_NAMINGPOLICY));
		addParameter("nf_type", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NFTYPE));
		addParameter("nf_role", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NFROLE));
		addParameter("nf_code", extractValue( nodeTemplate, "nf_naming_code"));
		addParameter("nf_function", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_NFFUNCTION));
		addParameter("avail_zone_max_count", extractValue(nodeTemplate, SdcPropertyNames.PROPERTY_NAME_AVAILABILITYZONEMAXCOUNT));
	}

}
