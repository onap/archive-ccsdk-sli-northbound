/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 ONAP Intellectual Property. All rights
 * 						reserved.
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

package org.openecomp.sdnc.vnfapi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.OptimisticLockFailedException;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.NetworkTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.NetworkTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.NetworkTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.NetworkTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadNetworkTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadNetworkTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadNetworkTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadNetworkTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModuleTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModuleTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModuleTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModuleTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModules;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModulesBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstanceTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstanceTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstanceTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstanceTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstances;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstancesBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfs;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfsBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VNFAPIService;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModuleTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModuleTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModuleTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModuleTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModules;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModulesBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstances;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstancesBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.Vnfs;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfsBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.information.NetworkInformationBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.data.PreloadData;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.data.PreloadDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.model.information.VnfPreloadList;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.model.information.VnfPreloadListBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.model.information.VnfPreloadListKey;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vf.module.model.information.VfModulePreloadList;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vf.module.model.information.VfModulePreloadListBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vf.module.model.information.VfModulePreloadListKey;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vnf.instance.model.information.VnfInstancePreloadList;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vnf.instance.model.information.VnfInstancePreloadListBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vnf.instance.model.information.VnfInstancePreloadListKey;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.request.information.RequestInformation;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.sdnc.request.header.SdncRequestHeader;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.sdnc.request.header.SdncRequestHeader.SvcAction;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.data.ServiceData;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.data.ServiceDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatus;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatus.RequestStatus;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatus.RpcAction;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatus.RpcName;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatus.VnfsdnAction;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatus.VnfsdnSubaction;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatusBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.information.VfModuleInformationBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.model.infrastructure.VfModuleList;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.model.infrastructure.VfModuleListBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.model.infrastructure.VfModuleListKey;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.preload.data.VfModulePreloadData;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.preload.data.VfModulePreloadDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.service.data.VfModuleServiceData;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.service.data.VfModuleServiceDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.information.VnfInformationBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.information.VnfInstanceInformationBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.model.infrastructure.VnfInstanceList;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.model.infrastructure.VnfInstanceListBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.model.infrastructure.VnfInstanceListKey;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.preload.data.VnfInstancePreloadData;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.preload.data.VnfInstancePreloadDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.service.data.VnfInstanceServiceData;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.service.data.VnfInstanceServiceDataBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.model.infrastructure.VnfList;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.model.infrastructure.VnfListBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.model.infrastructure.VnfListKey;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;


/**
 * Defines a base implementation for your provider. This class extends from a helper class
 * which provides storage for the most commonly used components of the MD-SAL. Additionally the
 * base class provides some basic logging and initialization / clean up methods.
 *
 */

public class vnfapiProvider implements AutoCloseable, VNFAPIService, DataChangeListener{

	private final Logger log = LoggerFactory.getLogger( vnfapiProvider.class );
	private final String appName = "vnfapi";
	private final ExecutorService executor;


	private ListenerRegistration<DataChangeListener> dclServices;

	protected DataBroker dataBroker;
	protected NotificationProviderService notificationService;
	protected RpcProviderRegistry rpcRegistry;
	protected BindingAwareBroker.RpcRegistration<VNFAPIService> rpcRegistration;



	public vnfapiProvider(DataBroker dataBroker2,
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
		// Create the top level containers
		createContainers();
		try {
			VnfSdnUtil.loadProperties();
		} catch (Exception e) {
			log.error("Caught Exception while trying to load properties file");
		}
		rpcRegistration = rpcRegistry.addRpcImplementation(VNFAPIService.class, this);

		log.info( "Initialization complete for " + appName );
	}

	private void createContainers() {
		final WriteTransaction t = dataBroker.newReadWriteTransaction();

		// Create the Vnfs container
		t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(Vnfs.class),
				new VnfsBuilder().build());
		t.merge(LogicalDatastoreType.OPERATIONAL, InstanceIdentifier.create(Vnfs.class),
				new VnfsBuilder().build());

