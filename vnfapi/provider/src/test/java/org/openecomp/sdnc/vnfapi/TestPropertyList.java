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

package org.openecomp.sdnc.vnfapi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;

import junit.framework.TestCase;

import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.data.ServiceDataBuilder;
import org.openecomp.sdnc.vnfapi.VnfSdnUtil;
import org.openecomp.sdnc.vnfapi.vnfapiProvider;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.data.PreloadDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPropertyList extends TestCase {


	private final Logger log = LoggerFactory.getLogger( vnfapiProvider.class );
	public void testPrintPropertyList() {
		InputStream propStr = getClass().getResourceAsStream("/proplist.properties");
		
		Properties props = new Properties();

		try
		{
			props.load(propStr);
		}  catch (Exception e)
		{
			fail("Could not load proplist.properties");
		}
		
		String aptFile = props.getProperty("proplist.aptFile");
		
		if ((aptFile == null) || (aptFile.length() == 0))
		{
			fail("proplist.aptFile unset");
		}
		
		PrintStream outStr = null;
		
		try {
			outStr = new PrintStream(new FileOutputStream(aptFile));
		} catch (FileNotFoundException e) {
			fail("Cannot open apt file "+aptFile);
		}
		
		Date now = new Date();
		
		outStr.print("      ---");
		outStr.print("\n      Service Logic Variables");
		outStr.print("\n      ---");
		outStr.print("\n      System generated");
		outStr.print("\n      ---");
		outStr.print("\n      "+now.toString());
		outStr.print("\n\nSupported service logic variables");
		outStr.print("\n\n   Config/operational tree data for VNF SDN service is passed to the service logic in the following variables");
		outStr.print("\n");
		VnfSdnUtil.printPropertyList(outStr, "", ServiceDataBuilder.class);
		VnfSdnUtil.printPropertyList(outStr, "", PreloadDataBuilder.class);
		outStr.print("\n");
		outStr.print("\n\n   Input parameters to the vnf-topology-operation RPC are passed to the service logic in the following variables");
		outStr.print("\n");
		VnfSdnUtil.printPropertyList(outStr, "", VnfTopologyOperationInputBuilder.class);
		outStr.print("\n");
		outStr.print("\n\n   Input parameters to the preload-vnf-topology-operation -operation RPC are passed to the service logic in the following variables");
		outStr.print("\n");
		VnfSdnUtil.printPropertyList(outStr, "", PreloadVnfTopologyOperationInputBuilder.class);
		outStr.print("\n");
		outStr.flush();
		outStr.close();
	}

}
