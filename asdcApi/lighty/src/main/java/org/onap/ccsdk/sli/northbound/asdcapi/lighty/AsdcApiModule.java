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
package org.onap.ccsdk.sli.northbound.asdcapi.lighty;

import io.lighty.core.controller.api.AbstractLightyModule;
import io.lighty.core.controller.api.LightyModule;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.ccsdk.sli.northbound.asdcapi.AsdcApiProvider;
import org.onap.ccsdk.sli.northbound.asdcapi.AsdcApiSliClient;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;

/**
 * The implementation of the {@link io.lighty.core.controller.api.LightyModule} that manages and provides services from
 * the asdcApi-provicer artifact.
 */
public class AsdcApiModule extends AbstractLightyModule implements LightyModule {

    private final SvcLogicService svcLogicService;
    private final DataBroker dataBroker;
    private final NotificationPublishService publishService;
    private final RpcProviderRegistry rpcRegistry;
    private AsdcApiSliClient asdcApiClient;
    private AsdcApiProvider asdcApiProvider;

    public AsdcApiModule(SvcLogicService svcLogicService, DataBroker dataBroker,
            NotificationPublishService publishService, RpcProviderRegistry rpcRegistry) {
        this.svcLogicService = svcLogicService;
        this.dataBroker = dataBroker;
        this.publishService = publishService;
        this.rpcRegistry = rpcRegistry;
    }

    @Override
    protected boolean initProcedure() {
        this.asdcApiClient = new AsdcApiSliClient(svcLogicService);
        this.asdcApiProvider = new AsdcApiProvider(dataBroker, publishService, rpcRegistry, asdcApiClient);
        return true;
    }

    @Override
    protected boolean stopProcedure() {
        return true;
    }

}
