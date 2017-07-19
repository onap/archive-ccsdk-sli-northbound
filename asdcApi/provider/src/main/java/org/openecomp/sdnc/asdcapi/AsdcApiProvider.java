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

package org.openecomp.sdnc.asdcapi;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.http.xmlns.openecomp.org.asdc.license.model._1._0.rev160427.vf.license.model.grouping.VfLicenseModel;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.ASDCAPIService;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.Artifacts;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.ArtifactsBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.VfLicenseModelUpdateInput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.VfLicenseModelUpdateInputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.VfLicenseModelUpdateOutput;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.VfLicenseModelUpdateOutputBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.VfLicenseModelVersions;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.VfLicenseModelVersionsBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.artifacts.Artifact;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.artifacts.ArtifactBuilder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.artifacts.ArtifactKey;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.vf.license.model.versions.VfLicenseModelVersion;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.rev170201.vf.license.model.versions.VfLicenseModelVersionBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines a base implementation for your provider. This class extends from a helper class
 * which provides storage for the most commonly used components of the MD-SAL. Additionally the
 * base class provides some basic logging and initialization / clean up methods.
 *
 * To use this, copy and paste (overwrite) the following method into the TestApplicationProviderModule
 * class which is auto generated under src/main/java in this project
 * (created only once during first compilation):
 *
 * <pre>

    @Override
    public java.lang.AutoCloseable createInstance() {

         final asdcApiProvider provider = new asdcApiProvider();
         provider.setDataBroker( getDataBrokerDependency() );
         provider.setNotificationService( getNotificationServiceDependency() );
         provider.setRpcRegistry( getRpcRegistryDependency() );
         provider.initialize();
         return new AutoCloseable() {

            @Override
            public void close() throws Exception {
                //TODO: CLOSE ANY REGISTRATION OBJECTS CREATED USING ABOVE BROKER/NOTIFICATION
                //SERVIE/RPC REGISTRY
                provider.close();
            }
        };
    }


    </pre>
 */
public class AsdcApiProvider implements AutoCloseable, ASDCAPIService {

    private static final String ACTIVE_VERSION = "active";

    private final Logger log = LoggerFactory.getLogger( AsdcApiProvider.class );
    private final String appName = "asdcApi";

    private final ExecutorService executor;
    protected DataBroker dataBroker;
    protected NotificationProviderService notificationService;
    protected RpcProviderRegistry rpcRegistry;

    protected BindingAwareBroker.RpcRegistration<ASDCAPIService> rpcRegistration;

    public AsdcApiProvider(DataBroker dataBroker2,
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


        createContainers();

        if (rpcRegistration == null) {
            if (rpcRegistry != null) {
                rpcRegistration = rpcRegistry.addRpcImplementation(
                        ASDCAPIService.class, this);
                log.info("Initialization complete for " + appName);
            } else {
                log.warn("Error initializing " + appName
                        + " : rpcRegistry unset");
            }
        }
    }

    private void createContainers() {

        if (dataBroker != null) {
        final WriteTransaction t = dataBroker.newReadWriteTransaction();

        // Create the vf-model-license-versions and artifacts containers
        t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(VfLicenseModelVersions.class),
        new VfLicenseModelVersionsBuilder().build());

        t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(Artifacts.class), new ArtifactsBuilder().build());


