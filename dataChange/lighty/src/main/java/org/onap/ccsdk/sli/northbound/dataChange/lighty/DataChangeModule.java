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

    private final SvcLogicService svcLogicService;
    private final DataBroker dataBroker;
    private final NotificationPublishService publishService;
    private final RpcProviderRegistry rpcRegistry;

    private DataChangeClient dataChangeClient;
    private DataChangeProvider dataChangeProvider;

    public DataChangeModule(SvcLogicService svcLogicService, DataBroker dataBroker,
            NotificationPublishService publishService, RpcProviderRegistry rpcRegistry) {
        this.svcLogicService = svcLogicService;
        this.dataBroker = dataBroker;
        this.publishService = publishService;
        this.rpcRegistry = rpcRegistry;
    }

    @Override
    protected boolean initProcedure() {
        this.dataChangeClient = new DataChangeClient(svcLogicService);
        this.dataChangeProvider = new DataChangeProvider(dataBroker, publishService, rpcRegistry, dataChangeClient);
        return true;
    }

    @Override
    protected boolean stopProcedure() {
        return true;
    }

    public DataChangeClient getDataChangeClient() {
        return this.dataChangeClient;
    }

    public DataChangeProvider getDataChangeProvider() {
        return dataChangeProvider;
    }
}
