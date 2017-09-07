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

package org.onap.ccsdk.sli.northbound;

import java.util.Properties;

import org.onap.ccsdk.sli.core.sli.SvcLogicException;
import org.onap.ccsdk.sli.core.sli.provider.MdsalHelper;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.datachange.rev150519.DataChangeNotificationOutputBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataChangeClient {

	private static final Logger LOG = LoggerFactory.getLogger(DataChangeClient.class);

	private SvcLogicService svcLogicService = null;

	public DataChangeClient(final SvcLogicService svcLogicService) {
		this.svcLogicService = svcLogicService;
	}

	public boolean hasGraph(String module, String rpc, String version, String mode) throws SvcLogicException
	{
		return(svcLogicService.hasGraph(module, rpc, version, mode));
	}

	public Properties execute(String module, String rpc, String version, String mode, DataChangeNotificationOutputBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();

		return execute(module,rpc,version, mode,serviceData,parms);
	}

	public Properties execute(String module, String rpc, String version, String mode, DataChangeNotificationOutputBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = MdsalHelper.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");

			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);

				LOG.debug(parmName+" = "+parmValue);

			}
		}

		Properties respProps = svcLogicService.execute(module, rpc, version, mode, parms);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters returned by SLI");

			for (Object key : respProps.keySet()) {
				String parmName = (String) key;
				String parmValue = respProps.getProperty(parmName);

				LOG.debug(parmName+" = "+parmValue);

			}
		}
		if ("failure".equalsIgnoreCase(respProps.getProperty("SvcLogic.status"))) {
			return (respProps);
		}

		MdsalHelper.toBuilder(respProps, serviceData);

		return (respProps);
	}

}
