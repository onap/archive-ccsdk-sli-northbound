/*
 * ============LICENSE_START==========================================
 * Copyright (c) 2019 PANTHEON.tech s.r.o.
 * ===================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END============================================
 *
 */
package org.onap.ccsdk.sli.northbound.lighty;

import io.lighty.core.controller.api.AbstractLightyModule;
import org.onap.ccsdk.sli.core.lighty.common.CcsdkLightyUtils;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.ccsdk.sli.northbound.asdcapi.lighty.AsdcApiModule;
import org.onap.ccsdk.sli.northbound.dataChange.lighty.DataChangeModule;
import org.onap.ccsdk.sli.northbound.lcm.lighty.LcmModule;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of the {@link io.lighty.core.controller.api.LightyModule} that groups all other LightyModules
 * from the ccsdk-sli-northbound repository so they can be all treated as one component (for example started/stopped at once).
 * For more information about the lighty.io visit the website https://lighty.io.
 */
public class CcsdkNorhboundLightyModule extends AbstractLightyModule {

    private static final Logger LOG = LoggerFactory.getLogger(CcsdkNorhboundLightyModule.class);

    private final SvcLogicService svcLogicService;
    private final DataBroker dataBroker;
    private final NotificationPublishService publishService;
    private final RpcProviderRegistry rpcProviderRegistry;

    private AsdcApiModule asdcApiModule;
    private DataChangeModule dataChangeModule;
    private LcmModule lcmModule;

    public CcsdkNorhboundLightyModule(SvcLogicService svcLogicService, DataBroker dataBroker,
            NotificationPublishService publishService, RpcProviderRegistry rpcProviderRegistry) {

        this.svcLogicService = svcLogicService;
        this.dataBroker = dataBroker;
        this.publishService = publishService;
        this.rpcProviderRegistry = rpcProviderRegistry;
    }

    protected boolean initProcedure() {
        LOG.debug("Initializing CCSDK Northbound Lighty module...");

        this.asdcApiModule = new AsdcApiModule(svcLogicService, dataBroker, publishService, rpcProviderRegistry);
        if (!CcsdkLightyUtils.startLightyModule(asdcApiModule)) {
            LOG.error("Unable to start AsdcApiModule in CCSDK Northbound Lighty module!");
            return false;
        }

        this.dataChangeModule = new DataChangeModule(svcLogicService, dataBroker, publishService, rpcProviderRegistry);
        if (!CcsdkLightyUtils.startLightyModule(dataChangeModule)) {
            LOG.error("Unable to start DataChangeModule in CCSDK Northbound Lighty module!");
            return false;
        }

        this.lcmModule = new LcmModule(svcLogicService, dataBroker, publishService, rpcProviderRegistry);
        if (!CcsdkLightyUtils.startLightyModule(lcmModule)) {
            LOG.error("Unable to start LcmModule in CCSDK Northbound Lighty module!");
            return false;
        }

        LOG.debug("CCSDK Northbound Lighty module was initialized successfully");
        return true;
    }

    protected boolean stopProcedure() {
        LOG.debug("Stopping CCSDK Northbound Lighty module...");

        boolean stopSuccessful = true;

        if (!CcsdkLightyUtils.stopLightyModule(lcmModule)) {
            stopSuccessful = false;
        }

        if (!CcsdkLightyUtils.stopLightyModule(dataChangeModule)) {
            stopSuccessful = false;
        }

        if (!CcsdkLightyUtils.stopLightyModule(asdcApiModule)) {
            stopSuccessful = false;
        }

        if (stopSuccessful) {
            LOG.debug("CCSDK Northbound Lighty module was stopped successfully");
        } else {
            LOG.error("CCSDK Northbound Lighty module was not stopped successfully!");
        }
        return stopSuccessful;
    }

    public AsdcApiModule getAsdcApiModule() {
        return asdcApiModule;
    }

    public DataChangeModule getDataChangeModule() {
        return dataChangeModule;
    }

    public LcmModule getLcmModule() {
        return lcmModule;
    }
}
