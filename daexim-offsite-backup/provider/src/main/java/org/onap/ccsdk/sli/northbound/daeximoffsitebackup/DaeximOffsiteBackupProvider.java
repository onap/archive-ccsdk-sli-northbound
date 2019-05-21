/*
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2018 AT&T Intellectual Property. All rights
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

package org.onap.ccsdk.sli.northbound.daeximoffsitebackup;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nonnull;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataTreeChangeListener;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.daeximoffsitebackup.rev180926.BackupDataInput;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.daeximoffsitebackup.rev180926.BackupDataOutput;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.daeximoffsitebackup.rev180926.BackupDataOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.daeximoffsitebackup.rev180926.DaeximOffsiteBackupService;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.daeximoffsitebackup.rev180926.RetrieveDataInput;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.daeximoffsitebackup.rev180926.RetrieveDataOutput;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.daeximoffsitebackup.rev180926.RetrieveDataOutputBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaeximOffsiteBackupProvider implements AutoCloseable, DaeximOffsiteBackupService, DataTreeChangeListener {

    private static final Logger LOG = LoggerFactory.getLogger(DaeximOffsiteBackupProvider.class);
    private static final String BACKUP_ARCHIVE = "odl_backup.zip";
    private static final String appName = "daexim-offsite-backup";
    private static final String PROPERTIES_FILE =
        System.getenv("SDNC_CONFIG_DIR") + "/daexim-offsite-backup.properties";
    private static String DAEXIM_DIR;
    private static String CREDENTIALS;
    private static String NEXUS_URL;
    private static String POD_NAME;
    private static String OPERATIONAL_JSON;
    private static String MODELS_JSON;
    private static String CONFIG_JSON;
    private final ExecutorService executor;
    private Properties properties;
    private DataBroker dataBroker;
    private RpcProviderRegistry rpcRegistry;
    private BindingAwareBroker.RpcRegistration<DaeximOffsiteBackupService> rpcRegistration;

    public DaeximOffsiteBackupProvider(DataBroker dataBroker, RpcProviderRegistry rpcProviderRegistry) {
        LOG.info("Creating provider for {}", appName);
        this.executor = Executors.newFixedThreadPool(1);
        this.dataBroker = dataBroker;
        this.rpcRegistry = rpcProviderRegistry;
        initialize();
    }

    public void initialize() {
        LOG.info("Initializing provider for {}", appName);
        // Create the top level containers
        createContainers();
        try {
            DaeximOffsiteBackupUtil.loadProperties();
        } catch (final Exception e) {
            LOG.error("Caught Exception while trying to load properties file", e);
        }
        rpcRegistration = rpcRegistry.addRpcImplementation(DaeximOffsiteBackupService.class, this);
        LOG.info("Initialization complete for {}", appName);
    }

    private void loadProperties() {
        LOG.info("Loading properties from {}", PROPERTIES_FILE);
        if (properties == null) {
            properties = new Properties();
        }
        final File propertiesFile = new File(PROPERTIES_FILE);
        if (!propertiesFile.exists()) {
            LOG.warn("Properties file (" + PROPERTIES_FILE + ") not found. Using default properties.");
            properties.put("daeximDirectory", "/opt/opendaylight/current/daexim/");
            properties.put("credentials", "admin:enc:YWRtaW4xMjM=");
            properties.put("nexusUrl", "http://localhost:8081/nexus/content/repositories/");
            properties.put("podName", "UNKNOWN_ODL");
            properties.put("file.operational", "odl_backup_operational.json");
            properties.put("file.models", "odl_backup_models.json");
            properties.put("file.config", "odl_backup_config.json");
            return;
        }

        try (final FileInputStream fileInputStream = new FileInputStream(propertiesFile);) {
            properties.load(fileInputStream);
            LOG.info(properties.size() + " properties loaded.");
            LOG.info("daeximDirectory: {}", properties.getProperty("daeximDirectory"));
            LOG.info("nexusUrl: {}", properties.getProperty("nexusUrl"));
            LOG.info("podName: {}", properties.getProperty("podName"));
            LOG.info("file.operational: {}", properties.getProperty("file.operational"));
            LOG.info("file.models: {}", properties.getProperty("file.models"));
            LOG.info("file.config: {}", properties.getProperty("file.config"));
        } catch (final IOException e) {
            LOG.error("Error loading properties.", e);
        }
    }

    private void applyProperties() {
        LOG.info("Applying properties...");
        if (POD_NAME == null || POD_NAME.isEmpty()) {
            LOG.warn("MY_POD_NAME environment variable not set. Using value from properties.");
            POD_NAME = properties.getProperty("podName");
        }
        DAEXIM_DIR = properties.getProperty("daeximDirectory");
        NEXUS_URL = properties.getProperty("nexusUrl");

        OPERATIONAL_JSON = properties.getProperty("file.operational");
        MODELS_JSON = properties.getProperty("file.models");
        CONFIG_JSON = properties.getProperty("file.config");

        if (!properties.getProperty("credentials").contains(":")) { //Entire thing is encoded
            CREDENTIALS = new String(Base64.getDecoder().decode(properties.getProperty("credentials")));
        } else {
            final String[] credentials = properties.getProperty("credentials").split(":", 2);
            if (credentials[1].startsWith("enc:")) { // Password is encoded
                credentials[1] = new String(Base64.getDecoder().decode(credentials[1].split(":")[1]));
            }
            CREDENTIALS = credentials[0] + ":" + credentials[1];
        }
        LOG.info("Properties applied.");
    }

    private void createContainers() {
        final WriteTransaction t = dataBroker.newReadWriteTransaction();
        try {
            CheckedFuture<Void, TransactionCommitFailedException> checkedFuture = t.submit();
            checkedFuture.get();
            LOG.info("Create Containers succeeded!: ");
        } catch (final InterruptedException | ExecutionException e) {
            LOG.error("Create Containers Failed: ", e);
            LOG.error("context", e);
        }
    }

    protected void initializeChild() {

    }

    @Override
    public void close() throws Exception {
        LOG.info("Closing provider for " + appName);
        executor.shutdown();
        rpcRegistration.close();
        LOG.info("Successfully closed provider for " + appName);
    }

    @Override
    public void onDataTreeChanged(@Nonnull final Collection changes) {

    }

    @Override
    public ListenableFuture<RpcResult<BackupDataOutput>> backupData(final BackupDataInput input) {
        final String SVC_OPERATION = "backup-data";
        LOG.info(appName + ":" + SVC_OPERATION + " called.");

        String statusCode;
        String message = "Data sent to offsite location.";

        loadProperties();
        applyProperties();

        LOG.info("Pod Name: {}", POD_NAME);
        final Instant timestamp = Instant.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HH").withZone(ZoneId.of("GMT"));
        final String timestampedArchive = DAEXIM_DIR + POD_NAME + '-' + formatter.format(timestamp) + "-" + BACKUP_ARCHIVE;
        try {
            LOG.info("Creating archive...");
            final List<String> daeximFiles =
                Arrays.asList(DAEXIM_DIR + OPERATIONAL_JSON, DAEXIM_DIR + MODELS_JSON, DAEXIM_DIR + CONFIG_JSON);
            createArchive(daeximFiles, timestampedArchive);
            LOG.info("Archive created.");
        } catch (final IOException e) {
            LOG.error("Error creating archive " + timestampedArchive, e);
            statusCode = "500";
            message = "Archive creation failed.";
            return buildBackupDataFuture(statusCode, message);
        }

        try {
            LOG.info("Sending archive to Nexus server: {}", NEXUS_URL);
            statusCode = Integer.toString(putArchive(timestampedArchive));
            LOG.info("Archive sent to Nexus.");
        } catch (final IOException e) {
            LOG.error("Nexus creation failed.", e);
            statusCode = "500";
            message = "Nexus creation failed.";
        }

        final File archive = new File(timestampedArchive);
        if (archive.exists()) {
            archive.delete(); // Save some space on the ODL, keep them from piling up
        }

        LOG.info("Sending Response statusCode=" + statusCode + " message=" + message + " | " + SVC_OPERATION);
        return buildBackupDataFuture(statusCode, message);
    }

    @Override
    public ListenableFuture<RpcResult<RetrieveDataOutput>> retrieveData(final RetrieveDataInput input) {
        final String SVC_OPERATION = "retrieve-data";
        LOG.info(appName + ":" + SVC_OPERATION + " called.");

        String statusCode = "200";
        String message = "Data retrieved from offsite location.";

        loadProperties();
        applyProperties();

        LOG.info("Pod Name: {}", POD_NAME);
        final String archiveIdentifier = POD_NAME + '-' + input.getTimestamp();
        final String timestampedArchive = DAEXIM_DIR + archiveIdentifier + "-" + BACKUP_ARCHIVE;
        LOG.info("Trying to retrieve {}", timestampedArchive);
        try {
            statusCode = Integer.toString(getArchive(archiveIdentifier));
        } catch (final IOException e) {
            LOG.error("Could not retrieve archive.", e);
            statusCode = "500";
            message = "Could not retrieve archive.";
            return retrieveDataOutputRpcResult(statusCode, message);
        }
        LOG.info("Retrieved archive.");

        LOG.info("Extracting archive...");
        try {
            extractArchive(DAEXIM_DIR + "-" + BACKUP_ARCHIVE);
        } catch (final IOException e) {
            LOG.error("Could not extract archive.", e);
            statusCode = "500";
            message = "Could not extract archive.";
            return retrieveDataOutputRpcResult(statusCode, message);
        }
        LOG.info("Archive extracted.");

        return retrieveDataOutputRpcResult(statusCode, message);
    }

    private boolean exportExists(final List<String> daeximFiles) {
        File file;
        for (final String f : daeximFiles) {
            file = new File(f);
            if (!file.exists()) {
                return false;
            }
        }
        return true;
    }

    private void createArchive(final List<String> daeximFiles, final String timestampedArchive) throws IOException {
        if (!exportExists(daeximFiles)) {
            LOG.error("Daexim exports do not exist.");
            throw new IOException();
        }
        LOG.info("Creating {}", timestampedArchive);
        try (final FileOutputStream fileOutputStream = new FileOutputStream(timestampedArchive);
            final ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);) {

            int length;
            final byte[] bytes = new byte[1024];
            for (final String source : daeximFiles) {
                LOG.info("Adding {} to archive...", source);
                final File targetZipFile = new File(source);

                final ZipEntry zipEntry = new ZipEntry(targetZipFile.getName());
                zipOutputStream.putNextEntry(zipEntry);

                try (final FileInputStream fileInputStream = new FileInputStream(targetZipFile);) {
                    while ((length = fileInputStream.read(bytes)) >= 0) {
                        zipOutputStream.write(bytes, 0, length);
                    }
                } catch (final IOException e) {
                    LOG.warn("Failed to create Archive", e);
                }
            }
        } catch (final IOException e) {
            LOG.warn("Failed to create Archive", e);
        }
    }

    private void extractArchive(final String timestampedArchive) throws IOException {
        final byte[] bytes = new byte[1024];

        try (final ZipInputStream zis = new ZipInputStream(new FileInputStream(timestampedArchive));) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                final String fileName = zipEntry.getName();
                final File newFile = new File(DAEXIM_DIR + fileName);

                int len;
                try (final FileOutputStream fos = new FileOutputStream(newFile);) {
                    while ((len = zis.read(bytes)) > 0) {
                        fos.write(bytes, 0, len);
                    }
                } catch (final IOException e) {
                    LOG.warn("Failed to extract Archive", e);
                }
                LOG.info("{} extracted.", zipEntry.getName());
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }

        LOG.info("{} extracted successfully.", timestampedArchive);
    }

    private int putArchive(final String timestampedArchive) throws IOException {
        final File archive = new File(timestampedArchive);
        final HttpURLConnection connection = getNexusConnection(archive.getName());
        connection.setRequestProperty("Content-Length", Long.toString(archive.length()));
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        try (final FileInputStream fileInputStream = new FileInputStream(archive);
            final OutputStream outputStream = connection.getOutputStream();) {
            final byte[] bytes = new byte[1024];
            int length;
            while ((length = fileInputStream.read(bytes)) >= 0) {
                outputStream.write(bytes, 0, length);
            }

            outputStream.flush();
        } catch (final IOException e) {
            LOG.warn("Failed to PUT Archive", e);
        }
        connection.disconnect();

        LOG.info("Status: {}", connection.getResponseCode());
        LOG.info("Message: {}", connection.getResponseMessage());
        return connection.getResponseCode();
    }

    private HttpURLConnection getNexusConnection(final String archive) throws IOException {
        final URL url = new URL(NEXUS_URL + archive);
        final String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(CREDENTIALS.getBytes());
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Authorization", auth);
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Proxy-Connection", "keep-alive");
        return connection;
    }

    private int getArchive(final String archiveIdentifier) throws IOException {
        final File archive = new File(DAEXIM_DIR + "backup.zip");
        if (archive.exists()) {
            LOG.info("Recently retrieved archive found. Removing old archive...");
            archive.delete();
            LOG.info("Archive removed.");
        }
        final HttpURLConnection connection = getNexusConnection(archiveIdentifier + "-" + BACKUP_ARCHIVE);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        final byte[] bytes = new byte[1024];
        int length;
        try (final InputStream connectionInputStream = connection.getInputStream();
            final FileOutputStream fileOutputStream = new FileOutputStream(archive);) {
            while ((length = connectionInputStream.read(bytes)) >= 0) { // while connection has bytes
                fileOutputStream.write(bytes, 0, length); // write to archive
            }
        } catch (final IOException e) {
            LOG.warn("Failed to GET Archive", e);
        }
        connection.disconnect();

        LOG.info("Status: {}", connection.getResponseCode());
        LOG.info("Message: {}", connection.getResponseMessage());
        LOG.info("{} successfully created.", archive.getName());
        return connection.getResponseCode();
    }

    private ListenableFuture<RpcResult<BackupDataOutput>> buildBackupDataFuture(final String statusCode,
        final String message) {
        final BackupDataOutputBuilder outputBuilder = new BackupDataOutputBuilder();
        outputBuilder.setStatus(statusCode);
        outputBuilder.setMessage(message);
        final RpcResult<BackupDataOutput> rpcResult = RpcResultBuilder.<BackupDataOutput>status(true)
            .withResult(outputBuilder.build()).build();
        return Futures.immediateFuture(rpcResult);
    }

    private ListenableFuture<RpcResult<RetrieveDataOutput>> retrieveDataOutputRpcResult(final String status,
        final String message) {
        final RetrieveDataOutputBuilder outputBuilder = new RetrieveDataOutputBuilder();
        outputBuilder.setStatus(status);
        outputBuilder.setMessage(message);
        final RpcResult<RetrieveDataOutput> rpcResult = RpcResultBuilder.<RetrieveDataOutput>status(true)
            .withResult(outputBuilder.build()).build();
        return Futures.immediateFuture(rpcResult);
    }
}
