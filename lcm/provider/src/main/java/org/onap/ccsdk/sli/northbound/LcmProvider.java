package org.onap.ccsdk.sli.northbound;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.onap.ccsdk.sli.core.sli.provider.MdsalHelper;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.impl.AbstractForwardedDataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.md.sal.dom.api.DOMDataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.*;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeaderBuilder;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.Status;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.StatusBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;

import org.onap.ccsdk.sli.northbound.LcmResponseCode.*;

/**
 * Defines a base implementation for your provider. This class extends from a
 * helper class which provides storage for the most commonly used components of
 * the MD-SAL. Additionally the base class provides some basic logging and
 * initialization / clean up methods.
 *
 */
public class LcmProvider implements AutoCloseable, LCMService {

	private class CommonLcmFields {
		private StatusBuilder statusBuilder;
		private CommonHeaderBuilder commonHeaderBuilder;

		public CommonLcmFields(StatusBuilder statusBuilder, CommonHeaderBuilder commonHeaderBuilder) {
			this.statusBuilder = statusBuilder;
			this.commonHeaderBuilder = commonHeaderBuilder;
		}

		public StatusBuilder getStatusBuilder() {
			return statusBuilder;
		}

		public CommonHeaderBuilder getCommonHeaderBuilder() {
			return commonHeaderBuilder;
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(LcmProvider.class);

	private static final String ACTIVE_VERSION = "active";

	private static final String APPLICATION_NAME = "LCM";

	private final ExecutorService executor;
	protected DataBroker dataBroker;
	protected DOMDataBroker domDataBroker;
	protected NotificationPublishService notificationService;
	protected RpcProviderRegistry rpcRegistry;
	private final LcmSliClient lcmSliClient;

	protected BindingAwareBroker.RpcRegistration<LCMService> rpcRegistration;

	public LcmProvider(final DataBroker dataBroker, final NotificationPublishService notificationPublishService,
			final RpcProviderRegistry rpcProviderRegistry, final LcmSliClient lcmSliClient) {

		LOG.info("Creating provider for {}", APPLICATION_NAME);
		executor = Executors.newFixedThreadPool(1);
		this.dataBroker = dataBroker;
		if (dataBroker instanceof AbstractForwardedDataBroker) {
			domDataBroker = ((AbstractForwardedDataBroker) dataBroker).getDelegate();
		}
		notificationService = notificationPublishService;
		rpcRegistry = rpcProviderRegistry;
		this.lcmSliClient = lcmSliClient;
		initialize();
	}

	public void initialize() {
		LOG.info("Initializing {} for {}", this.getClass().getName(), APPLICATION_NAME);

		if (rpcRegistration == null) {
			if (rpcRegistry != null) {
				rpcRegistration = rpcRegistry.addRpcImplementation(LCMService.class, this);
				LOG.info("Initialization complete for {}", APPLICATION_NAME);
			} else {
				LOG.warn("Error initializing {} : rpcRegistry unset", APPLICATION_NAME);
			}
		}
	}

	protected void initializeChild() {
		// Override if you have custom initialization intelligence
	}

	@Override
	public void close() throws Exception {
		LOG.info("Closing provider for " + APPLICATION_NAME);
		executor.shutdown();
		rpcRegistration.close();
		LOG.info("Successfully closed provider for " + APPLICATION_NAME);
	}



	@Override
	public Future<RpcResult<CheckLockOutput>> checkLock(CheckLockInput input) {
		CheckLockInputBuilder iBuilder = new CheckLockInputBuilder(input);
		CheckLockOutputBuilder oBuilder = new CheckLockOutputBuilder();

		try {
			CommonLcmFields retval = callDG("CheckLock", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<CheckLockOutput> rpcResult =
				RpcResultBuilder.<CheckLockOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);

	}

	@Override
	public Future<RpcResult<RebootOutput>> reboot(RebootInput input) {
		RebootInputBuilder iBuilder = new RebootInputBuilder(input);
		RebootOutputBuilder oBuilder = new RebootOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Reboot", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<RebootOutput> rpcResult =
				RpcResultBuilder.<RebootOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<UpgradeBackupOutput>> upgradeBackup(UpgradeBackupInput input) {
		UpgradeBackupInputBuilder iBuilder = new UpgradeBackupInputBuilder(input);
		UpgradeBackupOutputBuilder oBuilder = new UpgradeBackupOutputBuilder();

		try {
			CommonLcmFields retval = callDG("UpgradeBackup", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());

		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<UpgradeBackupOutput> rpcResult =
				RpcResultBuilder.<UpgradeBackupOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<RollbackOutput>> rollback(RollbackInput input) {
		RollbackInputBuilder iBuilder = new RollbackInputBuilder(input);
		RollbackOutputBuilder oBuilder = new RollbackOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Rollback", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<RollbackOutput> rpcResult =
				RpcResultBuilder.<RollbackOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<SyncOutput>> sync(SyncInput input) {
		SyncInputBuilder iBuilder = new SyncInputBuilder(input);
		SyncOutputBuilder oBuilder = new SyncOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Sync", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<SyncOutput> rpcResult =
				RpcResultBuilder.<SyncOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<QueryOutput>> query(QueryInput input) {
		QueryInputBuilder iBuilder = new QueryInputBuilder(input);
		QueryOutputBuilder oBuilder = new QueryOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Query", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<QueryOutput> rpcResult =
				RpcResultBuilder.<QueryOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ConfigExportOutput>> configExport(ConfigExportInput input) {
		ConfigExportInputBuilder iBuilder = new ConfigExportInputBuilder(input);
		ConfigExportOutputBuilder oBuilder = new ConfigExportOutputBuilder();

		try {
			CommonLcmFields retval = callDG("ConfigExport", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ConfigExportOutput> rpcResult =
				RpcResultBuilder.<ConfigExportOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<StopApplicationOutput>> stopApplication(StopApplicationInput input) {
		StopApplicationInputBuilder iBuilder = new StopApplicationInputBuilder(input);
		StopApplicationOutputBuilder oBuilder = new StopApplicationOutputBuilder();

		try {
			CommonLcmFields retval = callDG("StopApplication", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<StopApplicationOutput> rpcResult =
				RpcResultBuilder.<StopApplicationOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<SoftwareUploadOutput>> softwareUpload(SoftwareUploadInput input) {
		SoftwareUploadInputBuilder iBuilder = new SoftwareUploadInputBuilder(input);
		SoftwareUploadOutputBuilder oBuilder = new SoftwareUploadOutputBuilder();

		try {
			CommonLcmFields retval = callDG("SoftwareUpload", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<SoftwareUploadOutput> rpcResult =
				RpcResultBuilder.<SoftwareUploadOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ResumeTrafficOutput>> resumeTraffic(ResumeTrafficInput input) {
		ResumeTrafficInputBuilder iBuilder = new ResumeTrafficInputBuilder(input);
		ResumeTrafficOutputBuilder oBuilder = new ResumeTrafficOutputBuilder();

		try {
			CommonLcmFields retval = callDG("ResumeTraffic", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ResumeTrafficOutput> rpcResult =
				RpcResultBuilder.<ResumeTrafficOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ConfigureOutput>> configure(ConfigureInput input) {
		ConfigureInputBuilder iBuilder = new ConfigureInputBuilder(input);
		ConfigureOutputBuilder oBuilder = new ConfigureOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Configure", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ConfigureOutput> rpcResult =
				RpcResultBuilder.<ConfigureOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ActionStatusOutput>> actionStatus(ActionStatusInput input) {
		ActionStatusInputBuilder iBuilder = new ActionStatusInputBuilder(input);
		ActionStatusOutputBuilder oBuilder = new ActionStatusOutputBuilder();

		try {
			CommonLcmFields retval = callDG("ActionStatus", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ActionStatusOutput> rpcResult =
				RpcResultBuilder.<ActionStatusOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<UpgradePreCheckOutput>> upgradePreCheck(UpgradePreCheckInput input) {
		UpgradePreCheckInputBuilder iBuilder = new UpgradePreCheckInputBuilder(input);
		UpgradePreCheckOutputBuilder oBuilder = new UpgradePreCheckOutputBuilder();

		try {
			CommonLcmFields retval = callDG("UpgradePreCheck", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<UpgradePreCheckOutput> rpcResult =
				RpcResultBuilder.<UpgradePreCheckOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<LiveUpgradeOutput>> liveUpgrade(LiveUpgradeInput input) {
		LiveUpgradeInputBuilder iBuilder = new LiveUpgradeInputBuilder(input);
		LiveUpgradeOutputBuilder oBuilder = new LiveUpgradeOutputBuilder();

		try {
			CommonLcmFields retval = callDG("LiveUpgrade", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<LiveUpgradeOutput> rpcResult =
				RpcResultBuilder.<LiveUpgradeOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ConfigModifyOutput>> configModify(ConfigModifyInput input) {
		ConfigModifyInputBuilder iBuilder = new ConfigModifyInputBuilder(input);
		ConfigModifyOutputBuilder oBuilder = new ConfigModifyOutputBuilder();

		try {
			CommonLcmFields retval = callDG("ConfigModify", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ConfigModifyOutput> rpcResult =
				RpcResultBuilder.<ConfigModifyOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<RestartOutput>> restart(RestartInput input) {
		RestartInputBuilder iBuilder = new RestartInputBuilder(input);
		RestartOutputBuilder oBuilder = new RestartOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Restart", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<RestartOutput> rpcResult =
				RpcResultBuilder.<RestartOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<HealthCheckOutput>> healthCheck(HealthCheckInput input) {
		HealthCheckInputBuilder iBuilder = new HealthCheckInputBuilder(input);
		HealthCheckOutputBuilder oBuilder = new HealthCheckOutputBuilder();

		try {
			CommonLcmFields retval = callDG("HealthCheck", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<HealthCheckOutput> rpcResult =
				RpcResultBuilder.<HealthCheckOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<LockOutput>> lock(LockInput input) {
		LockInputBuilder iBuilder = new LockInputBuilder(input);
		LockOutputBuilder oBuilder = new LockOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Lock", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<LockOutput> rpcResult =
				RpcResultBuilder.<LockOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<TerminateOutput>> terminate(TerminateInput input) {
		TerminateInputBuilder iBuilder = new TerminateInputBuilder(input);
		TerminateOutputBuilder oBuilder = new TerminateOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Terminate", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<TerminateOutput> rpcResult =
				RpcResultBuilder.<TerminateOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<AttachVolumeOutput>> attachVolume(AttachVolumeInput input) {
		AttachVolumeInputBuilder iBuilder = new AttachVolumeInputBuilder(input);
		AttachVolumeOutputBuilder oBuilder = new AttachVolumeOutputBuilder();

		try {
			CommonLcmFields retval = callDG("AttachVolume", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<AttachVolumeOutput> rpcResult =
				RpcResultBuilder.<AttachVolumeOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<MigrateOutput>> migrate(MigrateInput input) {
		MigrateInputBuilder iBuilder = new MigrateInputBuilder(input);
		MigrateOutputBuilder oBuilder = new MigrateOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Migrate", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<MigrateOutput> rpcResult =
				RpcResultBuilder.<MigrateOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<QuiesceTrafficOutput>> quiesceTraffic(QuiesceTrafficInput input) {
		QuiesceTrafficInputBuilder iBuilder = new QuiesceTrafficInputBuilder(input);
		QuiesceTrafficOutputBuilder oBuilder = new QuiesceTrafficOutputBuilder();

		try {
			CommonLcmFields retval = callDG("QuiesceTraffic", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<QuiesceTrafficOutput> rpcResult =
				RpcResultBuilder.<QuiesceTrafficOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ConfigRestoreOutput>> configRestore(ConfigRestoreInput input) {
		ConfigRestoreInputBuilder iBuilder = new ConfigRestoreInputBuilder(input);
		ConfigRestoreOutputBuilder oBuilder = new ConfigRestoreOutputBuilder();

		try {
			CommonLcmFields retval = callDG("ConfigRestore", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ConfigRestoreOutput> rpcResult =
				RpcResultBuilder.<ConfigRestoreOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<UpgradeBackoutOutput>> upgradeBackout(UpgradeBackoutInput input) {
		UpgradeBackoutInputBuilder iBuilder = new UpgradeBackoutInputBuilder(input);
		UpgradeBackoutOutputBuilder oBuilder = new UpgradeBackoutOutputBuilder();

		try {
			CommonLcmFields retval = callDG("UpgradeBackout", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<UpgradeBackoutOutput> rpcResult =
				RpcResultBuilder.<UpgradeBackoutOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<EvacuateOutput>> evacuate(EvacuateInput input) {
		EvacuateInputBuilder iBuilder = new EvacuateInputBuilder(input);
		EvacuateOutputBuilder oBuilder = new EvacuateOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Evacuate", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<EvacuateOutput> rpcResult =
				RpcResultBuilder.<EvacuateOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<UnlockOutput>> unlock(UnlockInput input) {
		UnlockInputBuilder iBuilder = new UnlockInputBuilder(input);
		UnlockOutputBuilder oBuilder = new UnlockOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Unlock", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<UnlockOutput> rpcResult =
				RpcResultBuilder.<UnlockOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ConfigBackupDeleteOutput>> configBackupDelete(ConfigBackupDeleteInput input) {
		ConfigBackupDeleteInputBuilder iBuilder = new ConfigBackupDeleteInputBuilder(input);
		ConfigBackupDeleteOutputBuilder oBuilder = new ConfigBackupDeleteOutputBuilder();

		try {
			CommonLcmFields retval = callDG("ConfigBackupDelete", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ConfigBackupDeleteOutput> rpcResult =
				RpcResultBuilder.<ConfigBackupDeleteOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<UpgradeSoftwareOutput>> upgradeSoftware(UpgradeSoftwareInput input) {
		UpgradeSoftwareInputBuilder iBuilder = new UpgradeSoftwareInputBuilder(input);
		UpgradeSoftwareOutputBuilder oBuilder = new UpgradeSoftwareOutputBuilder();

		try {
			CommonLcmFields retval = callDG("UpgradeSoftware", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<UpgradeSoftwareOutput> rpcResult =
				RpcResultBuilder.<UpgradeSoftwareOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<StopOutput>> stop(StopInput input) {
		StopInputBuilder iBuilder = new StopInputBuilder(input);
		StopOutputBuilder oBuilder = new StopOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Stop", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<StopOutput> rpcResult =
				RpcResultBuilder.<StopOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<DetachVolumeOutput>> detachVolume(DetachVolumeInput input) {
		DetachVolumeInputBuilder iBuilder = new DetachVolumeInputBuilder(input);
		DetachVolumeOutputBuilder oBuilder = new DetachVolumeOutputBuilder();

		try {
			CommonLcmFields retval = callDG("DetachVolume", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<DetachVolumeOutput> rpcResult =
				RpcResultBuilder.<DetachVolumeOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ConfigScaleOutOutput>> configScaleOut(ConfigScaleOutInput input) {
		ConfigScaleOutInputBuilder iBuilder = new ConfigScaleOutInputBuilder(input);
		ConfigScaleOutOutputBuilder oBuilder = new ConfigScaleOutOutputBuilder();

		try {
			CommonLcmFields retval = callDG("ConfigScaleOut", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ConfigScaleOutOutput> rpcResult =
				RpcResultBuilder.<ConfigScaleOutOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<UpgradePostCheckOutput>> upgradePostCheck(UpgradePostCheckInput input) {
		UpgradePostCheckInputBuilder iBuilder = new UpgradePostCheckInputBuilder(input);
		UpgradePostCheckOutputBuilder oBuilder = new UpgradePostCheckOutputBuilder();

		try {
			CommonLcmFields retval = callDG("UpgradePostCheck", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<UpgradePostCheckOutput> rpcResult =
				RpcResultBuilder.<UpgradePostCheckOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<TestOutput>> test(TestInput input) {
		TestInputBuilder iBuilder = new TestInputBuilder(input);
		TestOutputBuilder oBuilder = new TestOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Test", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<TestOutput> rpcResult =
				RpcResultBuilder.<TestOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<StartApplicationOutput>> startApplication(StartApplicationInput input) {
		StartApplicationInputBuilder iBuilder = new StartApplicationInputBuilder(input);
		StartApplicationOutputBuilder oBuilder = new StartApplicationOutputBuilder();

		try {
			CommonLcmFields retval = callDG("StartApplication", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<StartApplicationOutput> rpcResult =
				RpcResultBuilder.<StartApplicationOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ConfigBackupOutput>> configBackup(ConfigBackupInput input) {
		ConfigBackupInputBuilder iBuilder = new ConfigBackupInputBuilder(input);
		ConfigBackupOutputBuilder oBuilder = new ConfigBackupOutputBuilder();

		try {
			CommonLcmFields retval = callDG("ConfigBackup", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<ConfigBackupOutput> rpcResult =
				RpcResultBuilder.<ConfigBackupOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<RebuildOutput>> rebuild(RebuildInput input) {
		RebuildInputBuilder iBuilder = new RebuildInputBuilder(input);
		RebuildOutputBuilder oBuilder = new RebuildOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Rebuild", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<RebuildOutput> rpcResult =
				RpcResultBuilder.<RebuildOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<AuditOutput>> audit(AuditInput input) {
		AuditInputBuilder iBuilder = new AuditInputBuilder(input);
		AuditOutputBuilder oBuilder = new AuditOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Audit", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<AuditOutput> rpcResult =
				RpcResultBuilder.<AuditOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<StartOutput>> start(StartInput input) {
		StartInputBuilder iBuilder = new StartInputBuilder(input);
		StartOutputBuilder oBuilder = new StartOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Start", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<StartOutput> rpcResult =
				RpcResultBuilder.<StartOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<SnapshotOutput>> snapshot(SnapshotInput input) {
		SnapshotInputBuilder iBuilder = new SnapshotInputBuilder(input);
		SnapshotOutputBuilder oBuilder = new SnapshotOutputBuilder();

		try {
			CommonLcmFields retval = callDG("Snapshot", iBuilder.build());
			oBuilder.setStatus(retval.getStatusBuilder().build());
			oBuilder.setCommonHeader(retval.getCommonHeaderBuilder().build());
		} catch (LcmRpcInvocationException e) {
			LOG.debug("Caught exception", e);
			oBuilder.setCommonHeader(e.getCommonHeader());
			oBuilder.setStatus(e.getStatus());
		}

		RpcResult<SnapshotOutput> rpcResult =
				RpcResultBuilder.<SnapshotOutput> status(true).withResult(oBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	private CommonLcmFields callDG(String rpcName, Object input) throws LcmRpcInvocationException {

		StatusBuilder statusBuilder = new StatusBuilder();

		if (input == null) {
			LOG.debug("Rejecting " +rpcName+ " because of invalid input");
			statusBuilder.setCode(LcmResponseCode.REJECT_INVALID_INPUT.getValue());
			statusBuilder.setMessage("REJECT - INVALID INPUT.  Missing input");
			CommonHeaderBuilder hBuilder = new CommonHeaderBuilder();
			hBuilder.setApiVer("1");
			hBuilder.setOriginatorId("unknown");
			hBuilder.setRequestId("unset");
			hBuilder.setTimestamp(new ZULU(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date())));
			throw new LcmRpcInvocationException(statusBuilder.build(), hBuilder.build());
		}

		CommonHeaderBuilder hBuilder = new CommonHeaderBuilder(((CommonHeader)input).getCommonHeader());

		// add input to parms
		LOG.info("Adding INPUT data for "+ rpcName +" input: " + input.toString());
		Properties inputProps = new Properties();
		MdsalHelper.toProperties(inputProps, input);

		Properties respProps = new Properties();

		// Call SLI sync method
		try
		{
			if (lcmSliClient.hasGraph("LCM", rpcName , null, "sync"))
			{
				try
				{
					respProps = lcmSliClient.execute("LCM", rpcName, null, "sync", inputProps, domDataBroker);
				}
				catch (Exception e)
				{
					LOG.error("Caught exception executing service logic for "+ rpcName, e);
					statusBuilder.setCode(LcmResponseCode.FAILURE_DG_FAILURE.getValue());
					statusBuilder.setMessage("FAILURE - DG FAILURE ("+e.getMessage()+")");
					throw new LcmRpcInvocationException(statusBuilder.build(), hBuilder.build());
				}
			} else {
				LOG.error("No service logic active for LCM: '" + rpcName + "'");

				statusBuilder.setCode(LcmResponseCode.REJECT_DG_NOT_FOUND.getValue());
				statusBuilder.setMessage("FAILURE - DG not found for action "+rpcName);
				throw new LcmRpcInvocationException(statusBuilder.build(), hBuilder.build());
			}
		}
		catch (Exception e)
		{
			LOG.error("Caught exception looking for service logic", e);

			statusBuilder.setCode(LcmResponseCode.FAILURE_DG_FAILURE.getValue());
			statusBuilder.setMessage("FAILURE - Unexpected error looking for DG ("+e.getMessage()+")");
			throw new LcmRpcInvocationException(statusBuilder.build(), hBuilder.build());
		}


		StatusBuilder sBuilder = new StatusBuilder();
		MdsalHelper.toBuilder(respProps, sBuilder);
		MdsalHelper.toBuilder(respProps, hBuilder);

		String statusCode = sBuilder.getCode().toString();

		if (!"400".equals(statusCode)) {
			LOG.error("Returned FAILED for "+rpcName+" error code: '" + statusCode + "'");
		} else {
			LOG.info("Returned SUCCESS for "+rpcName+" ");
		}

		return new CommonLcmFields(sBuilder,hBuilder);

	}

}
