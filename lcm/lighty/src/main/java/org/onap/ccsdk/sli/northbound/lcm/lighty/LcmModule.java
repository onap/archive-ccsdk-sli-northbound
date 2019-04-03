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

    private final LcmSliClient lcmSliClient;
    private final LcmProvider lcmProvider;

    public LcmModule(final SvcLogicService svcLogicService,
                     final DataBroker dataBroker,
                     final NotificationPublishService publishService,
                     final RpcProviderRegistry rpcRegistry) {
        this.lcmSliClient = new LcmSliClient(svcLogicService);
        this.lcmProvider = new LcmProvider(dataBroker, publishService, rpcRegistry, lcmSliClient);
    }

    public LcmSliClient getLcmSliClient() {
        return this.lcmSliClient;
    }

    public LcmProvider getLcmProvider() {
        return lcmProvider;
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