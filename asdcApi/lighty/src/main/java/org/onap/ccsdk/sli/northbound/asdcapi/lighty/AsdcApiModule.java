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

    public AsdcApiSliClient getAsdcApiSliClient() {
        return this.asdcApiClient;
    }

    public AsdcApiProvider getAsdcApiProvider() {
        return asdcApiProvider;
    }
}