        try {
            CheckedFuture<Void, TransactionCommitFailedException> checkedFuture = t.submit();
            checkedFuture.get();
            log.info("Create Containers succeeded!: ");

        } catch (InterruptedException | ExecutionException e) {
            log.error("Create Containers Failed: " + e);
            e.printStackTrace();
        }
        } else {
            log.warn("createContainers : cannot find dataBroker to create containers");
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

        rpcRegistration = rpcRegistry.addRpcImplementation(ASDCAPIService.class, this);

        if( log.isDebugEnabled() ){
            log.debug( "RpcRegistry set to " + (rpcRegistry==null?"null":"non-null") + "." );
        }
    }


    protected boolean artifactVersionExists(String aName, String aVersion) {
        InstanceIdentifier artifactInstanceId =
                InstanceIdentifier.<Artifacts>builder(Artifacts.class)
                .child(Artifact.class, new ArtifactKey(aName, aVersion)).toInstance();
        ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
        Optional<Artifact> data = null;
        try {
            data = (Optional<Artifact>) readTx.read(LogicalDatastoreType.CONFIGURATION, artifactInstanceId).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Caught Exception reading MD-SAL for ["+aName+","+ aVersion+"] " ,e);
            return false;

        }

        if (data.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    protected void addArtifactVersion(String aName, String aVersion) {


        try {
            ArtifactBuilder aBuilder = new ArtifactBuilder();

            aBuilder.setArtifactName(aName);
            aBuilder.setArtifactVersion(aVersion);

            Artifact artifact = aBuilder.build();

            InstanceIdentifier.InstanceIdentifierBuilder<Artifact> aIdBuilder = InstanceIdentifier
                    .<Artifacts> builder(Artifacts.class)
                    .child(Artifact.class, artifact.getKey());

            InstanceIdentifier<Artifact> path = aIdBuilder
                    .toInstance();

            WriteTransaction tx = dataBroker.newWriteOnlyTransaction();

            tx.merge(LogicalDatastoreType.CONFIGURATION, path,
                    artifact);
            tx.submit().checkedGet();
        } catch (Exception e) {
            log.error("Caught exception trying to add artifact entry", e);
        }

    }


    private void applyVfLicenseModelUpdate(VfLicenseModelUpdateInput input) {

    String aName = input.getArtifactName();
    String aVersion = input.getArtifactVersion();
    VfLicenseModel vfLicenseModel = input.getVfLicenseModel();


    // Add new version (version = artifact-version)
    try {

        VfLicenseModelVersionBuilder vBuilder = new VfLicenseModelVersionBuilder();
        vBuilder.setArtifactName(aName);
        vBuilder.setArtifactVersion(aVersion);
        vBuilder.setVfLicenseModel(vfLicenseModel);

        VfLicenseModelVersion version = vBuilder.build();

        InstanceIdentifier.InstanceIdentifierBuilder<VfLicenseModelVersion> versionIdBuilder = InstanceIdentifier
                .<VfLicenseModelVersions> builder(VfLicenseModelVersions.class)
                .child(VfLicenseModelVersion.class, version.getKey());

        InstanceIdentifier<VfLicenseModelVersion> path = versionIdBuilder
                .toInstance();

        WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
  tx.merge(LogicalDatastoreType.CONFIGURATION, path,
                version);
        tx.submit().checkedGet();
    } catch (Exception e) {
        log.error(
                "Caught exception trying to save entry to MD-SAL",
                e);
    }


    // Add "active" version (version = "active")
    try {

        VfLicenseModelVersionBuilder vBuilder = new VfLicenseModelVersionBuilder();
        vBuilder.setArtifactName(aName);
        vBuilder.setArtifactVersion(ACTIVE_VERSION);
        vBuilder.setVfLicenseModel(vfLicenseModel);

        VfLicenseModelVersion version = vBuilder.build();
        InstanceIdentifier.InstanceIdentifierBuilder<VfLicenseModelVersion> versionIdBuilder = InstanceIdentifier
                .<VfLicenseModelVersions> builder(VfLicenseModelVersions.class)
                .child(VfLicenseModelVersion.class, version.getKey());

        InstanceIdentifier<VfLicenseModelVersion> path = versionIdBuilder
                .toInstance();

        WriteTransaction tx = dataBroker.newWriteOnlyTransaction();

        tx.merge(LogicalDatastoreType.CONFIGURATION, path,
                version);
        tx.submit().checkedGet();
    } catch (Exception e) {
        log.error(
                "Caught exception trying to save entry to MD-SAL",
                e);
    }

}

@Override
public Future<RpcResult<VfLicenseModelUpdateOutput>> vfLicenseModelUpdate(VfLicenseModelUpdateInput input) {
    final String SVC_OPERATION = "vf-license-model-update";

    Properties parms = new Properties();

    log.info( SVC_OPERATION +" called." );

    if(input == null ) {
        log.debug("exiting " +SVC_OPERATION+ " because of invalid input");
        return null;
    }

    VfLicenseModelUpdateInputBuilder inputBuilder = new VfLicenseModelUpdateInputBuilder(input);
    input = inputBuilder.build();

    String errorMessage = "Success";
    String errorCode = "200";

    // If this artifact already exists, reject this update
    if (artifactVersionExists(input.getArtifactName(), input.getArtifactVersion())) {
        errorCode = "409";
        errorMessage = "Artifact version already exists";
    } else {
        // Translate input object into SLI-consumable properties
        log.info("Adding INPUT data for "+SVC_OPERATION+" input: " + input);
        AsdcApiUtil.toProperties(parms, input);


        // Call directed graph

        Properties respProps = null;


        AsdcApiSliClient sliClient = new AsdcApiSliClient();
        try
        {
            if (sliClient.hasGraph("ASDC-API", SVC_OPERATION , null, "sync"))
            {

                try
                {
                    respProps = sliClient.execute("ASDC-API", SVC_OPERATION, null, "sync", parms);
                }
                catch (Exception e)
                {
                    log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
                }
            } else {
                errorMessage = "No service logic active for ASDC-API: '" + SVC_OPERATION + "'";
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
            errorMessage = respProps.getProperty("error-message", "");
        }
    }


    if ("200".equals(errorCode)) {
        log.info("ASDC update succeeded");

        // Update config tree
        applyVfLicenseModelUpdate(input);
        addArtifactVersion(input.getArtifactName(), input.getArtifactVersion());

    } else {
        log.info("ASDC update failed ("+errorCode+" : "+errorMessage);
    }

    // Send response
    VfLicenseModelUpdateOutputBuilder respBuilder = new VfLicenseModelUpdateOutputBuilder();
    respBuilder.setAsdcApiResponseCode(errorCode);
    if (errorMessage != null && errorMessage.length() > 0) {
        respBuilder.setAsdcApiResponseText(errorMessage);
    }

    RpcResult<VfLicenseModelUpdateOutput> rpcResult;


    rpcResult = RpcResultBuilder.<VfLicenseModelUpdateOutput> status(true).withResult(respBuilder.build()).build();



    return Futures.immediateFuture(rpcResult);
}


}