		// Create the PreloadVnfs container
		t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(PreloadVnfs.class),
				new PreloadVnfsBuilder().build());
		t.merge(LogicalDatastoreType.OPERATIONAL, InstanceIdentifier.create(PreloadVnfs.class),
				new PreloadVnfsBuilder().build());

		// 1610 Create the PreloadVnfInstances container
		t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(PreloadVnfInstances.class),
				new PreloadVnfInstancesBuilder().build());
		t.merge(LogicalDatastoreType.OPERATIONAL, InstanceIdentifier.create(PreloadVnfInstances.class),
				new PreloadVnfInstancesBuilder().build());

		// 1610 Create the VnfInstances container
		t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(VnfInstances.class),
				new VnfInstancesBuilder().build());
		t.merge(LogicalDatastoreType.OPERATIONAL, InstanceIdentifier.create(VnfInstances.class),
				new VnfInstancesBuilder().build());

		// 1610 Create the PreloadVfModules container
		t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(PreloadVfModules.class),
				new PreloadVfModulesBuilder().build());
		t.merge(LogicalDatastoreType.OPERATIONAL, InstanceIdentifier.create(PreloadVfModules.class),
				new PreloadVfModulesBuilder().build());

		// 1610 Create the VfModules container
		t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(VfModules.class),
				new VfModulesBuilder().build());
		t.merge(LogicalDatastoreType.OPERATIONAL, InstanceIdentifier.create(VfModules.class),
				new VfModulesBuilder().build());

		try {
			CheckedFuture<Void, TransactionCommitFailedException> checkedFuture = t.submit();
			checkedFuture.get();
			log.info("Create Containers succeeded!: ");

		} catch (InterruptedException | ExecutionException e) {
			log.error("Create Containers Failed: " + e);
			e.printStackTrace();
		}
	}



	protected void initializeChild() {
		//Override if you have custom initialization intelligence
	}

	@Override
	public void close() throws Exception {
		log.info( "Closing provider for " + appName );
	    executor.shutdown();
	    rpcRegistration.close();
		// dclServices.close();
		log.info( "Successfully closed provider for " + appName );
	}

	// On data change not used
	@Override
	public void onDataChanged(
			AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
		boolean changed = false;
		log.info("   IN ON DATA CHANGE: ");
		WriteTransaction writeTransaction = dataBroker
				.newWriteOnlyTransaction();

		DataObject updatedSubTree = change.getUpdatedSubtree();

		if (updatedSubTree != null) {
			if (log.isDebugEnabled())
			{
				log.debug("updatedSubTree was non-null:" + updatedSubTree);
			}
			if ( updatedSubTree instanceof Vnfs ) {
				ArrayList<VnfList> vnfList = (ArrayList<VnfList>) ((Vnfs) updatedSubTree).getVnfList();
				if (vnfList != null) {
					for (VnfList entry : vnfList) {
						ServiceData serviceData = entry.getServiceData();
						ServiceStatus serviceStatus = entry.getServiceStatus();
						if (serviceData != null && serviceStatus != null) {
							//
							// ServiceData change detected, check the AckFinal indicator and request-status to see if we need to proceed.
							//
							if ((! "Y".equals(serviceStatus.getFinalIndicator())) && (RequestStatus.Synccomplete.equals(serviceStatus.getRequestStatus()))) {
								if (log.isDebugEnabled())
								{
									log.debug("Final Indicator is not Y, calling handleServiceDataUpdated");
								}
								//handleServiceDataUpdated(serviceData, serviceStatus, writeTransaction);
								changed = true;
							}
						}
					}
				}
			}
			if ( updatedSubTree instanceof PreloadVnfs ) {
				ArrayList<VnfPreloadList> vnfList = (ArrayList<VnfPreloadList>) ((PreloadVnfs) updatedSubTree).getVnfPreloadList();
				if (vnfList != null) {
					for (VnfPreloadList entry : vnfList) {
						PreloadData preloadData = entry.getPreloadData();
						if (preloadData != null ) {
							//
							// PreloadData change detected
							//
							// handlePreloadDataUpdated(preloadData, writeTransaction);
							changed = true;
						}
					}
				}
			}
			//1610
			if ( updatedSubTree instanceof PreloadVnfInstances ) {
				ArrayList<VnfInstancePreloadList> vnfInstanceList = (ArrayList<VnfInstancePreloadList>) ((PreloadVnfInstances) updatedSubTree).getVnfInstancePreloadList();
				if (vnfInstanceList != null) {
					for (VnfInstancePreloadList entry : vnfInstanceList) {
						VnfInstancePreloadData vnfInstancePreloadData = entry.getVnfInstancePreloadData();
						if (vnfInstancePreloadData != null ) {
							//
							// PreloadData change detected
							//
							// handlePreloadDataUpdated(preloadData, writeTransaction);
							changed = true;
						}
					}
				}
			}
            //1610
			if ( updatedSubTree instanceof VnfInstances ) {
				ArrayList<VnfInstanceList> vnfInstanceList = (ArrayList<VnfInstanceList>) ((VnfInstances) updatedSubTree).getVnfInstanceList();
				if (vnfInstanceList != null) {
					for (VnfInstanceList entry : vnfInstanceList) {
						VnfInstanceServiceData vnfInstanceServiceData = entry.getVnfInstanceServiceData();
						ServiceStatus serviceStatus = entry.getServiceStatus();
						if (vnfInstanceServiceData != null && serviceStatus != null) {
							//
							// VnfInstanceServiceData change detected, check the AckFinal indicator and request-status to see if we need to proceed.
							//
							if ((! "Y".equals(serviceStatus.getFinalIndicator())) && (RequestStatus.Synccomplete.equals(serviceStatus.getRequestStatus()))) {
								if (log.isDebugEnabled())
								{
									log.debug("Final Indicator is not Y, calling handleServiceDataUpdated");
								}
								//handleServiceDataUpdated(serviceData, serviceStatus, writeTransaction);
								changed = true;
							}
						}
					}
				}
			}
			//1610
			if ( updatedSubTree instanceof PreloadVfModules ) {
				ArrayList<VfModulePreloadList> vnfInstanceList = (ArrayList<VfModulePreloadList>) ((PreloadVfModules) updatedSubTree).getVfModulePreloadList();
				if (vnfInstanceList != null) {
					for (VfModulePreloadList entry : vnfInstanceList) {
						VfModulePreloadData vnfInstancePreloadData = entry.getVfModulePreloadData();
						if (vnfInstancePreloadData != null ) {
							//
							// PreloadData change detected
							//
							// handlePreloadDataUpdated(preloadData, writeTransaction);
							changed = true;
						}
					}
				}
            }
            //1610
			if ( updatedSubTree instanceof VfModules ) {
				ArrayList<VfModuleList> vfModuleList = (ArrayList<VfModuleList>) ((VfModules) updatedSubTree).getVfModuleList();
				if (vfModuleList != null) {
					for (VfModuleList entry : vfModuleList) {
						VfModuleServiceData vfModuleServiceData = entry.getVfModuleServiceData();
						ServiceStatus serviceStatus = entry.getServiceStatus();
						if (vfModuleServiceData != null && serviceStatus != null) {
							//
							// VfModuleServiceData change detected, check the AckFinal indicator and request-status to see if we need to proceed.
							//
							if ((! "Y".equals(serviceStatus.getFinalIndicator())) && (RequestStatus.Synccomplete.equals(serviceStatus.getRequestStatus()))) {
								if (log.isDebugEnabled())
								{
									log.debug("Final Indicator is not Y, calling handleServiceDataUpdated");
								}
								//handleServiceDataUpdated(serviceData, serviceStatus, writeTransaction);
								changed = true;
							}
						}
					}
				}
			}
		}
		// Do the write transaction only if something changed.
		if (changed) {
			CheckedFuture<Void, TransactionCommitFailedException> checkedFuture = writeTransaction
					.submit();
			Futures.addCallback(checkedFuture, new FutureCallback<Void>() {

				@Override
				public void onSuccess(Void arg0) {
					log.debug("Successfully updated Service Status");
				}

				@Override
				public void onFailure(Throwable ex) {
					log.debug(
							"Failed updating Service Status",
							ex);
				}
			}, executor);
		}
	}

	private static class Iso8601Util
	{
	    private static TimeZone tz = TimeZone.getTimeZone("UTC");
	    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	    static
	    {
	        df.setTimeZone(tz);
	    }

	    private static String now()
	    {
	        return df.format(new Date());
	    }
	}

	private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder, String errorCode, String errorMessage, String ackFinal)
	{
		serviceStatusBuilder.setResponseCode(errorCode);
		serviceStatusBuilder.setResponseMessage(errorMessage);
		serviceStatusBuilder.setFinalIndicator(ackFinal);
		serviceStatusBuilder.setResponseTimestamp(Iso8601Util.now());
	}

	private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder,  RequestInformation requestInformation)
	{
		if (requestInformation != null && requestInformation.getRequestAction() != null) {
			switch (requestInformation.getRequestAction())
			{
			case VNFActivateRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.VNFActivateRequest);
				break;
			case ChangeVNFActivateRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.ChangeVNFActivateRequest);
				break;
			case DisconnectVNFRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.DisconnectVNFRequest);
				break;
			case PreloadVNFRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.PreloadVNFRequest);
				break;
			case DeletePreloadVNFRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.DeletePreloadVNFRequest);
				break;
        // 1610 vnf-instance Requests
			case VnfInstanceActivateRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.VnfInstanceActivateRequest);
				break;
			case ChangeVnfInstanceActivateRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.ChangeVnfInstanceActivateRequest);
				break;
			case DisconnectVnfInstanceRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.DisconnectVnfInstanceRequest);
				break;
			case PreloadVnfInstanceRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.PreloadVnfInstanceRequest);
				break;
        // 1610 vf-module Requests
			case VfModuleActivateRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.VfModuleActivateRequest);
				break;
			case ChangeVfModuleActivateRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.ChangeVfModuleActivateRequest);
				break;
			case DisconnectVfModuleRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.DisconnectVfModuleRequest);
				break;
			case PreloadVfModuleRequest:
				serviceStatusBuilder.setVnfsdnAction(VnfsdnAction.PreloadVfModuleRequest);
				break;
			default:
				log.error("Unknown RequestAction: " + requestInformation.getRequestAction() );
				break;
			};
		}
		if (requestInformation != null && requestInformation.getRequestSubAction() != null) {
			switch (requestInformation.getRequestSubAction())
			{
			case SUPP:
				serviceStatusBuilder.setVnfsdnSubaction(VnfsdnSubaction.SUPP);
				break;
			case CANCEL:
				serviceStatusBuilder.setVnfsdnSubaction(VnfsdnSubaction.CANCEL);
				break;
			default:
				log.error("Unknown RequestSubAction: " + requestInformation.getRequestSubAction() );
				break;
			};
		}
	}

	private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder,  SdncRequestHeader requestHeader)
	{
		if (requestHeader != null && requestHeader.getSvcAction() != null) {
			switch (requestHeader.getSvcAction())
			{
			case Reserve:
				serviceStatusBuilder.setRpcAction(RpcAction.Reserve);
				break;
			case Activate:
				serviceStatusBuilder.setRpcAction(RpcAction.Activate);
				break;
			case Assign:
				serviceStatusBuilder.setRpcAction(RpcAction.Assign);
				break;
			case Delete:
				serviceStatusBuilder.setRpcAction(RpcAction.Delete);
				break;
			case Changeassign:
				serviceStatusBuilder.setRpcAction(RpcAction.Changeassign);
				break;
			case Changedelete:
				serviceStatusBuilder.setRpcAction(RpcAction.Changedelete);
				break;
			case Rollback:
				serviceStatusBuilder.setRpcAction(RpcAction.Rollback);
				break;
			default:
				log.error("Unknown SvcAction: " + requestHeader.getSvcAction() );
				break;
			};
		}
	}

	private void getServiceData(String siid, ServiceDataBuilder serviceDataBuilder)
	{
		// default to config
		getServiceData(siid,serviceDataBuilder,LogicalDatastoreType.CONFIGURATION);
	}


	private void getServiceData(String siid, ServiceDataBuilder serviceDataBuilder, LogicalDatastoreType type)
	{
		// See if any data exists yet for this siid, if so grab it.
		InstanceIdentifier serviceInstanceIdentifier =
				InstanceIdentifier.<Vnfs>builder(Vnfs.class)
				.child(VnfList.class, new VnfListKey(siid)).toInstance();
		ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
		Optional<VnfList> data = null;
		try {
			data = (Optional<VnfList>) readTx.read(type, serviceInstanceIdentifier).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Caught Exception reading MD-SAL ("+type+") for ["+siid+"] " ,e);
		}

		if ( data.isPresent()) {
			ServiceData serviceData = (ServiceData) data.get().getServiceData();
			if (serviceData != null) {
				log.info("Read MD-SAL ("+type+") data for ["+siid+"] ServiceData: " + serviceData);
				serviceDataBuilder.setSdncRequestHeader(serviceData.getSdncRequestHeader());
				serviceDataBuilder.setRequestInformation(serviceData.getRequestInformation());
				serviceDataBuilder.setServiceInformation(serviceData.getServiceInformation());
				serviceDataBuilder.setVnfRequestInformation(serviceData.getVnfRequestInformation());
				serviceDataBuilder.setVnfId(serviceData.getVnfId());
				serviceDataBuilder.setVnfTopologyInformation(serviceData.getVnfTopologyInformation());
				serviceDataBuilder.setOperStatus(serviceData.getOperStatus());
			} else {
				log.info("No service-data found in MD-SAL ("+type+") for ["+siid+"] ");
			}
		} else {
			log.info("No data found in MD-SAL ("+type+") for ["+siid+"] ");
		}
	}

	//1610 vnf-instance
	private void getVnfInstanceServiceData(String siid, VnfInstanceServiceDataBuilder vnfInstanceServiceDataBuilder)
	{
		// default to config
		getVnfInstanceServiceData(siid,vnfInstanceServiceDataBuilder,LogicalDatastoreType.CONFIGURATION);
	}
	//1610 vnf-instance
	private void getVnfInstanceServiceData(String siid, VnfInstanceServiceDataBuilder vnfInstanceServiceDataBuilder, LogicalDatastoreType type)
	{
		// See if any data exists yet for this siid, if so grab it.
		InstanceIdentifier vnfInstanceIdentifier =
				InstanceIdentifier.<VnfInstances>builder(VnfInstances.class)
				.child(VnfInstanceList.class, new VnfInstanceListKey(siid)).toInstance();
		ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
		Optional<VnfInstanceList> data = null;
		try {
			data = (Optional<VnfInstanceList>) readTx.read(type, vnfInstanceIdentifier).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Caught Exception reading MD-SAL ("+type+") for ["+siid+"] " ,e);
		}

		if ( data.isPresent()) {
			VnfInstanceServiceData vnfInstanceServiceData = (VnfInstanceServiceData) data.get().getVnfInstanceServiceData();
			if (vnfInstanceServiceData != null) {
				log.info("Read MD-SAL ("+type+") data for ["+siid+"] VnfInstanceServiceData: " + vnfInstanceServiceData);
				vnfInstanceServiceDataBuilder.setSdncRequestHeader(vnfInstanceServiceData.getSdncRequestHeader());
				vnfInstanceServiceDataBuilder.setRequestInformation(vnfInstanceServiceData.getRequestInformation());
				vnfInstanceServiceDataBuilder.setServiceInformation(vnfInstanceServiceData.getServiceInformation());
				vnfInstanceServiceDataBuilder.setVnfInstanceRequestInformation(vnfInstanceServiceData.getVnfInstanceRequestInformation());
				vnfInstanceServiceDataBuilder.setVnfInstanceId(vnfInstanceServiceData.getVnfInstanceId());
				vnfInstanceServiceDataBuilder.setVnfInstanceTopologyInformation(vnfInstanceServiceData.getVnfInstanceTopologyInformation());
				vnfInstanceServiceDataBuilder.setOperStatus(vnfInstanceServiceData.getOperStatus());
			} else {
				log.info("No vnf-instance-service-data found in MD-SAL ("+type+") for ["+siid+"] ");
			}
		} else {
			log.info("No data found in MD-SAL ("+type+") for ["+siid+"] ");
		}
	}

	//1610 vf-module
	private void getVfModuleServiceData(String siid, VfModuleServiceDataBuilder vfModuleServiceDataBuilder)
	{
		// default to config
		getVfModuleServiceData(siid,vfModuleServiceDataBuilder,LogicalDatastoreType.CONFIGURATION);
	}
	//1610 vf-module
	private void getVfModuleServiceData(String siid, VfModuleServiceDataBuilder vfModuleServiceDataBuilder, LogicalDatastoreType type)
	{
		// See if any data exists yet for this siid, if so grab it.
		InstanceIdentifier vfModuleIdentifier =
				InstanceIdentifier.<VfModules>builder(VfModules.class)
				.child(VfModuleList.class, new VfModuleListKey(siid)).toInstance();
		ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
		Optional<VfModuleList> data = null;
		try {
			data = (Optional<VfModuleList>) readTx.read(type, vfModuleIdentifier).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Caught Exception reading MD-SAL ("+type+") for ["+siid+"] " ,e);
		}

		if ( data.isPresent()) {
			VfModuleServiceData vfModuleServiceData = (VfModuleServiceData) data.get().getVfModuleServiceData();
			if (vfModuleServiceData != null) {
				log.info("Read MD-SAL ("+type+") data for ["+siid+"] VfModuleServiceData: " + vfModuleServiceData);
				vfModuleServiceDataBuilder.setSdncRequestHeader(vfModuleServiceData.getSdncRequestHeader());
				vfModuleServiceDataBuilder.setRequestInformation(vfModuleServiceData.getRequestInformation());
				vfModuleServiceDataBuilder.setServiceInformation(vfModuleServiceData.getServiceInformation());
				vfModuleServiceDataBuilder.setVfModuleRequestInformation(vfModuleServiceData.getVfModuleRequestInformation());
				vfModuleServiceDataBuilder.setVfModuleId(vfModuleServiceData.getVfModuleId());
				vfModuleServiceDataBuilder.setVfModuleTopologyInformation(vfModuleServiceData.getVfModuleTopologyInformation());
				vfModuleServiceDataBuilder.setOperStatus(vfModuleServiceData.getOperStatus());
			} else {
				log.info("No vf-module-service-data found in MD-SAL ("+type+") for ["+siid+"] ");
			}
		} else {
			log.info("No data found in MD-SAL ("+type+") for ["+siid+"] ");
		}
	}


	private void getPreloadData(String vnf_name, String vnf_type, PreloadDataBuilder preloadDataBuilder)
	{
		// default to config
		getPreloadData(vnf_name, vnf_type ,preloadDataBuilder,LogicalDatastoreType.CONFIGURATION);
	}

	private void getPreloadData(String preload_name, String preload_type, PreloadDataBuilder preloadDataBuilder, LogicalDatastoreType type)
	{
		// See if any data exists yet for this name/type, if so grab it.
		InstanceIdentifier preloadInstanceIdentifier =
				InstanceIdentifier.<PreloadVnfs>builder(PreloadVnfs.class)
				.child(VnfPreloadList.class, new VnfPreloadListKey(preload_name, preload_type)).toInstance();
		ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
		Optional<VnfPreloadList> data = null;
		try {
			data = (Optional<VnfPreloadList>) readTx.read(type, preloadInstanceIdentifier).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Caught Exception reading MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] " ,e);
		}

		if ( data.isPresent()) {
			PreloadData preloadData = (PreloadData) data.get().getPreloadData();
			if (preloadData != null) {
				log.info("Read MD-SAL ("+type+") data for ["+preload_name+","+preload_type+"] PreloadData: " + preloadData);
				preloadDataBuilder.setVnfTopologyInformation(preloadData.getVnfTopologyInformation());
				preloadDataBuilder.setNetworkTopologyInformation(preloadData.getNetworkTopologyInformation());
				preloadDataBuilder.setOperStatus(preloadData.getOperStatus());
			} else {
				log.info("No preload-data found in MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] ");
			}
		} else {
			log.info("No data found in MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] ");
		}
	}

    //1610 preload-vnf-instance
	private void getVnfInstancePreloadData(String vnf_name, String vnf_type, VnfInstancePreloadDataBuilder preloadDataBuilder)
	{
		// default to config
		getVnfInstancePreloadData(vnf_name, vnf_type ,preloadDataBuilder,LogicalDatastoreType.CONFIGURATION);
	}

	//1610 preload-vnf-instance
	private void getVnfInstancePreloadData(String preload_name, String preload_type, VnfInstancePreloadDataBuilder preloadDataBuilder, LogicalDatastoreType type)
	{
		// See if any data exists yet for this name/type, if so grab it.
		InstanceIdentifier preloadInstanceIdentifier =
				InstanceIdentifier.<PreloadVnfInstances>builder(PreloadVnfInstances.class)
				.child(VnfInstancePreloadList.class, new VnfInstancePreloadListKey(preload_name, preload_type)).toInstance();
		ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
		Optional<VnfInstancePreloadList> data = null;
		try {
			data = (Optional<VnfInstancePreloadList>) readTx.read(type, preloadInstanceIdentifier).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Caught Exception reading MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] " ,e);
		}

		if ( data.isPresent()) {
			VnfInstancePreloadData preloadData = (VnfInstancePreloadData) data.get().getVnfInstancePreloadData();
			if (preloadData != null) {
				log.info("Read MD-SAL ("+type+") data for ["+preload_name+","+preload_type+"] VnfInstancePreloadData: " + preloadData);
				preloadDataBuilder.setVnfInstanceTopologyInformation(preloadData.getVnfInstanceTopologyInformation());
				preloadDataBuilder.setOperStatus(preloadData.getOperStatus());
			} else {
				log.info("No vnf-instance-preload-data found in MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] ");
			}
		} else {
			log.info("No data found in MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] ");
		}
	}

    // 1610 preload-vf-module
	private void getVfModulePreloadData(String vnf_name, String vnf_type, VfModulePreloadDataBuilder preloadDataBuilder)
	{
		// default to config
		getVfModulePreloadData(vnf_name, vnf_type ,preloadDataBuilder,LogicalDatastoreType.CONFIGURATION);
	}

	private void getVfModulePreloadData(String preload_name, String preload_type, VfModulePreloadDataBuilder preloadDataBuilder, LogicalDatastoreType type)
	{
		// See if any data exists yet for this name/type, if so grab it.
		InstanceIdentifier preloadInstanceIdentifier =
				InstanceIdentifier.<PreloadVfModules>builder(PreloadVfModules.class)
				.child(VfModulePreloadList.class, new VfModulePreloadListKey(preload_name, preload_type)).toInstance();
		ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
		Optional<VfModulePreloadList> data = null;
		try {
			data = (Optional<VfModulePreloadList>) readTx.read(type, preloadInstanceIdentifier).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Caught Exception reading MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] " ,e);
		}

		if ( data.isPresent()) {
			VfModulePreloadData preloadData = (VfModulePreloadData) data.get().getVfModulePreloadData();
			if (preloadData != null) {
				log.info("Read MD-SAL ("+type+") data for ["+preload_name+","+preload_type+"] VfModulePreloadData: " + preloadData);
				preloadDataBuilder.setVfModuleTopologyInformation(preloadData.getVfModuleTopologyInformation());
				preloadDataBuilder.setOperStatus(preloadData.getOperStatus());
			} else {
				log.info("No preload-data found in MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] ");
			}
		} else {
			log.info("No data found in MD-SAL ("+type+") for ["+preload_name+","+preload_type+"] ");
		}
	}

	private void SaveVnfList (final VnfList entry, boolean merge, LogicalDatastoreType storeType) throws IllegalStateException {
		// Each entry will be identifiable by a unique key, we have to create that identifier
		InstanceIdentifier.InstanceIdentifierBuilder<VnfList> vnfListIdBuilder =
				InstanceIdentifier.<Vnfs>builder(Vnfs.class)
				.child(VnfList.class, entry.getKey());
		InstanceIdentifier<VnfList> path = vnfListIdBuilder.toInstance();

		int tries = 2;
		while(true) {
			try {
				WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
				if (merge) {
					tx.merge(storeType, path, entry);
				} else {
					tx.put(storeType, path, entry);
				}
				tx.submit().checkedGet();
				log.debug("Update DataStore succeeded");
				break;
			} catch (final TransactionCommitFailedException e) {
				if(e instanceof OptimisticLockFailedException) {
					if(--tries <= 0) {
						log.debug("Got OptimisticLockFailedException on last try - failing ");
						throw new IllegalStateException(e);
					}
					log.debug("Got OptimisticLockFailedException - trying again ");
				} else {
					log.debug("Update DataStore failed");
					throw new IllegalStateException(e);
				}
			}
		}

	}
    private void DeleteVnfList (final VnfList entry, LogicalDatastoreType storeType) throws IllegalStateException {
        // Each entry will be identifiable by a unique key, we have to create that identifier
		InstanceIdentifier.InstanceIdentifierBuilder<VnfList> vnfListIdBuilder =
				InstanceIdentifier.<Vnfs>builder(Vnfs.class)
				.child(VnfList.class, entry.getKey());
		InstanceIdentifier<VnfList> path = vnfListIdBuilder.toInstance();

        int tries = 2;
        while (true) {
            try {
                WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
                tx.delete(storeType, path);
                tx.submit().checkedGet();
                log.debug("DataStore delete succeeded");
                break;
            } catch (final TransactionCommitFailedException e) {
                if (e instanceof OptimisticLockFailedException) {
                    if (--tries <= 0) {
                        log.debug("Got OptimisticLockFailedException on last try - failing ");
                        throw new IllegalStateException(e);
                    }
                    log.debug("Got OptimisticLockFailedException - trying again ");
                } else {
                    log.debug("Delete DataStore failed");
                    throw new IllegalStateException(e);
                }
            }
        }
    }

	//1610 vnf-instance
	private void SaveVnfInstanceList (final VnfInstanceList entry, boolean merge, LogicalDatastoreType storeType) throws IllegalStateException {
		// Each entry will be identifiable by a unique key, we have to create that identifier
		InstanceIdentifier.InstanceIdentifierBuilder<VnfInstanceList> vnfInstanceListIdBuilder =
				InstanceIdentifier.<VnfInstances>builder(VnfInstances.class)
				.child(VnfInstanceList.class, entry.getKey());
		InstanceIdentifier<VnfInstanceList> path = vnfInstanceListIdBuilder.toInstance();

		int tries = 2;
		while(true) {
			try {
				WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
				if (merge) {
					tx.merge(storeType, path, entry);
				} else {
					tx.put(storeType, path, entry);
				}
				tx.submit().checkedGet();
				log.debug("Update DataStore succeeded");
				break;
			} catch (final TransactionCommitFailedException e) {
				if(e instanceof OptimisticLockFailedException) {
					if(--tries <= 0) {
						log.debug("Got OptimisticLockFailedException on last try - failing ");
						throw new IllegalStateException(e);
					}
					log.debug("Got OptimisticLockFailedException - trying again ");
				} else {
					log.debug("Update DataStore failed");
					throw new IllegalStateException(e);
				}
			}
		}
	}

	//1610 vf-module
	private void SaveVfModuleList (final VfModuleList entry, boolean merge, LogicalDatastoreType storeType) throws IllegalStateException {
		// Each entry will be identifiable by a unique key, we have to create that identifier
		InstanceIdentifier.InstanceIdentifierBuilder<VfModuleList> vfModuleListIdBuilder =
				InstanceIdentifier.<VfModules>builder(VfModules.class)
				.child(VfModuleList.class, entry.getKey());
		InstanceIdentifier<VfModuleList> path = vfModuleListIdBuilder.toInstance();

		int tries = 2;
		while(true) {
			try {
				WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
				if (merge) {
					tx.merge(storeType, path, entry);
				} else {
					tx.put(storeType, path, entry);
				}
				tx.submit().checkedGet();
				log.debug("Update DataStore succeeded");
				break;
			} catch (final TransactionCommitFailedException e) {
				if(e instanceof OptimisticLockFailedException) {
					if(--tries <= 0) {
						log.debug("Got OptimisticLockFailedException on last try - failing ");
						throw new IllegalStateException(e);
					}
					log.debug("Got OptimisticLockFailedException - trying again ");
				} else {
					log.debug("Update DataStore failed");
					throw new IllegalStateException(e);
				}
			}
		}
	}

	private void SavePreloadList(final VnfPreloadList entry, boolean merge, LogicalDatastoreType storeType) throws IllegalStateException{

		// Each entry will be identifiable by a unique key, we have to create that identifier
		InstanceIdentifier.InstanceIdentifierBuilder<VnfPreloadList> vnfListIdBuilder =
				InstanceIdentifier.<PreloadVnfs>builder(PreloadVnfs.class)
				.child(VnfPreloadList.class, entry.getKey());
		InstanceIdentifier<VnfPreloadList> path = vnfListIdBuilder.toInstance();
		int tries = 2;
		while(true) {
			try {
				WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
				if (merge) {
					tx.merge(storeType, path, entry);
				} else {
					tx.put(storeType, path, entry);
				}
				tx.submit().checkedGet();
				log.debug("Update DataStore succeeded");
				break;
			} catch (final TransactionCommitFailedException e) {
				if(e instanceof OptimisticLockFailedException) {
					if(--tries <= 0) {
						log.debug("Got OptimisticLockFailedException on last try - failing ");
						throw new IllegalStateException(e);
					}
					log.debug("Got OptimisticLockFailedException - trying again ");
				} else {
					log.debug("Update DataStore failed");
					throw new IllegalStateException(e);
				}
			}
		}
	}

    //1610 preload vnf-instance
	private void SaveVnfInstancePreloadList(final VnfInstancePreloadList entry, boolean merge, LogicalDatastoreType storeType) throws IllegalStateException{

		// Each entry will be identifiable by a unique key, we have to create that identifier
		InstanceIdentifier.InstanceIdentifierBuilder<VnfInstancePreloadList> vnfInstanceListIdBuilder =
				InstanceIdentifier.<PreloadVnfInstances>builder(PreloadVnfInstances.class)
				.child(VnfInstancePreloadList.class, entry.getKey());
		InstanceIdentifier<VnfInstancePreloadList> path = vnfInstanceListIdBuilder.toInstance();
		int tries = 2;
		while(true) {
			try {
				WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
				if (merge) {
					tx.merge(storeType, path, entry);
				} else {
					tx.put(storeType, path, entry);
				}
				tx.submit().checkedGet();
				log.debug("Update DataStore succeeded");
				break;
			} catch (final TransactionCommitFailedException e) {
				if(e instanceof OptimisticLockFailedException) {
					if(--tries <= 0) {
						log.debug("Got OptimisticLockFailedException on last try - failing ");
						throw new IllegalStateException(e);
					}
					log.debug("Got OptimisticLockFailedException - trying again ");
				} else {
					log.debug("Update DataStore failed");
					throw new IllegalStateException(e);
				}
			}
		}
	}

    //1610 preload vf-module
	private void SaveVfModulePreloadList(final VfModulePreloadList entry, boolean merge, LogicalDatastoreType storeType) throws IllegalStateException{

		// Each entry will be identifiable by a unique key, we have to create that identifier
		InstanceIdentifier.InstanceIdentifierBuilder<VfModulePreloadList> vfModuleListIdBuilder =
				InstanceIdentifier.<PreloadVfModules>builder(PreloadVfModules.class)
				.child(VfModulePreloadList.class, entry.getKey());
		InstanceIdentifier<VfModulePreloadList> path = vfModuleListIdBuilder.toInstance();
		int tries = 2;
		while(true) {
			try {
				WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
				if (merge) {
					tx.merge(storeType, path, entry);
				} else {
					tx.put(storeType, path, entry);
				}
				tx.submit().checkedGet();
				log.debug("Update DataStore succeeded");
				break;
			} catch (final TransactionCommitFailedException e) {
				if(e instanceof OptimisticLockFailedException) {
					if(--tries <= 0) {
						log.debug("Got OptimisticLockFailedException on last try - failing ");
						throw new IllegalStateException(e);
					}
					log.debug("Got OptimisticLockFailedException - trying again ");
				} else {
					log.debug("Update DataStore failed");
					throw new IllegalStateException(e);
				}
			}
		}
	}

	//1610 vnf-instance-topology-operation
	@Override
	public Future<RpcResult<VnfInstanceTopologyOperationOutput>> vnfInstanceTopologyOperation(
			VnfInstanceTopologyOperationInput input) {

		final String SVC_OPERATION = "vnf-instance-topology-operation";
		VnfInstanceServiceData vnfInstanceServiceData = null;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info( SVC_OPERATION +" called." );
		// create a new response object
		VnfInstanceTopologyOperationOutputBuilder responseBuilder = new VnfInstanceTopologyOperationOutputBuilder();

		//if(input == null || input.getVnfInstanceRequestInformation().getVnfInstanceTopologyIdentifier().getVnfInstanceId() == null )
		if(input == null ||
            input.getVnfInstanceRequestInformation() == null  ||
		        input.getVnfInstanceRequestInformation().getVnfInstanceId() == null )
        {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty vnf-instance-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfInstanceTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the service instance ID from the input buffer
		String viid = input.getVnfInstanceRequestInformation().getVnfInstanceId();
		String preload_name  = input.getVnfInstanceRequestInformation().getVnfInstanceName();
		String preload_type = input.getVnfInstanceRequestInformation().getVnfModelId();

		// Make sure we have a valid viid
		if(viid == null || viid.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid vnf-instance-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfInstanceTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

        // Get vnf-instance-preload-data
		VnfInstancePreloadDataBuilder vnfInstancePreloadDataBuilder = new VnfInstancePreloadDataBuilder();
		getVnfInstancePreloadData(preload_name, preload_type, vnfInstancePreloadDataBuilder);

        // Get service-data
		VnfInstanceServiceDataBuilder vnfInstanceServiceDataBuilder = new VnfInstanceServiceDataBuilder();
		getVnfInstanceServiceData(viid,vnfInstanceServiceDataBuilder);

        // Get operational-data
		VnfInstanceServiceDataBuilder operDataBuilder = new VnfInstanceServiceDataBuilder();
		getVnfInstanceServiceData(viid,operDataBuilder, LogicalDatastoreType.OPERATIONAL );

		// Set the serviceStatus based on input
		setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
		setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

		//
		// setup a service-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		//  USES sdnc-request-header;
		//  USES request-information;
		//  USES service-information;
		//  USES vnf-request-information
		// OUTPUT:
		//  USES vnf-topology-response-body;
		//  USES vnf-information
		//  USES service-information
		//
		// container service-data
        //   uses vnf-configuration-information;
        //   uses oper-status;

		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+viid+"] input: " + input);
		VnfInstanceTopologyOperationInputBuilder inputBuilder = new VnfInstanceTopologyOperationInputBuilder(input);
		VnfSdnUtil.toProperties(parms, inputBuilder.build());

		log.info("Adding OPERATIONAL data for "+SVC_OPERATION+" ["+viid+"] operational-data: " + operDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "operational-data", operDataBuilder);

		log.info("Adding CONFIG data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preload-data: " + vnfInstancePreloadDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "vnf-instance-preload-data", vnfInstancePreloadDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		VNFSDNSvcLogicServiceClient svcLogicClient = new VNFSDNSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";


		try
		{
			if (svcLogicClient.hasGraph("VNF-API", SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute("VNF-API", SVC_OPERATION, null, "sync", vnfInstanceServiceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for VNF-API: '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
		}

		setServiceStatus(serviceStatusBuilder,errorCode, errorMessage, ackFinal);
		serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
		serviceStatusBuilder.setRpcName(RpcName.VnfInstanceTopologyOperation);

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);
			VnfInstanceListBuilder vnfInstanceListBuilder = new VnfInstanceListBuilder();
			vnfInstanceListBuilder.setVnfInstanceId(viid);
			vnfInstanceListBuilder.setServiceStatus(serviceStatusBuilder.build());
			try {
				SaveVnfInstanceList (vnfInstanceListBuilder.build(), true,LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+viid+"] \n",e);
			}
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+viid+"] " + responseBuilder.build());
			RpcResult<VnfInstanceTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			vnfInstanceServiceData = vnfInstanceServiceDataBuilder.build();
			log.info("Updating MD-SAL for "+SVC_OPERATION+" ["+viid+"] VnfInstanceServiceData: " + vnfInstanceServiceData);
			// svc-configuration-list
			VnfInstanceListBuilder vnfInstanceListBuilder = new VnfInstanceListBuilder();
			vnfInstanceListBuilder.setVnfInstanceServiceData(vnfInstanceServiceData);
			vnfInstanceListBuilder.setVnfInstanceId(vnfInstanceServiceData.getVnfInstanceId());
			//siid = vnfInstanceServiceData.getVnfInstanceId();
			vnfInstanceListBuilder.setServiceStatus(serviceStatusBuilder.build());
			SaveVnfInstanceList (vnfInstanceListBuilder.build(), false,LogicalDatastoreType.CONFIGURATION);
			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on Delete or Activate
				if (input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Delete) ||
				    input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate))
				{
					log.info("Updating OPERATIONAL tree.");
					SaveVnfInstanceList (vnfInstanceListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
				}
			}
			VnfInstanceInformationBuilder vnfInstanceInformationBuilder = new VnfInstanceInformationBuilder();
			vnfInstanceInformationBuilder.setVnfInstanceId(viid);
			responseBuilder.setVnfInstanceInformation(vnfInstanceInformationBuilder.build());
			responseBuilder.setServiceInformation(vnfInstanceServiceData.getServiceInformation());
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+viid+"] \n",e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+viid+"] " + responseBuilder.build());
			RpcResult<VnfInstanceTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for "+SVC_OPERATION+" ["+viid+"] ");
		log.info("Returned SUCCESS for "+SVC_OPERATION+" ["+viid+"] " + responseBuilder.build());

		RpcResult<VnfInstanceTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<VnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		// return success
		return Futures.immediateFuture(rpcResult);
	}

	//1610 vf-module-topology-operation
	@Override
	public Future<RpcResult<VfModuleTopologyOperationOutput>> vfModuleTopologyOperation(
			VfModuleTopologyOperationInput input) {

		final String SVC_OPERATION = "vf-module-topology-operation";
		VfModuleServiceData vfModuleServiceData = null;
		VnfInstanceServiceData vnfInstanceServiceData = null;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info( SVC_OPERATION +" called." );
		// create a new response object
		VfModuleTopologyOperationOutputBuilder responseBuilder = new VfModuleTopologyOperationOutputBuilder();

        // Validate vf-module-id from vf-module-request-information
        if(input == null ||
            input.getVfModuleRequestInformation() == null ||
                input.getVfModuleRequestInformation().getVfModuleId() == null)
        {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty vf-module-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vf-module-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VfModuleTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the vf-module-request-information.vf-module-id from the input buffer
		String vfid  = input.getVfModuleRequestInformation().getVfModuleId();
		String preload_name  = input.getVfModuleRequestInformation().getVfModuleName();
		String preload_type = input.getVfModuleRequestInformation().getVfModuleModelId();

		// Make sure we have a valid siid
		if(vfid == null || vfid.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid vf-module-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vf-module-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VfModuleTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

        // 1610 add vf-module-id to vnf-instance-list.vf-module-relationship-list
		String viid = input.getVfModuleRequestInformation().getVnfInstanceId();

		if(viid == null || viid.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid vnf-instance-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VfModuleTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

        // Get vf-module-preload-data
		VfModulePreloadDataBuilder vfModulePreloadDataBuilder = new VfModulePreloadDataBuilder();
		getVfModulePreloadData(preload_name, preload_type, vfModulePreloadDataBuilder);

        // Get vf-module-service-data
		VfModuleServiceDataBuilder vfModuleServiceDataBuilder = new VfModuleServiceDataBuilder();
		getVfModuleServiceData(vfid,vfModuleServiceDataBuilder);

        // Get vf-module operation-data
		VfModuleServiceDataBuilder operDataBuilder = new VfModuleServiceDataBuilder();
		getVfModuleServiceData(vfid,operDataBuilder, LogicalDatastoreType.OPERATIONAL );

        // save service-data builder object for rollback
		VfModuleServiceDataBuilder rb_vfModuleServiceDataBuilder = vfModuleServiceDataBuilder;
		VfModuleServiceDataBuilder rb_operDataBuilder = operDataBuilder;

        // 1610 Need to pull vnf-instance-list.vf-module-relationship-list from MD-SAL
		VnfInstanceServiceDataBuilder vnfInstanceServiceDataBuilder = new VnfInstanceServiceDataBuilder();
		getVnfInstanceServiceData(viid, vnfInstanceServiceDataBuilder);

        // vnf-instance operational-data
		VnfInstanceServiceDataBuilder vnfInstanceOperDataBuilder = new VnfInstanceServiceDataBuilder();
		getVnfInstanceServiceData(viid, vnfInstanceOperDataBuilder, LogicalDatastoreType.OPERATIONAL );

        // save operational builder object for rollback
		VnfInstanceServiceDataBuilder rb_vnfInstanceServiceDataBuilder = vnfInstanceServiceDataBuilder;
		VnfInstanceServiceDataBuilder rb_vnfInstanceOperDataBuilder = vnfInstanceOperDataBuilder;

		// Set the serviceStatus based on input
		setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
		setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

		//
		// setup a service-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		//  USES sdnc-request-header;
		//  USES request-information;
		//  USES service-information;
		//  USES vnf-request-information
		// OUTPUT:
		//  USES vnf-topology-response-body;
		//  USES vnf-information
		//  USES service-information
		//
		// container service-data
        //   uses vnf-configuration-information;
        //   uses oper-status;

		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+vfid+"] input: " + input);
		VfModuleTopologyOperationInputBuilder inputBuilder = new VfModuleTopologyOperationInputBuilder(input);
		VnfSdnUtil.toProperties(parms, inputBuilder.build());

		log.info("Adding OPERATIONAL data for "+SVC_OPERATION+" ["+vfid+"] vf-module operational-data: " + operDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "operational-data", operDataBuilder);

		log.info("Adding CONFIG data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] vf-module-preload-data: " + vfModulePreloadDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "vf-module-preload-data", vfModulePreloadDataBuilder);

		log.info("Adding vnf-instance CONFIG data for "+SVC_OPERATION+" ["+viid+"] vnf-instance-service-data: " + vnfInstanceServiceDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "vnf-instance-service-data", vnfInstanceServiceDataBuilder);

		log.info("Adding vnf-instance OPERATIONAL data for "+SVC_OPERATION+" ["+viid+"] vnf-instance operational-data: " + vnfInstanceOperDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "vnf-instance-operational-data", vnfInstanceOperDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		VNFSDNSvcLogicServiceClient svcLogicClient = new VNFSDNSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";


		try
		{
			if (svcLogicClient.hasGraph("VNF-API", SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute("VNF-API", SVC_OPERATION, null, "sync", vfModuleServiceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic on vf-module for "+ SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}

			} else {
				errorMessage = "No service logic active for VNF-API: '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null) {
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
		}

		setServiceStatus(serviceStatusBuilder,errorCode, errorMessage, ackFinal);
		serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
		serviceStatusBuilder.setRpcName(RpcName.VfModuleTopologyOperation);

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);
			VfModuleListBuilder vfModuleListBuilder = new VfModuleListBuilder();
			vfModuleListBuilder.setVfModuleId(vfid);
			vfModuleListBuilder.setServiceStatus(serviceStatusBuilder.build());
			try {
				SaveVfModuleList (vfModuleListBuilder.build(), true,LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+vfid+"] \n",e);
			}
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+vfid+"] " + responseBuilder.build());
			RpcResult<VfModuleTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
        // save vf-module-service-data in MD-SAL
		try {
			vfModuleServiceData = vfModuleServiceDataBuilder.build();
			log.info("Updating MD-SAL for "+SVC_OPERATION+" ["+vfid+"] VfModuleServiceData: " + vfModuleServiceData);
			// vf-module-list
			VfModuleListBuilder vfModuleListBuilder = new VfModuleListBuilder();
			vfModuleListBuilder.setVfModuleServiceData(vfModuleServiceData);
			vfModuleListBuilder.setVfModuleId(vfModuleServiceData.getVfModuleId());
			//vfid = vfModuleServiceData.getVfModuleId();
			vfModuleListBuilder.setServiceStatus(serviceStatusBuilder.build());
			SaveVfModuleList (vfModuleListBuilder.build(), false,LogicalDatastoreType.CONFIGURATION);
			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on Delete or Activate
				if (input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Delete) ||
				    input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate))
				{
					log.info("Updating OPERATIONAL tree.");
					SaveVfModuleList (vfModuleListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
				}
			}
			VfModuleInformationBuilder vfModuleInformationBuilder = new VfModuleInformationBuilder();
			vfModuleInformationBuilder.setVfModuleId(vfid);
			responseBuilder.setVfModuleInformation(vfModuleInformationBuilder.build());
			responseBuilder.setServiceInformation(vfModuleServiceData.getServiceInformation());
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+vfid+"] \n",e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+vfid+"] " + responseBuilder.build());
			RpcResult<VfModuleTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated vf-module in MD-SAL for "+SVC_OPERATION+" ["+vfid+"] ");
		log.info("Returned SUCCESS for "+SVC_OPERATION+" ["+vfid+"] " + responseBuilder.build());

		RpcResult<VfModuleTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		// return success
		return Futures.immediateFuture(rpcResult);
	}


	@Override
	public Future<RpcResult<VnfTopologyOperationOutput>> vnfTopologyOperation(
			VnfTopologyOperationInput input) {

		final String SVC_OPERATION = "vnf-topology-operation";
		ServiceData serviceData = null;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info( SVC_OPERATION +" called." );
		// create a new response object
		VnfTopologyOperationOutputBuilder responseBuilder = new VnfTopologyOperationOutputBuilder();

		if(input == null ||
            input.getServiceInformation() == null ||
                input.getServiceInformation().getServiceInstanceId() == null ||
                    input.getServiceInformation().getServiceInstanceId().length() == 0)
        {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty service-instance-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		if(input.getVnfRequestInformation() == null ||
                input.getVnfRequestInformation().getVnfId() == null ||
                    input.getVnfRequestInformation().getVnfId().length() == 0)
        {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty vf-module-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vf-module-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the service instance ID from the input buffer
		String siid = input.getVnfRequestInformation().getVnfId();
		String preload_name  = input.getVnfRequestInformation().getVnfName();
		String preload_type = input.getVnfRequestInformation().getVnfType();

        /*
		// Make sure we have a valid siid
		if(siid == null || siid.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid siid");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}
        */

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, preloadDataBuilder);

		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid,serviceDataBuilder);

		ServiceDataBuilder operDataBuilder = new ServiceDataBuilder();
		getServiceData(siid,operDataBuilder, LogicalDatastoreType.OPERATIONAL );

		// Set the serviceStatus based on input
		setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
		setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

		//
		// setup a service-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		//  USES sdnc-request-header;
		//  USES request-information;
		//  USES service-information;
		//  USES vnf-request-information
		// OUTPUT:
		//  USES vnf-topology-response-body;
		//  USES vnf-information
		//  USES service-information
		//
		// container service-data
        //   uses vnf-configuration-information;
        //   uses oper-status;

		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+siid+"] input: " + input);
		VnfTopologyOperationInputBuilder inputBuilder = new VnfTopologyOperationInputBuilder(input);
		VnfSdnUtil.toProperties(parms, inputBuilder.build());

		log.info("Adding OPERATIONAL data for "+SVC_OPERATION+" ["+siid+"] operational-data: " + operDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "operational-data", operDataBuilder);

		log.info("Adding CONFIG data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preload-data: " + preloadDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "preload-data", preloadDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		VNFSDNSvcLogicServiceClient svcLogicClient = new VNFSDNSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";


		try
		{
			if (svcLogicClient.hasGraph("VNF-API", SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute("VNF-API", SVC_OPERATION, null, "sync", serviceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for VNF-API: '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
		}

		setServiceStatus(serviceStatusBuilder,errorCode, errorMessage, ackFinal);
		serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
		serviceStatusBuilder.setRpcName(RpcName.VnfTopologyOperation);

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);
			VnfListBuilder vnfListBuilder = new VnfListBuilder();
			vnfListBuilder.setVnfId(siid);
			vnfListBuilder.setServiceStatus(serviceStatusBuilder.build());
			try {
				SaveVnfList (vnfListBuilder.build(), true,LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+siid+"] \n",e);
			}
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+siid+"] " + responseBuilder.build());
			RpcResult<VnfTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			serviceData = serviceDataBuilder.build();
			log.info("Updating MD-SAL for "+SVC_OPERATION+" ["+siid+"] ServiceData: " + serviceData);
			// svc-configuration-list
			VnfListBuilder vnfListBuilder = new VnfListBuilder();
			vnfListBuilder.setServiceData(serviceData);
			vnfListBuilder.setVnfId(serviceData.getVnfId());
			siid = serviceData.getVnfId();
			vnfListBuilder.setServiceStatus(serviceStatusBuilder.build());
			SaveVnfList (vnfListBuilder.build(), false,LogicalDatastoreType.CONFIGURATION);
			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on Delete or Activate
				if (input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate)) {
					log.info("Updating OPERATIONAL tree.");
					SaveVnfList (vnfListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
				}
				else if (input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Delete) ||
				    input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Rollback)) {
					    log.info("Delete OPERATIONAL tree.");
					    DeleteVnfList (vnfListBuilder.build(), LogicalDatastoreType.CONFIGURATION);
					    DeleteVnfList (vnfListBuilder.build(), LogicalDatastoreType.OPERATIONAL);
                }
			}
			VnfInformationBuilder vnfInformationBuilder = new VnfInformationBuilder();
			vnfInformationBuilder.setVnfId(siid);
			responseBuilder.setVnfInformation(vnfInformationBuilder.build());
			responseBuilder.setServiceInformation(serviceData.getServiceInformation());
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+siid+"] \n",e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+siid+"] " + responseBuilder.build());
			RpcResult<VnfTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for "+SVC_OPERATION+" ["+siid+"] ");
		log.info("Returned SUCCESS for "+SVC_OPERATION+" ["+siid+"] " + responseBuilder.build());

		RpcResult<VnfTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		// return success
		return Futures.immediateFuture(rpcResult);
	}


	@Override
	public Future<RpcResult<NetworkTopologyOperationOutput>> networkTopologyOperation(
			NetworkTopologyOperationInput input) {

		final String SVC_OPERATION = "network-topology-operation";
		ServiceData serviceData = null;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info( SVC_OPERATION +" called." );
		// create a new response object
		NetworkTopologyOperationOutputBuilder responseBuilder = new NetworkTopologyOperationOutputBuilder();

		if(input == null ||
            input.getServiceInformation() == null ||
                input.getServiceInformation().getServiceInstanceId() == null ||
                    input.getServiceInformation().getServiceInstanceId().length() == 0)
        {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty service-instance-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<NetworkTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		if(input.getNetworkRequestInformation() == null || input.getNetworkRequestInformation().getNetworkName() == null) {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty service-instance-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<NetworkTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the service instance ID from the input buffer
		String siid = null;
        if (input.getSdncRequestHeader().getSvcAction().equals("assign")) {
		    siid = input.getNetworkRequestInformation().getNetworkName();
        }
        else {
		    siid = input.getNetworkRequestInformation().getNetworkId();
        }
		String preload_name  = input.getNetworkRequestInformation().getNetworkName();
		String preload_type = input.getNetworkRequestInformation().getNetworkType();

        /*
		if(siid == null || siid.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid siid");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<NetworkTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}
        */

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, preloadDataBuilder);

		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+siid+"] input: " + input);
		NetworkTopologyOperationInputBuilder inputBuilder = new NetworkTopologyOperationInputBuilder(input);
		VnfSdnUtil.toProperties(parms, inputBuilder.build());

/*
		log.info("Adding OPERATIONAL data for "+SVC_OPERATION+" ["+siid+"] operational-data: " + operDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "operational-data", operDataBuilder);

		log.info("Adding CONFIG data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preload-data: " + preloadDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "preload-data", preloadDataBuilder);
*/

		// Call SLI sync method
		// Get SvcLogicService reference

		VNFSDNSvcLogicServiceClient svcLogicClient = new VNFSDNSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";
		String networkId = "error";


		try
		{
			if (svcLogicClient.hasGraph("VNF-API", SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute("VNF-API", SVC_OPERATION, null, "sync", preloadDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for VNF-API: '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
			networkId = respProps.getProperty("networkId","0");
		}

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error("Returned FAILED for "+SVC_OPERATION+" ["+siid+"] " + responseBuilder.build());

			RpcResult<NetworkTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			NetworkInformationBuilder networkInformationBuilder = new NetworkInformationBuilder();
			networkInformationBuilder.setNetworkId(networkId);
			responseBuilder.setNetworkInformation(networkInformationBuilder.build());
			responseBuilder.setServiceInformation(input.getServiceInformation());
		} catch (IllegalStateException e) {
			log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+siid+"] \n",e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+siid+"] " + responseBuilder.build());
			RpcResult<NetworkTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for "+SVC_OPERATION+" ["+siid+"] ");
		log.info("Returned SUCCESS for "+SVC_OPERATION+" ["+siid+"] " + responseBuilder.build());

		RpcResult<NetworkTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		// return success
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<PreloadVnfTopologyOperationOutput>> preloadVnfTopologyOperation(
			PreloadVnfTopologyOperationInput input) {

		final String SVC_OPERATION = "preload-vnf-topology-operation";
		PreloadData preloadData = null;
		Properties parms = new Properties();

		log.info( SVC_OPERATION +" called." );
		// create a new response object
		PreloadVnfTopologyOperationOutputBuilder responseBuilder = new PreloadVnfTopologyOperationOutputBuilder();

		// Result from savePreloadData
		final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

		if(input == null || input.getVnfTopologyInformation() == null || input.getVnfTopologyInformation().getVnfTopologyIdentifier() == null || input.getVnfTopologyInformation().getVnfTopologyIdentifier().getVnfName() == null || input.getVnfTopologyInformation().getVnfTopologyIdentifier().getVnfType() == null) {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty vnf-name and vnf-type");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-name and vnf-type");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the name and type from the input buffer
		String preload_name = input.getVnfTopologyInformation().getVnfTopologyIdentifier().getVnfName();
		String preload_type = input.getVnfTopologyInformation().getVnfTopologyIdentifier().getVnfType();

		// Make sure we have a preload_name and preload_type
		if(preload_name == null || preload_name.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid preload-name");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, invalid preload-name");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}
		if(preload_type == null || preload_type.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid preload-type");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, invalid preload-type");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, preloadDataBuilder);
		//preloadData = preloadDataBuilder.build();

		PreloadDataBuilder operDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, operDataBuilder, LogicalDatastoreType.OPERATIONAL );

		//
		// setup a preload-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		//  USES sdnc-request-header;
		//  USES request-information;
        //  uses vnf-topology-information;
		// OUTPUT:
		//  USES vnf-topology-response-body;
		//
		// container preload-data
        //   uses vnf-configuration-information;


		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] input: " + input);
		PreloadVnfTopologyOperationInputBuilder inputBuilder = new PreloadVnfTopologyOperationInputBuilder(input);
		VnfSdnUtil.toProperties(parms, inputBuilder.build());
		log.info("Adding OPERATIONAL data for "+SVC_OPERATION+" ["+preload_name+","+preload_type +"] operational-data: " + operDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "operational-data", operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		VNFSDNSvcLogicServiceClient svcLogicClient = new VNFSDNSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";


		try
		{
			if (svcLogicClient.hasGraph("VNF-API", SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute("VNF-API", SVC_OPERATION, null, "sync", preloadDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for VNF-API: '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
			// internalError = respProps.getProperty("internal-error", "false");
		}

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {

			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
			preloadVnfListBuilder.setVnfName(preload_name);
			preloadVnfListBuilder.setVnfType(preload_type);
			preloadVnfListBuilder.setPreloadData(preloadDataBuilder.build());
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] error code: '" + errorCode + "', Reason: '" + errorMessage + "'");
			try {
				SavePreloadList (preloadVnfListBuilder.build(), true,LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);
			}
			log.debug("Sending Success rpc result due to external error");
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			preloadData = preloadDataBuilder.build();
			log.info("Updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preloadData: " + preloadData);
			// svc-configuration-list
			VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
			preloadVnfListBuilder.setVnfName(preload_name);
			preloadVnfListBuilder.setVnfType(preload_type);
			preloadVnfListBuilder.setPreloadData(preloadData);

			// SDNGC-989 set merge flag to false
			SavePreloadList (preloadVnfListBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);
			log.info("Updating OPERATIONAL tree.");
			SavePreloadList (preloadVnfListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] " + responseBuilder.build());
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(false).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] ");
		log.info("Returned SUCCESS for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] " + responseBuilder.build());

		RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

    //1610 preload-vnf-instance-topology-operation
	@Override
	public Future<RpcResult<PreloadVnfInstanceTopologyOperationOutput>> preloadVnfInstanceTopologyOperation(
			PreloadVnfInstanceTopologyOperationInput input) {

		final String SVC_OPERATION = "preload-vnf-instance-topology-operation";
		VnfInstancePreloadData vnfInstancePreloadData = null;
		Properties parms = new Properties();

		log.info( SVC_OPERATION +" called." );
		// create a new response object
		PreloadVnfInstanceTopologyOperationOutputBuilder responseBuilder = new PreloadVnfInstanceTopologyOperationOutputBuilder();

		// Result from savePreloadData
		final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

		if(input == null ||
            input.getVnfInstanceTopologyInformation() == null ||
                input.getVnfInstanceTopologyInformation().getVnfInstanceIdentifiers().getVnfInstanceName() == null ||
                    input.getVnfInstanceTopologyInformation().getVnfInstanceIdentifiers().getVnfModelId() == null)
        {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty vnf-instance-name and vnf-model-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-instance-name and vnf-model-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVnfInstanceTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the name and type from the input buffer
		String preload_name = input.getVnfInstanceTopologyInformation().getVnfInstanceIdentifiers().getVnfInstanceName();
		String preload_type = input.getVnfInstanceTopologyInformation().getVnfInstanceIdentifiers().getVnfModelId();

		// Make sure we have a preload_name and preload_type
		if(preload_name == null || preload_name.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid preload-name");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, invalid preload-name");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVnfInstanceTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}
		if(preload_type == null || preload_type.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid preload-type");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, invalid preload-type");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVnfInstanceTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		VnfInstancePreloadDataBuilder vnfInstancePreloadDataBuilder = new VnfInstancePreloadDataBuilder();
		getVnfInstancePreloadData(preload_name, preload_type, vnfInstancePreloadDataBuilder);
		//preloadData = preloadDataBuilder.build();

		VnfInstancePreloadDataBuilder operDataBuilder = new VnfInstancePreloadDataBuilder();
		getVnfInstancePreloadData(preload_name, preload_type, operDataBuilder, LogicalDatastoreType.OPERATIONAL );

		//
		// setup a preload-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		//  USES sdnc-request-header;
		//  USES request-information;
        //  uses vnf-topology-information;
		// OUTPUT:
		//  USES vnf-topology-response-body;
		//
		// container preload-data
        //   uses vnf-configuration-information;


		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] input: " + input);
		PreloadVnfInstanceTopologyOperationInputBuilder inputBuilder = new PreloadVnfInstanceTopologyOperationInputBuilder(input);
		VnfSdnUtil.toProperties(parms, inputBuilder.build());
		log.info("Adding OPERATIONAL data for "+SVC_OPERATION+" ["+preload_name+","+preload_type +"] operational-data: " + operDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "operational-data", operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		VNFSDNSvcLogicServiceClient svcLogicClient = new VNFSDNSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";


		try
		{
			if (svcLogicClient.hasGraph("VNF-API", SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute("VNF-API", SVC_OPERATION, null, "sync", vnfInstancePreloadDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for VNF-API: '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
			// internalError = respProps.getProperty("internal-error", "false");
		}

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {

			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			VnfInstancePreloadListBuilder vnfInstancePreloadListBuilder = new VnfInstancePreloadListBuilder();
			vnfInstancePreloadListBuilder.setVnfInstanceName(preload_name);
			vnfInstancePreloadListBuilder.setVnfModelId(preload_type);
			vnfInstancePreloadListBuilder.setVnfInstancePreloadData(vnfInstancePreloadDataBuilder.build());
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] error code: '" + errorCode + "', Reason: '" + errorMessage + "'");
			try {
				SaveVnfInstancePreloadList (vnfInstancePreloadListBuilder.build(), true,LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);
			}
			log.debug("Sending Success rpc result due to external error");
			RpcResult<PreloadVnfInstanceTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			vnfInstancePreloadData = vnfInstancePreloadDataBuilder.build();
			log.info("Updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preloadData: " + vnfInstancePreloadData);
			// svc-configuration-list
			VnfInstancePreloadListBuilder vnfInstancePreloadListBuilder = new VnfInstancePreloadListBuilder();
			vnfInstancePreloadListBuilder.setVnfInstanceName(preload_name);
			vnfInstancePreloadListBuilder.setVnfModelId(preload_type);
			vnfInstancePreloadListBuilder.setVnfInstancePreloadData(vnfInstancePreloadData);

			// SDNGC-989 set merge flag to false
			SaveVnfInstancePreloadList (vnfInstancePreloadListBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);
			log.info("Updating OPERATIONAL tree.");
			SaveVnfInstancePreloadList (vnfInstancePreloadListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] " + responseBuilder.build());
			RpcResult<PreloadVnfInstanceTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfInstanceTopologyOperationOutput> status(false).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] ");
		log.info("Returned SUCCESS for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] " + responseBuilder.build());

		RpcResult<PreloadVnfInstanceTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVnfInstanceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}


    //1610 preload-vf-module-topology-operation
	@Override
	public Future<RpcResult<PreloadVfModuleTopologyOperationOutput>> preloadVfModuleTopologyOperation(
			PreloadVfModuleTopologyOperationInput input) {

		final String SVC_OPERATION = "preload-vf-module-topology-operation";
		VfModulePreloadData vfModulePreloadData = null;
		Properties parms = new Properties();

		log.info( SVC_OPERATION +" called." );
		// create a new response object
		PreloadVfModuleTopologyOperationOutputBuilder responseBuilder = new PreloadVfModuleTopologyOperationOutputBuilder();

		// Result from savePreloadData
		final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

		if(input == null ||
            input.getVfModuleTopologyInformation() == null ||
                input.getVfModuleTopologyInformation().getVfModuleIdentifiers().getVfModuleName() == null ||
                    input.getVfModuleTopologyInformation().getVfModuleIdentifiers().getVfModuleModelId() == null)
        {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty vnf-instance-name and vnf-model-id");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-instance-name and vnf-model-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVfModuleTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the name and type from the input buffer
		String preload_name = input.getVfModuleTopologyInformation().getVfModuleIdentifiers().getVfModuleName();
		String preload_type = input.getVfModuleTopologyInformation().getVfModuleIdentifiers().getVfModuleModelId();

		// Make sure we have a preload_name and preload_type
		if(preload_name == null || preload_name.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid preload-name");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, invalid preload-name");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVfModuleTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}
		if(preload_type == null || preload_type.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid preload-type");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, invalid preload-type");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVfModuleTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		VfModulePreloadDataBuilder vfModulePreloadDataBuilder = new VfModulePreloadDataBuilder();
		getVfModulePreloadData(preload_name, preload_type, vfModulePreloadDataBuilder);
		//preloadData = preloadDataBuilder.build();

		VfModulePreloadDataBuilder operDataBuilder = new VfModulePreloadDataBuilder();
		getVfModulePreloadData(preload_name, preload_type, operDataBuilder, LogicalDatastoreType.OPERATIONAL );

		//
		// setup a preload-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		//  USES sdnc-request-header;
		//  USES request-information;
        //  uses vnf-topology-information;
		// OUTPUT:
		//  USES vnf-topology-response-body;
		//
		// container preload-data
        //   uses vnf-configuration-information;


		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] input: " + input);
		PreloadVfModuleTopologyOperationInputBuilder inputBuilder = new PreloadVfModuleTopologyOperationInputBuilder(input);
		VnfSdnUtil.toProperties(parms, inputBuilder.build());
		log.info("Adding OPERATIONAL data for "+SVC_OPERATION+" ["+preload_name+","+preload_type +"] operational-data: " + operDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "operational-data", operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		VNFSDNSvcLogicServiceClient svcLogicClient = new VNFSDNSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";


		try
		{
			if (svcLogicClient.hasGraph("VNF-API", SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute("VNF-API", SVC_OPERATION, null, "sync", vfModulePreloadDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for VNF-API: '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
			// internalError = respProps.getProperty("internal-error", "false");
		}

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {

			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			VfModulePreloadListBuilder vfModulePreloadListBuilder = new VfModulePreloadListBuilder();
			vfModulePreloadListBuilder.setVfModuleName(preload_name);
			vfModulePreloadListBuilder.setVfModuleModelId(preload_type);
			vfModulePreloadListBuilder.setVfModulePreloadData(vfModulePreloadDataBuilder.build());
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] error code: '" + errorCode + "', Reason: '" + errorMessage + "'");
			try {
				SaveVfModulePreloadList (vfModulePreloadListBuilder.build(), true,LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);
			}
			log.debug("Sending Success rpc result due to external error");
			RpcResult<PreloadVfModuleTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			vfModulePreloadData = vfModulePreloadDataBuilder.build();
			log.info("Updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preloadData: " + vfModulePreloadData);
			// svc-configuration-list
			VfModulePreloadListBuilder vfModulePreloadListBuilder = new VfModulePreloadListBuilder();
			vfModulePreloadListBuilder.setVfModuleName(preload_name);
			vfModulePreloadListBuilder.setVfModuleModelId(preload_type);
			vfModulePreloadListBuilder.setVfModulePreloadData(vfModulePreloadData);

			// SDNGC-989 set merge flag to false
			SaveVfModulePreloadList (vfModulePreloadListBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);
			log.info("Updating OPERATIONAL tree.");
			SaveVfModulePreloadList (vfModulePreloadListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] " + responseBuilder.build());
			RpcResult<PreloadVfModuleTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVfModuleTopologyOperationOutput> status(false).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] ");
		log.info("Returned SUCCESS for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] " + responseBuilder.build());

		RpcResult<PreloadVfModuleTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadVfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}


	@Override
	public Future<RpcResult<PreloadNetworkTopologyOperationOutput>> preloadNetworkTopologyOperation(
			PreloadNetworkTopologyOperationInput input) {

		final String SVC_OPERATION = "preload-network-topology-operation";
		PreloadData preloadData = null;
		Properties parms = new Properties();

		log.info( SVC_OPERATION +" called." );
		// create a new response object
		PreloadNetworkTopologyOperationOutputBuilder responseBuilder = new PreloadNetworkTopologyOperationOutputBuilder();

		// Result from savePreloadData
		final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

		if(input == null || input.getNetworkTopologyInformation() == null || input.getNetworkTopologyInformation().getNetworkTopologyIdentifier() == null || input.getNetworkTopologyInformation().getNetworkTopologyIdentifier().getNetworkName() == null || input.getNetworkTopologyInformation().getNetworkTopologyIdentifier().getNetworkType() == null) {
			log.debug("exiting " +SVC_OPERATION+ " because of invalid input, null or empty vnf-name and vnf-type");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("input, null or empty vnf-name and vnf-type");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
                                RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
                        return Futures.immediateFuture(rpcResult);
		}

		// Grab the name and type from the input buffer
		String preload_name = input.getNetworkTopologyInformation().getNetworkTopologyIdentifier().getNetworkName();
		String preload_type = input.getNetworkTopologyInformation().getNetworkTopologyIdentifier().getNetworkType();

		// Make sure we have a preload_name and preload_type
		if(preload_name == null || preload_name.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid preload-name");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("input, invalid preload-name");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
                                RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
                        return Futures.immediateFuture(rpcResult);
		}
		if(preload_type == null || preload_type.length() == 0 ) {
			log.debug("exiting "+SVC_OPERATION+" because of invalid preload-type");
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("input, invalid preload-type");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
                                RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
                        return Futures.immediateFuture(rpcResult);
		}

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, preloadDataBuilder);

		PreloadDataBuilder operDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, operDataBuilder, LogicalDatastoreType.OPERATIONAL );

		//
		// setup a preload-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		//  USES sdnc-request-header;
		//  USES request-information;
        //  uses vnf-topology-information;
		// OUTPUT:
		//  USES vnf-topology-response-body;
		//
		// container preload-data
        //   uses vnf-configuration-information;


		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] input: " + input);
		PreloadNetworkTopologyOperationInputBuilder inputBuilder = new PreloadNetworkTopologyOperationInputBuilder(input);
		VnfSdnUtil.toProperties(parms, inputBuilder.build());
		log.info("Adding OPERATIONAL data for "+SVC_OPERATION+" ["+preload_name+","+preload_type +"] operational-data: " + operDataBuilder.build());
		VnfSdnUtil.toProperties(parms, "operational-data", operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		VNFSDNSvcLogicServiceClient svcLogicClient = new VNFSDNSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";


		try
		{
			if (svcLogicClient.hasGraph("VNF-API", SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute("VNF-API", SVC_OPERATION, null, "sync", preloadDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for VNF-API: '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
			// internalError = respProps.getProperty("internal-error", "false");
		}

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {

			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
			preloadVnfListBuilder.setVnfName(preload_name);
			preloadVnfListBuilder.setVnfType(preload_type);
			preloadVnfListBuilder.setPreloadData(preloadDataBuilder.build());
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] error code: '" + errorCode + "', Reason: '" + errorMessage + "'");
			try {
				SavePreloadList (preloadVnfListBuilder.build(),true,LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);

			}
			log.debug("Sending Success rpc result due to external error");
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			preloadData = preloadDataBuilder.build();
			log.info("Updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preloadData: " + preloadData);
			// svc-configuration-list
			VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
			preloadVnfListBuilder.setVnfName(preload_name);
			preloadVnfListBuilder.setVnfType(preload_type);
			preloadVnfListBuilder.setPreloadData(preloadData);

			// SDNGC-989 set merge flag to false
			SavePreloadList (preloadVnfListBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);
			log.info("Updating OPERATIONAL tree.");
			SavePreloadList (preloadVnfListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] " + responseBuilder.build());
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(false).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] ");
		log.info("Returned SUCCESS for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] " + responseBuilder.build());

		RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
				RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}
}
