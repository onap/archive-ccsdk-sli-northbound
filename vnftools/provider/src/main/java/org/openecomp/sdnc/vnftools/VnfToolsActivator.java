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

package org.openecomp.sdnc.vnftools;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.openecomp.sdnc.sli.ConfigurationException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VnfToolsActivator implements BundleActivator {

	private static final String VNFTOOLS_PROP_VAR = "/vnftools.properties";
	private static final String SDNC_CONFIG_DIR = "SDNC_CONFIG_DIR";

	@SuppressWarnings("rawtypes")
	private List<ServiceRegistration> registrations = new LinkedList<ServiceRegistration>();

	private static final Logger LOG = LoggerFactory.getLogger(VnfToolsActivator.class);

	@Override
	public void start(BundleContext ctx) throws Exception {
		// Read properties
		Properties props = new Properties();

		String propDir = System.getenv(SDNC_CONFIG_DIR);
		if (propDir == null) {
			propDir = "/opt/sdnc/data/properties";
		}

		String propPath = propDir + VNFTOOLS_PROP_VAR;

		File propFile = new File(propPath);

		if (!propFile.exists()) {
			props = null;
		} else {

			try {
				props.load(new FileInputStream(propFile));
			} catch (Exception e) {
				throw new ConfigurationException("Could not load properties file " + propPath, e);
			}
		}
		VnfTools plugin = new VnfTools(props);

		LOG.info("Registering service " + plugin.getClass().getName());
		registrations.add(ctx.registerService(plugin.getClass().getName(), plugin, null));
	}

	@Override
	public void stop(BundleContext ctx) throws Exception {

		for (@SuppressWarnings("rawtypes")
		ServiceRegistration registration : registrations) {
			registration.unregister();
			registration = null;
		}
	}

}
