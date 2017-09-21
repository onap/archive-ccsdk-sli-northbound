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

import com.att.nsa.mr.client.MRClientFactory;
import com.att.nsa.mr.client.MRConsumer;
import com.att.nsa.mr.client.response.MRConsumerResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SdncDmaapConsumer implements Runnable {

    private static final Logger LOG = LoggerFactory
        .getLogger(SdncDmaapConsumer.class);

    private String propertiesPath = "";
    private Properties properties = null;
    private MRConsumer consumer = null;
    private MRConsumerResponse consumerResponse = null;
    private boolean running = false;
    private boolean ready = false;
    private int fetchPause = 5000; // Default pause between fetch - 5 seconds
    private int timeout = 15000; // Default timeout - 15 seconds

    public boolean isReady() {
        return ready;
    }


    public boolean isRunning() {
        return running;
    }

    public SdncDmaapConsumer() {

    }

    public SdncDmaapConsumer(Properties properties, String propertiesPath) {
        init(properties, propertiesPath);
    }

    public String getProperty(String name) {
        return (properties.getProperty(name, ""));
    }

    public void init(Properties properties, String propertiesPath) {

        this.propertiesPath = propertiesPath;

        try (FileInputStream in = new FileInputStream(new File(propertiesPath))) {

            this.properties = (Properties) properties.clone();
            this.properties.load(in);

            String timeoutStr = properties.getProperty("timeout");

            if ((timeoutStr != null) && (timeoutStr.length() > 0)) {
                timeout = parseTimeOutValue(timeoutStr);
            }

            String fetchPauseStr = properties.getProperty("fetchPause");
            if ((fetchPauseStr != null) && (fetchPauseStr.length() > 0)) {
                fetchPause = parseFetchPause(fetchPauseStr);
            }

            this.consumer = MRClientFactory.createConsumer(propertiesPath);
            ready = true;
        } catch (Exception e) {
            LOG.error("Error initializing DMaaP consumer from file " + propertiesPath, e);
        }
    }

    private int parseTimeOutValue(String timeoutStr) {
        try {
            return Integer.parseInt(timeoutStr);
        } catch (NumberFormatException e) {
            LOG.error("Non-numeric value specified for timeout (" + timeoutStr + ")");
        }
        return timeout;
    }

    private int parseFetchPause(String fetchPauseStr) {
        try {
            return Integer.parseInt(fetchPauseStr);
        } catch (NumberFormatException e) {
            LOG.error("Non-numeric value specified for fetchPause (" + fetchPauseStr + ")");
        }
        return fetchPause;
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
                        LOG.info("Received message from DMaaP:\n" + msg);
                        processMsg(msg);
                    }

                    if (noData) {
                        pauseThread();
                    }
                } catch (Exception e) {
                    LOG.error("Caught exception reading from DMaaP", e);
                    running = false;
                }
            }
        }
    }

    private void pauseThread() throws InterruptedException {
        if (fetchPause > 0) {
            LOG.info(String.format("No data received from fetch.  Pausing %d ms before retry", fetchPause));
            Thread.sleep(fetchPause);
        } else {
            LOG.info("No data received from fetch.  No fetch pause specified - retrying immediately");
        }
    }

    abstract public void processMsg(String msg) throws InvalidMessageException;
}
