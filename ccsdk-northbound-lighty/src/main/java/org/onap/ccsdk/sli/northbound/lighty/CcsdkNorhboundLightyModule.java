package org.onap.ccsdk.sli.northbound.lighty;

import io.lighty.core.controller.api.AbstractLightyModule;
import io.lighty.core.controller.api.LightyModule;
import java.util.concurrent.ExecutionException;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.ccsdk.sli.northbound.asdcapi.lighty.AsdcApiModule;
import org.onap.ccsdk.sli.northbound.dataChange.lighty.DataChangeModule;
import org.onap.ccsdk.sli.northbound.lcm.lighty.LcmModule;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        LOG.debug("Initializing CCSDK Norhbound Lighty module...");

        this.asdcApiModule = new AsdcApiModule(svcLogicService, dataBroker, publishService, rpcProviderRegistry);
        if (!startLightyModule(asdcApiModule)) {
            LOG.error("Unable to start AsdcApiModule in CCSDK Northbound Lighty module!");
            return false;
        }

        this.dataChangeModule = new DataChangeModule(svcLogicService, dataBroker, publishService, rpcProviderRegistry);
        if (!startLightyModule(dataChangeModule)) {
            LOG.error("Unable to start DataChangeModule in CCSDK Northbound Lighty module!");
            return false;
        }

        this.lcmModule = new LcmModule(svcLogicService, dataBroker, publishService, rpcProviderRegistry);
        if (!startLightyModule(lcmModule)) {
            LOG.error("Unable to start LcmModule in CCSDK Northbound Lighty module!");
            return false;
        }

        LOG.debug("CCSDK Northbound Lighty module was initialized successfully");
        return true;
    }

    protected boolean stopProcedure() {
        LOG.debug("Stopping CCSDK Northbound Lighty module...");

        boolean stopSuccessfull = true;

        if (!stopLightyModule(lcmModule)) {
            stopSuccessfull = false;
        }

        if (!stopLightyModule(dataChangeModule)) {
            stopSuccessfull = false;
        }

        if (!stopLightyModule(asdcApiModule)) {
            stopSuccessfull = false;
        }

        if (stopSuccessfull) {
            LOG.debug("CCSDK Northbound Lighty module was stopped successfully");
        } else {
            LOG.error("CCSDK Northbound Lighty module was not stopped successfully!");
        }
        return stopSuccessfull;
    }

    private boolean startLightyModule(LightyModule lightyModule) {
        try {
            return lightyModule.start().get();
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Exception thrown while initializing {} in CCSDK Northbound Lighty module!", lightyModule.getClass(),
                    e);
            return false;
        }
    }

    private boolean stopLightyModule(LightyModule lightyModule) {
        try {
            if (!lightyModule.shutdown().get()) {
                LOG.error("{} was not stopped successfully in CCSDK Northbound Lighty module!", lightyModule.getClass());
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            LOG.error("Exception thrown while shutting down {} in CCSDK Northbound Lighty module!", lightyModule.getClass(),
                    e);
            return false;
        }
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
