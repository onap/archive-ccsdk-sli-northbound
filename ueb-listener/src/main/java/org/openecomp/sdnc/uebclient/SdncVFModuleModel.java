/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 ONAP Intellectual Property. All rights
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

package org.openecomp.sdnc.uebclient;

import org.openecomp.sdc.tosca.parser.api.ISdcCsarHelper;
import org.openecomp.sdc.tosca.parser.impl.SdcPropertyNames;
import org.openecomp.sdc.toscaparser.api.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncVFModuleModel extends SdncBaseModel {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SdncVFModuleModel.class);
	
	public SdncVFModuleModel(ISdcCsarHelper sdcCsarHelper, Group group) {

		super(sdcCsarHelper, group);
		
		// extract properties
		addParameter("vf_module_type", extractValue(group, SdcPropertyNames.PROPERTY_NAME_VFMODULETYPE));
		addIntParameter("availability_zone_count", extractValue(group, SdcPropertyNames.PROPERTY_NAME_AVAILABILITYZONECOUNT));
		addParameter("ecomp_generated_vm_assignments", extractBooleanValue(group, SdcPropertyNames.PROPERTY_NAME_ECOMPGENERATEDVMASSIGNMENTS));
	}

}
