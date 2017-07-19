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

package org.openecomp.sdnc.dmaapclient;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.att.nsa.mr.client.MRClientFactory;
import com.att.nsa.mr.client.MRConsumer;
import com.att.nsa.mr.client.response.MRConsumerResponse;

public abstract class SdncDmaapConsumer implements Runnable {

	private static final Logger LOG = LoggerFactory
			.getLogger(SdncDmaapConsumer.class);

	private String propertiesPath = "";
	private Properties properties = null;
	MRConsumer consumer = null;
	MRConsumerResponse consumerResponse = null;
	boolean running = false;
	boolean ready = false;
	int fetchPause = 5000; // Default pause between fetchs - 5 seconds

	public boolean isReady() {
		return ready;
	}

	int timeout = 15000; // Default timeout - 15 seconds

	public boolean isRunning() {
		return running;
	}

	public SdncDmaapConsumer() {

	}

	public SdncDmaapConsumer(Properties properties, String propertiesPath) {
		init(properties, propertiesPath);
	}

	public String getProperty(String name) {
		return(properties.getProperty(name, ""));
	}

	public void init(Properties properties, String propertiesPath) {

		this.propertiesPath = propertiesPath;

		try {

			this.properties = (Properties) properties.clone();

			this.properties.load(new FileInputStream(new File(propertiesPath)));

			String timeoutStr = properties.getProperty("timeout");

			if ((timeoutStr != null) && (timeoutStr.length() > 0)) {
				try {
					timeout = Integer.parseInt(timeoutStr);
				} catch (NumberFormatException e) {
					LOG.error("Non-numeric value specified for timeout ("+timeoutStr+")");
				}
			}

			String fetchPauseStr = properties.getProperty("fetchPause");
			if ((fetchPauseStr != null) && (fetchPauseStr.length() > 0)) {
				try {
					fetchPause = Integer.parseInt(fetchPauseStr);
				} catch (NumberFormatException e) {
					LOG.error("Non-numeric valud specified for fetchPause ("+fetchPauseStr+")");
				}
			}

			this.consumer = MRClientFactory.createConsumer(propertiesPath);
			ready = true;
		} catch (Exception e) {
			LOG.error("Error initializing DMaaP consumer from file "+propertiesPath, e);
		}
	}


	@Override
	public void run() {
		if (ready) {

			running = true;

			while (running) {

				try {
					boolean noData = true;
					consumerResponse = consumer.fetchWithReturnConsumerResponse(timeout, -1);
					for (String msg : consumerResponse.getActualMessages()) {
						noData = false;
						LOG.info("Received message from DMaaP:\n"+msg);
						processMsg(msg);
					}

					if (noData) {
						if (fetchPause > 0) {

							LOG.info("No data received from fetch.  Pausing "+fetchPause+" ms before retry");
							Thread.sleep(fetchPause);
						} else {

							LOG.info("No data received from fetch.  No fetch pause specified - retrying immediately");
						}
					}
				} catch (Exception e) {
					LOG.error("Caught exception reading from DMaaP", e);
					running = false;
				}


			}
		}

	}

	abstract public void processMsg(String msg) throws InvalidMessageException;
}
