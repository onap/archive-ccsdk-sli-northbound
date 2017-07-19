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

import java.util.Properties;

import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.data.PreloadDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.data.ServiceDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.preload.data.VfModulePreloadDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.service.data.VfModuleServiceDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.preload.data.VnfInstancePreloadDataBuilder;
//1610
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.service.data.VnfInstanceServiceDataBuilder;
import org.openecomp.sdnc.sli.SvcLogicException;
import org.openecomp.sdnc.sli.provider.SvcLogicService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VNFSDNSvcLogicServiceClient {

	private static final Logger LOG = LoggerFactory
			.getLogger(VNFSDNSvcLogicServiceClient.class);

	private SvcLogicService svcLogic = null;

	public VNFSDNSvcLogicServiceClient()
	{
		BundleContext bctx = FrameworkUtil.getBundle(SvcLogicService.class).getBundleContext();

    	// Get SvcLogicService reference
		ServiceReference sref = bctx.getServiceReference(SvcLogicService.NAME);
		if (sref  != null)
		{
			svcLogic =  (SvcLogicService) bctx.getService(sref);

		}
		else
		{
			LOG.warn("Cannot find service reference for "+SvcLogicService.NAME);

		}
	}

	public boolean hasGraph(String module, String rpc, String version, String mode) throws SvcLogicException
	{
		return(svcLogic.hasGraph(module, rpc, version, mode));
	}

	public Properties execute(String module, String rpc, String version, String mode, ServiceDataBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();

		return execute(module,rpc,version, mode,serviceData,parms);
	}

	public Properties execute(String module, String rpc, String version, String mode, PreloadDataBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();

		return execute(module,rpc,version, mode,serviceData,parms);
	}


	public Properties execute(String module, String rpc, String version, String mode, ServiceDataBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = VnfSdnUtil.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");

			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);

				LOG.debug(parmName+" = "+parmValue);

			}
		}

		Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);

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

		VnfSdnUtil.toBuilder(respProps, serviceData);

		return (respProps);
	}


	public Properties execute(String module, String rpc, String version, String mode, PreloadDataBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = VnfSdnUtil.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");

			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);

				LOG.debug(parmName+" = "+parmValue);

			}
		}

		Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);

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

		VnfSdnUtil.toBuilder(respProps, serviceData);

		return (respProps);
	}


	//1610 vnf-instance
	public Properties execute(String module, String rpc, String version, String mode, VnfInstanceServiceDataBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();

		return execute(module,rpc,version, mode,serviceData,parms);
	}

	//1610 vnf-instance
	public Properties execute(String module, String rpc, String version, String mode, VnfInstanceServiceDataBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = VnfSdnUtil.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");

			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);

				LOG.debug(parmName+" = "+parmValue);

			}
		}

		Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);

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

		VnfSdnUtil.toBuilder(respProps, serviceData);

		return (respProps);
	}

	//1610 vf-module
	public Properties execute(String module, String rpc, String version, String mode, VfModuleServiceDataBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();

		return execute(module,rpc,version, mode,serviceData,parms);
	}

	//1610 vf-module
	public Properties execute(String module, String rpc, String version, String mode, VfModuleServiceDataBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = VnfSdnUtil.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");

			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);

				LOG.debug(parmName+" = "+parmValue);

			}
		}

		Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);

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

		VnfSdnUtil.toBuilder(respProps, serviceData);

		return (respProps);
	}

    //1610 vnf-instance-preload
	public Properties execute(String module, String rpc, String version, String mode, VnfInstancePreloadDataBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();

		return execute(module,rpc,version, mode,serviceData,parms);
	}


    //1610 vnf-instance-preload
	public Properties execute(String module, String rpc, String version, String mode, VnfInstancePreloadDataBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = VnfSdnUtil.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");

			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);

				LOG.debug(parmName+" = "+parmValue);

			}
		}

		Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);

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

		VnfSdnUtil.toBuilder(respProps, serviceData);

		return (respProps);
	}

    //1610 vf-module-preload
	public Properties execute(String module, String rpc, String version, String mode, VfModulePreloadDataBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();

		return execute(module,rpc,version, mode,serviceData,parms);
	}


    //1610 vf-module-preload
	public Properties execute(String module, String rpc, String version, String mode, VfModulePreloadDataBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = VnfSdnUtil.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");

			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);

				LOG.debug(parmName+" = "+parmValue);

			}
		}

		Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);

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

		VnfSdnUtil.toBuilder(respProps, serviceData);

		return (respProps);
	}


}
