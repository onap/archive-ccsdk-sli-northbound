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

package org.onap.ccsdk.sli.northbound.dmaapclient;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DmaapListener {
	
	private static final String DMAAP_LISTENER_PROPERTIES = "dmaap-listener.properties";
	private static final String SDNC_CONFIG_DIR = "SDNC_CONFIG_DIR";
	private static final Logger LOG = LoggerFactory
			.getLogger(DmaapListener.class);
	
	public static void main(String[] args) {
		
		Properties properties = new Properties();
		

		String propFileName = DMAAP_LISTENER_PROPERTIES;
		
		if (args.length > 0) {
			propFileName = args[0];
		}
		
		String propPath = null;
		String propDir = System.getenv(SDNC_CONFIG_DIR);
		
		List<SdncDmaapConsumer> consumers = new LinkedList();
		
		if (propDir == null) {
			
			propDir = "/opt/sdnc/data/properties";
		}
		
		if (!propFileName.startsWith("/")) {
			propPath = propDir + "/" + propFileName;
		}
		
		File propFile = new File(propPath);
		
		if (!propFile.canRead()) {
			LOG.error("Cannot read properties file "+propPath);
			System.exit(1);
		}
		
		try {
			properties.load(new FileInputStream(propFile));
		} catch (Exception e) {
			LOG.error("Caught exception loading properties from "+propPath, e);
			System.exit(1);
		}
		
		String subscriptionStr = properties.getProperty("subscriptions");
		
		boolean threadsRunning = false;
		
		LOG.debug("Dmaap subscriptions : "+subscriptionStr);
		
		if (subscriptionStr != null) {
			String[] subscriptions = subscriptionStr.split(";");
			
			for (int i = 0; i < subscriptions.length; i++) {
				String[] subscription = subscriptions[i].split(":");
				String consumerClassName = subscription[0];
				String propertyPath = subscription[1];

				LOG.debug("Handling subscription [" + consumerClassName + "," + propertyPath + "]");

				if (propertyPath == null) {
					LOG.error("Invalid subscription (" + subscriptions[i] + ") property file missing");
					continue;
				}

				if (!propertyPath.startsWith("/")) {
					propertyPath = propDir + "/" + propertyPath;
				}

				Class<?> consumerClass = null;

				try {
					consumerClass = Class.forName(consumerClassName);
				} catch (Exception e) {
					LOG.error("Could not find DMaap consumer class " + consumerClassName);
				}

				if (consumerClass != null) {

					SdncDmaapConsumer consumer = null;

					try {
						consumer = (SdncDmaapConsumer) consumerClass.newInstance();
					} catch (Exception e) {
						LOG.error("Could not create consumer from class " + consumerClassName, e);
					}

					if (consumer != null) {
						LOG.debug("Initializing consumer " + consumerClassName + "(" + propertyPath + ")");
						consumer.init(properties, propertyPath);

						if (consumer.isReady()) {
							Thread consumerThread = new Thread(consumer);
							consumerThread.start();
							consumers.add(consumer);
							threadsRunning = true;
							LOG.info("Started consumer thread (" + consumerClassName + " : " + propertyPath + ")");
						} else {
							LOG.debug("Consumer " + consumerClassName + " is not ready");
						}
					}

				}

			}
		}
		
		while (threadsRunning) {
			
			threadsRunning = false;
			for (SdncDmaapConsumer consumer : consumers) {
				if (consumer.isRunning()) {
					threadsRunning = true;
				}
			}
			
			if (!threadsRunning) {
				break;
			}
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				
			}
		}
		
		LOG.info("No listener threads running - exitting");

	}
}
