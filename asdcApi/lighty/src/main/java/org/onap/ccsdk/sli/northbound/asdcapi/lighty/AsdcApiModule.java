package org.onap.ccsdk.sli.northbound.asdcapi.lighty;


import io.lighty.core.controller.api.AbstractLightyModule;
import io.lighty.core.controller.api.LightyModule;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.ccsdk.sli.northbound.asdcapi.AsdcApiProvider;
import org.onap.ccsdk.sli.northbound.asdcapi.AsdcApiSliClient;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;

public class AsdcApiModule extends AbstractLightyModule implements LightyModule {

    private final AsdcApiSliClient asdcApiClient;
    private final AsdcApiProvider asdcApiProvider;

    public AsdcApiModule(final SvcLogicService svcLogicService,
                         final DataBroker dataBroker,
                         final NotificationPublishService publishService,
                         final RpcProviderRegistry rpcRegistry) {
        this.asdcApiClient = new AsdcApiSliClient(svcLogicService);
        this.asdcApiProvider = new AsdcApiProvider(dataBroker, publishService, rpcRegistry, asdcApiClient);
    }

    public AsdcApiSliClient getAsdcApiSliClient() {
        return this.asdcApiClient;
    }

    public AsdcApiProvider getAsdcApiProvider() {
        return asdcApiProvider;
    }

    @Override
    protected boolean initProcedure() {
        return true;
    }

    @Override
    protected boolean stopProcedure() {
        return true;
    }
}