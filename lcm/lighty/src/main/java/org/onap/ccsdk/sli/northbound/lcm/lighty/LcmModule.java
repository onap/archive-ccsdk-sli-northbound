package org.onap.ccsdk.sli.northbound.lcm.lighty;


import io.lighty.core.controller.api.AbstractLightyModule;
import io.lighty.core.controller.api.LightyModule;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.ccsdk.sli.northbound.LcmProvider;
import org.onap.ccsdk.sli.northbound.LcmSliClient;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;

public class LcmModule extends AbstractLightyModule implements LightyModule {

    private final SvcLogicService svcLogicService;
    private final DataBroker dataBroker;
    private final NotificationPublishService publishService;
    private final RpcProviderRegistry rpcRegistry;

    private LcmSliClient lcmSliClient;
    private LcmProvider lcmProvider;

    public LcmModule(final SvcLogicService svcLogicService,
                     final DataBroker dataBroker,
                     final NotificationPublishService publishService,
                     final RpcProviderRegistry rpcRegistry) {
        this.svcLogicService = svcLogicService;
        this.dataBroker = dataBroker;
        this.publishService = publishService;
        this.rpcRegistry = rpcRegistry;
    }

    @Override
    protected boolean initProcedure() {
        this.lcmSliClient = new LcmSliClient(svcLogicService);
        this.lcmProvider = new LcmProvider(dataBroker, publishService, rpcRegistry, lcmSliClient);
        return true;
    }

    @Override
    protected boolean stopProcedure() {
        return true;
    }

    public LcmSliClient getLcmSliClient() {
        return this.lcmSliClient;
    }

    public LcmProvider getLcmProvider() {
        return lcmProvider;
    }
}
