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

package org.onap.ccsdk.sli.northbound;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.onap.ccsdk.sli.core.sli.provider.MdsalHelper;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.datachange.rev150519.DataChangeNotificationInput;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.datachange.rev150519.DataChangeNotificationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.datachange.rev150519.DataChangeNotificationOutput;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.datachange.rev150519.DataChangeNotificationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.datachange.rev150519.DataChangeService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Futures;

/**
 * Defines a base implementation for your provider. This class extends from a helper class
 * which provides storage for the most commonly used components of the MD-SAL. Additionally the
 * base class provides some basic logging and initialization / clean up methods.
 *
 */
public class DataChangeProvider implements AutoCloseable, DataChangeService{

    private final Logger log = LoggerFactory.getLogger( DataChangeProvider.class );
    private final String appName = "DataChange";
    private final ExecutorService executor;

    protected DataBroker dataBroker;
    protected NotificationProviderService notificationService;
    protected RpcProviderRegistry rpcRegistry;
    protected BindingAwareBroker.RpcRegistration<DataChangeService> rpcRegistration;


    public DataChangeProvider(DataBroker dataBroker2,
			NotificationProviderService notificationProviderService,
			RpcProviderRegistry rpcProviderRegistry) {
        this.log.info( "Creating provider for " + appName );
        executor = Executors.newFixedThreadPool(1);
		dataBroker = dataBroker2;
		notificationService = notificationProviderService;
		rpcRegistry = rpcProviderRegistry;
		initialize();
    }

    public void initialize(){
        log.info( "Initializing provider for " + appName );
        rpcRegistration = rpcRegistry.addRpcImplementation(DataChangeService.class, this);
        log.info( "Initialization complete for " + appName );
    }

    protected void initializeChild() {
        //Override if you have custom initialization intelligence
    }

    @Override
    public void close() throws Exception {
        log.info( "Closing provider for " + appName );
	    executor.shutdown();
	    rpcRegistration.close();
        log.info( "Successfully closed provider for " + appName );
    }

    public void setDataBroker(DataBroker dataBroker) {
        this.dataBroker = dataBroker;
        if( log.isDebugEnabled() ){
            log.debug( "DataBroker set to " + (dataBroker==null?"null":"non-null") + "." );
        }
    }

    public void setNotificationService(
            NotificationProviderService notificationService) {
        this.notificationService = notificationService;
        if( log.isDebugEnabled() ){
            log.debug( "Notification Service set to " + (notificationService==null?"null":"non-null") + "." );
        }
    }

    public void setRpcRegistry(RpcProviderRegistry rpcRegistry) {
        this.rpcRegistry = rpcRegistry;
        if( log.isDebugEnabled() ){
            log.debug( "RpcRegistry set to " + (rpcRegistry==null?"null":"non-null") + "." );
        }
    }

	@Override
	public Future<RpcResult<DataChangeNotificationOutput>> dataChangeNotification(
			DataChangeNotificationInput input) {
		final String SVC_OPERATION = "data-change-notification";

		Properties parms = new Properties();
		DataChangeNotificationOutputBuilder serviceDataBuilder = new DataChangeNotificationOutputBuilder();

		log.info( SVC_OPERATION +" called." );

		if(input == null || input.getAaiEventId() == null) {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input");
			serviceDataBuilder.setDataChangeResponseCode("403");
			RpcResult<DataChangeNotificationOutput> rpcResult =
				RpcResultBuilder.<DataChangeNotificationOutput> status(true).withResult(serviceDataBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// add input to parms
		log.info("Adding INPUT data for "+SVC_OPERATION+" input: " + input);
		DataChangeNotificationInputBuilder inputBuilder = new DataChangeNotificationInputBuilder(input);
		MdsalHelper.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		DataChangeClient svcLogicClient = new DataChangeClient();
		Properties respProps = null;

		try
		{
			if (svcLogicClient.hasGraph("DataChange", SVC_OPERATION , null, "sync"))
			{
				try
				{
					respProps = svcLogicClient.execute("DataChange", SVC_OPERATION, null, "sync", serviceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
					serviceDataBuilder.setDataChangeResponseCode("500");
				}
			} else {
				log.error("No service logic active for DataChange: '" + SVC_OPERATION + "'");
				serviceDataBuilder.setDataChangeResponseCode("503");
			}
		}
		catch (Exception e)
		{
			log.error("Caught exception looking for service logic", e);
			serviceDataBuilder.setDataChangeResponseCode("500");
		}

		String errorCode = serviceDataBuilder.getDataChangeResponseCode();

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			log.error("Returned FAILED for "+SVC_OPERATION+" error code: '" + errorCode + "'");
		} else {
			log.info("Returned SUCCESS for "+SVC_OPERATION+" ");
		}

		RpcResult<DataChangeNotificationOutput> rpcResult =
				RpcResultBuilder.<DataChangeNotificationOutput> status(true).withResult(serviceDataBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}
}
