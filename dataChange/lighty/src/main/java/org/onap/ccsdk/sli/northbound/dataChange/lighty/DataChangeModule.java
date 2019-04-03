package org.onap.ccsdk.sli.northbound.dataChange.lighty;


import io.lighty.core.controller.api.AbstractLightyModule;
import io.lighty.core.controller.api.LightyModule;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.ccsdk.sli.northbound.DataChangeClient;
import org.onap.ccsdk.sli.northbound.DataChangeProvider;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;

public class DataChangeModule extends AbstractLightyModule implements LightyModule {

    private final DataChangeClient dataChangeClient;
    private final DataChangeProvider dataChangeProvider;

    public DataChangeModule(final SvcLogicService svcLogicService,
                            final DataBroker dataBroker,
                            final NotificationPublishService publishService,
                            final RpcProviderRegistry rpcRegistry) {
        this.dataChangeClient = new DataChangeClient(svcLogicService);
        this.dataChangeProvider = new DataChangeProvider(dataBroker, publishService, rpcRegistry, dataChangeClient);
    }

    public DataChangeClient getDataChangeClient() {
        return this.dataChangeClient;
    }

    public DataChangeProvider getDataChangeProvider() {
        return dataChangeProvider;
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