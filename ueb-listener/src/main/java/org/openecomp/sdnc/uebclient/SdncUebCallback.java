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

package org.openecomp.sdnc.uebclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.sql.rowset.CachedRowSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.Pair;
import org.openecomp.sdc.api.IDistributionClient;
import org.openecomp.sdc.api.consumer.IDistributionStatusMessage;
import org.openecomp.sdc.api.consumer.INotificationCallback;
import org.openecomp.sdc.api.notification.IArtifactInfo;
import org.openecomp.sdc.api.notification.INotificationData;
import org.openecomp.sdc.api.notification.IResourceInstance;
import org.openecomp.sdc.api.results.IDistributionClientDownloadResult;
import org.openecomp.sdc.api.results.IDistributionClientResult;
import org.openecomp.sdc.tosca.parser.api.ISdcCsarHelper;
import org.openecomp.sdc.tosca.parser.exceptions.SdcToscaParserException;
import org.openecomp.sdc.tosca.parser.impl.SdcPropertyNames;
import org.openecomp.sdc.tosca.parser.impl.SdcToscaParserFactory;
import org.openecomp.sdc.toscaparser.api.Group;
import org.openecomp.sdc.toscaparser.api.Metadata;
import org.openecomp.sdc.toscaparser.api.NodeTemplate;
import org.openecomp.sdc.utils.ArtifactTypeEnum;
import org.openecomp.sdc.utils.DistributionActionResultEnum;
import org.openecomp.sdc.utils.DistributionStatusEnum;
import org.openecomp.sdnc.sli.resource.dblib.DBResourceManager;
import org.openecomp.sdnc.uebclient.SdncArtifactMap.SdncArtifactType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class SdncUebCallback implements INotificationCallback {

    private static final Logger LOG = LoggerFactory
            .getLogger(SdncUebCallback.class);

	private static DBResourceManager jdbcDataSource = null;
	private static final String SDNC_CONFIG_DIR = "SDNC_CONFIG_DIR";


    private class SdncAuthenticator extends Authenticator {

        private final String user;
        private final String passwd;

        SdncAuthenticator(String user, String passwd) {
            this.user = user;
            this.passwd = passwd;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, passwd.toCharArray());
        }

    }

    private class DeployableArtifact {
        SdncArtifactType type;
        IArtifactInfo artifactInfo;
        String svcName;
        String resourceName;
        String artifactName;
        String artifactVersion;
        File file;

        public String getArtifactName() {
            return artifactName;
        }



        public String getArtifactVersion() {
            return artifactVersion;
        }


        public SdncArtifactType getType() {
            return type;
        }



        public IArtifactInfo getArtifactInfo() {
            return artifactInfo;
        }


        public File getFile() {
            return file;
        }




        public DeployableArtifact(SdncArtifactType type, String svcName, String resourceName, IArtifactInfo artifactInfo, File file) {
            this.type = type;
            this.artifactInfo = artifactInfo;
			this.svcName = svcName;
			this.resourceName = resourceName;
            this.artifactName = artifactInfo.getArtifactName();
            this.artifactVersion = artifactInfo.getArtifactVersion();
            this.file = file;
        }


        public DeployableArtifact(SdncArtifactType type, String svcName, String resourceName, String artifactName, String artifactVersion, File file) {
            this.type = type;
            this.artifactInfo = null;
			this.svcName = svcName;
			this.resourceName = resourceName;
            this.artifactName = artifactName;
            this.artifactVersion = artifactVersion;
            this.file = file;
        }



        public String getSvcName() {
            return svcName;
        }



        public String getResourceName() {
            return resourceName;
        }

    }

    private final IDistributionClient client;
    private final SdncUebConfiguration config;

    private LinkedList<DeployableArtifact> deployList[];

	private static void setJdbcDataSource() throws IOException {

		String propPath = null;
		String propDir = System.getenv(SDNC_CONFIG_DIR);
		if (propDir == null) {

			propDir = "/opt/sdnc/data/properties";
		}
		propPath = propDir + "/dblib.properties";
		File propFile = new File(propPath);

		if (!propFile.exists()) {

			throw new FileNotFoundException(
					"Missing configuration properties file : "
							+ propFile);
		}

		Properties props = new Properties();
		props.load(new FileInputStream(propFile));

		try {
			jdbcDataSource = DBResourceManager.create(props);
		} catch(Throwable exc) {
			LOG.error("", exc);
		}

		if(((DBResourceManager)jdbcDataSource).isActive()){
			LOG.warn( "DBLIB: JDBC DataSource has been initialized.");
		} else {
			LOG.warn( "DBLIB: JDBC DataSource did not initialize successfully.");
		}
	}

	private static void loadArtifactMap() {

	}

    public SdncUebCallback(IDistributionClient client, SdncUebConfiguration config) {
        this.client = client;
        this.config = config;

    }

    @Override
	public void activateCallback(INotificationData data) {

        LOG.info("Received notification : ("+data.getDistributionID()+","+data.getServiceName()+","+data.getServiceVersion()+
				","+data.getServiceDescription() +  ")");

        String incomingDirName = config.getIncomingDir();
        String archiveDirName = config.getArchiveDir();

        File incomingDir = null;
        File archiveDir = null;

        if (!incomingDir.exists()) {
            incomingDir.mkdirs();
        }


        if (!archiveDir.exists()) {
            archiveDir.mkdirs();
        }

        // Process service level artifacts
        List<IArtifactInfo> artifactList = data.getServiceArtifacts();

        if (artifactList != null) {

            incomingDir = new File(incomingDirName + "/" + escapeFilename(data.getServiceName()));
            if (!incomingDir.exists()) {
                incomingDir.mkdirs();
            }

            archiveDir = new File(archiveDirName + "/" + escapeFilename(data.getServiceName()));
            if (!archiveDir.exists()) {
                archiveDir.mkdirs();
            }
            for (IArtifactInfo curArtifact : artifactList)
            {

                LOG.info("Received artifact " + curArtifact.getArtifactName());

				handleArtifact(data, data.getServiceName(), null, null, curArtifact, incomingDir, archiveDir);
            }
        }


        // Process resource level artifacts
        for (IResourceInstance curResource : data.getResources()) {

            LOG.info("Received resource : "+curResource.getResourceName());
            artifactList = curResource.getArtifacts();

            if (artifactList != null) {

                incomingDir = new File(incomingDirName + "/" + escapeFilename(data.getServiceName()) + "/" + escapeFilename(curResource.getResourceName()));
                if (!incomingDir.exists()) {
                    incomingDir.mkdirs();
                }

                archiveDir = new File(archiveDirName + "/" + escapeFilename(data.getServiceName()) + "/" + escapeFilename(curResource.getResourceName()));
                if (!archiveDir.exists()) {
                    archiveDir.mkdirs();
                }
                for (IArtifactInfo curArtifact : artifactList)
                {

                    LOG.info("Received artifact " + curArtifact.getArtifactName());

					handleArtifact(data, data.getServiceName(), curResource.getResourceName(), curResource.getResourceType(), curArtifact, incomingDir, archiveDir);
                }
            }
        }

        deployDownloadedFiles(incomingDir, archiveDir, data);


    }


    public void deployDownloadedFiles(File incomingDir, File archiveDir, INotificationData data) {

        if (incomingDir == null) {
            incomingDir = new File(config.getIncomingDir());

            if (!incomingDir.exists()) {
                incomingDir.mkdirs();
            }

        }

        if (archiveDir == null) {
            archiveDir = new File(config.getArchiveDir());

            if (!archiveDir.exists()) {
                archiveDir.mkdirs();
            }
        }

        String curFileName = "";
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(incomingDir.toPath())) {
            for (Path file: stream) {
                curFileName = file.toString();
                handleSuccessfulDownload(null,null, null, null, file.toFile(), archiveDir);
            }
        } catch (Exception x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            LOG.warn("Cannot process spool file "+ curFileName, x);
        }

        // Deploy scheduled deployments
        int numPasses = config.getMaxPasses();

        deployList = new LinkedList[numPasses];

        for (int i = 0 ; i < numPasses ; i++) {
			deployList[i] = new LinkedList<DeployableArtifact>();
        }
        for (int pass = 0 ; pass < config.getMaxPasses() ; pass++) {

            if (deployList[pass] != null) {
                while (! deployList[pass].isEmpty()) {
                    DeployableArtifact artifact = deployList[pass].pop();

                    DistributionStatusEnum deployResult = DistributionStatusEnum.DEPLOY_ERROR;


                    try {

                        deployResult = deploySpoolFile(artifact);
                    } catch (Exception e) {
                        LOG.error("Caught exception trying to deploy file", e);
                    }


                    IArtifactInfo artifactInfo = artifact.getArtifactInfo();

					if ((artifactInfo != null) && (data != null)) {
                        IDistributionClientResult deploymentStatus;
                            deploymentStatus = client.sendDeploymentStatus(buildStatusMessage(
                                    client, data, artifactInfo,
                                    deployResult));
                    }

                }
            }
        }
    }

	private void handleArtifact(INotificationData data, String svcName, String resourceName, String resourceType, IArtifactInfo artifact, File incomingDir, File archiveDir) {

        // Download Artifact
        IDistributionClientDownloadResult downloadResult = client
                .download(artifact);

		if (downloadResult == null) {

			handleFailedDownload(data, artifact);
			return;
		}

		byte[] payloadBytes = downloadResult.getArtifactPayload();

		if (payloadBytes == null) {
			handleFailedDownload(data, artifact);
			return;
		}

		String payload = new String(payloadBytes);


        File spoolFile = new File(incomingDir.getAbsolutePath() + "/" + artifact.getArtifactName());

        boolean writeSucceeded = false;

        try {
            FileWriter spoolFileWriter = new FileWriter(spoolFile);
            spoolFileWriter.write(payload);
            spoolFileWriter.close();
            writeSucceeded = true;
        } catch (Exception e) {
            LOG.error("Unable to save downloaded file to spool directory ("+ incomingDir.getAbsolutePath() +")", e);
        }


		if (writeSucceeded && (downloadResult.getDistributionActionResult() == DistributionActionResultEnum.SUCCESS)) {
            handleSuccessfulDownload(data, svcName, resourceName, artifact, spoolFile, archiveDir);


        } else {
            handleFailedDownload(data, artifact);
        }

    }

    private void handleFailedDownload(INotificationData data,
            IArtifactInfo relevantArtifact) {
        // Send Download Status
        IDistributionClientResult sendDownloadStatus = client
                .sendDownloadStatus(buildStatusMessage(client, data,
                        relevantArtifact, DistributionStatusEnum.DOWNLOAD_ERROR));
    }

    private void handleSuccessfulDownload(INotificationData data, String svcName, String resourceName,
            IArtifactInfo artifact, File spoolFile, File archiveDir) {

		if ((data != null) && (artifact != null)) {
            // Send Download Status
            IDistributionClientResult sendDownloadStatus = client
                    .sendDownloadStatus(buildStatusMessage(client, data, artifact, DistributionStatusEnum.DOWNLOAD_OK));
        }

        // If an override file exists, read that instead of the file we just downloaded
        ArtifactTypeEnum artifactEnum = ArtifactTypeEnum.YANG_XML;

		boolean toscaYamlType = false;
        if (artifact != null) {
			String artifactTypeString = artifact.getArtifactType();
			if (artifactTypeString.contains("TOSCA_TEMPLATE")) {
				toscaYamlType = true;
			}
		} else {
			if (spoolFile.toString().contains(".yml") || spoolFile.toString().contains(".csar")) {
				toscaYamlType = true;
			}
        }
        String overrideFileName = config.getOverrideFile();
		if ((overrideFileName != null) && (overrideFileName.length() > 0)) {
            File overrideFile = new File(overrideFileName);

            if (overrideFile.exists()) {
                artifactEnum = ArtifactTypeEnum.YANG_XML;
                spoolFile = overrideFile;
            }

        }

		if (toscaYamlType == true) {
			processToscaYaml (data, svcName, resourceName, artifact, spoolFile, archiveDir);

			try {
				Path source = spoolFile.toPath();
				Path targetDir = archiveDir.toPath();

				Files.move(source, targetDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				LOG.warn("Could not move "+spoolFile.getAbsolutePath()+" to "+archiveDir.getAbsolutePath(), e);
			}

			return;
		}

        // Process spool file
        Document spoolDoc = null;
        File transformedFile = null;

        // Apply XSLTs and get Doc object
        try {
			if (!spoolFile.isDirectory()) {
            transformedFile = applyXslts(spoolFile);
			}
        } catch (Exception e) {
            LOG.error("Caught exception trying to parse XML file", e);
        }

        if (transformedFile != null) {
            try {

                try {

                    DocumentBuilderFactory dbf = DocumentBuilderFactory
                            .newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();

                    spoolDoc = db.parse(transformedFile);
                } catch (Exception e) {
                    LOG.error(
                            "Caught exception trying to parse transformed XML file "
                                    + transformedFile.getAbsolutePath(), e);
                }

            } catch (Exception e) {
                LOG.error("Caught exception trying to deploy file", e);
            }
        }


        if (spoolDoc != null) {
            // Analyze file type
            SdncArtifactType artifactType = analyzeFileType(artifactEnum,
                    spoolFile, spoolDoc);

            if (artifactType != null) {

                scheduleDeployment(artifactType, svcName, resourceName, artifact, spoolFile.getName(), transformedFile);

            }

            // SDNGC-2660 : Move file to archive directory even if it is an unrecognized type so that
            // we do not keep trying and failing to process it.
            try {
                Path source = spoolFile.toPath();
                Path targetDir = archiveDir.toPath();

                Files.move(source, targetDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                LOG.warn("Could not move "+spoolFile.getAbsolutePath()+" to "+archiveDir.getAbsolutePath(), e);
            }
        }


    }


	private void processToscaYaml(INotificationData data, String svcName, String resourceName,
			IArtifactInfo artifact, File spoolFile, File archiveDir) {
		
		// Use ASDC Dist Client 1.1.5 with TOSCA parsing APIs to extract relevant TOSCA model data
		
		// TOSCA data extraction flow 1707:
		// Use ASDC dist-client to get yaml string - not yet available
		String model_yaml = null;
		LOG.info("Process TOSCA YAML file: "+spoolFile.toString());
		
		SdcToscaParserFactory factory = SdcToscaParserFactory.getInstance();
		ISdcCsarHelper sdcCsarHelper = null;
		try {
			sdcCsarHelper = factory.getSdcCsarHelper(spoolFile.getAbsolutePath());
		} catch (SdcToscaParserException e) {
			LOG.error("Could not create SDC TOSCA Parser ", e);
			factory.close();
			return;
		}

		// Ingest Service Data - 1707
		Metadata serviceMetadata = sdcCsarHelper.getServiceMetadata();
		SdncServiceModel serviceModel = new SdncServiceModel(sdcCsarHelper, serviceMetadata);
		serviceModel.setFilename(spoolFile.toString().substring(spoolFile.toString().lastIndexOf("/")+1));  // will be csar file name
		serviceModel.setServiceInstanceNamePrefix(SdncBaseModel.extractSubstitutionMappingTypeName(sdcCsarHelper).substring(SdncBaseModel.extractSubstitutionMappingTypeName(sdcCsarHelper).lastIndexOf(".")+1));

		try {
			cleanUpExistingToscaServiceData(serviceModel.getServiceUUID());
			LOG.info("Call insertToscaData for SERVICE_MODEL serviceUUID = " + serviceModel.getServiceUUID());
			insertToscaData(serviceModel.getSql(model_yaml));
		} catch (IOException e) {
			LOG.error("Could not insert Tosca YAML data into the SERVICE_MODEL table ", e);
			factory.close();
			return;
		}	

		// Ingest Network (VL) Data - 1707
		//List<NodeTemplate> vlNodeTemplatesList = sdcCsarHelper.getServiceNodeTemplatesByType("VL");
		List<NodeTemplate> vlNodeTemplatesList = sdcCsarHelper.getServiceVlList();

		for (NodeTemplate nodeTemplate :  vlNodeTemplatesList) {
			SdncNodeModel nodeModel = new SdncNodeModel (sdcCsarHelper, nodeTemplate);
			nodeModel.setServiceUUID(serviceModel.getServiceUUID());
			nodeModel.setEcompGeneratedNaming(SdncBaseModel.extractBooleanInputDefaultValue(sdcCsarHelper, SdcPropertyNames.PROPERTY_NAME_SERVICENAMING_DEFAULT_ECOMPGENERATEDNAMING));//service_naming#default#ecomp_generated_naming
			
			try {
				cleanUpExistingToscaData("NETWORK_MODEL", "customization_uuid", nodeModel.getCustomizationUUID());
				cleanUpExistingToscaData("VPN_BINDINGS", "network_customization_uuid", nodeModel.getCustomizationUUID());
				LOG.info("Call insertToscaData for NETWORK_MODEL customizationUUID = " + nodeModel.getCustomizationUUID());
				// using ASDC dist-client use method for get yaml string
				insertToscaData(nodeModel.getSql(model_yaml));
				insertToscaData(nodeModel.getVpnBindingsSql());
			} catch (IOException e) {
				LOG.error("Could not insert Tosca YAML data into the NETWORK_MODEL table ", e);
			}
		}
		
		// Ingest Allotted Resource Data - 1707
		List<NodeTemplate> arNodeTemplatesList = sdcCsarHelper.getAllottedResources();

		for (NodeTemplate nodeTemplate :  arNodeTemplatesList) {
			SdncARModel nodeModel = new SdncARModel (sdcCsarHelper, nodeTemplate);
			
			try {
				cleanUpExistingToscaData("ALLOTTED_RESOURCE_MODEL", "customization_uuid", nodeModel.getCustomizationUUID());
				LOG.info("Call insertToscaData for ALLOTTED_RESOURCE_MODEL customizationUUID = " + nodeModel.getCustomizationUUID());
				// using ASDC dist-client use method for get yaml string
				insertToscaData(nodeModel.getSql("ALLOTTED_RESOURCE_MODEL", model_yaml));
			} catch (IOException e) {
				LOG.error("Could not insert Tosca YAML data into the NETWORK_MODEL table ", e);
			}
		}
		
		// Ingest Network (VF) Data - 1707
		//List<NodeTemplate> nodeTemplatesList = sdcCsarHelper.getServiceNodeTemplatesByType("VF");
		List<NodeTemplate> vfNodeTemplatesList = sdcCsarHelper.getServiceVfList();

		for (NodeTemplate nodeTemplate :  vfNodeTemplatesList) {
			SdncVFModel vfNodeModel = new SdncVFModel (sdcCsarHelper, nodeTemplate);
			
			try {
				cleanUpExistingToscaData("VF_MODEL", "customization_uuid", vfNodeModel.getCustomizationUUID()) ;
				LOG.info("Call insertToscaData for VF_MODEL customizationUUID = " + vfNodeModel.getCustomizationUUID());
				insertToscaData(vfNodeModel.getSql("VF_MODEL", model_yaml));
			} catch (IOException e) {
				LOG.error("Could not insert Tosca YAML data into the VF_MODEL table ", e);
			}
			
			// For each VF, insert VF_MODULE_MODEL data
			List<Group> vfModules = sdcCsarHelper.getVfModulesByVf(vfNodeModel.getCustomizationUUIDNoQuotes());
			for (Group group : vfModules){
				SdncVFModuleModel vfModuleModel = new SdncVFModuleModel(sdcCsarHelper, group);
			
				try {
					cleanUpExistingToscaData("VF_MODULE_MODEL", "customization_uuid", vfModuleModel.getCustomizationUUID());
					LOG.info("Call insertToscaData for VF_MODULE_MODEL customizationUUID = " + vfModuleModel.getCustomizationUUID());
					insertToscaData(vfModuleModel.getSql("VF_MODULE_MODEL", model_yaml));
				} catch (IOException e) {
					LOG.error("Could not insert Tosca YAML data into the VF_MODULE_MODEL table ", e);
				}
			
				// For each VF Module, get the VFC list, insert VF_MODULE_TO_VFC_MAPPING data
				// List<NodeTemplate> groupMembers = sdcCsarHelper.getMembersOfGroup(group); - old version
				// For each vfcNode (group member) in the groupMembers list, extract vm_type and vm_count.  
				// Insert vf_module.customizationUUID, vfcNode.customizationUUID and vm_type and vm_count into VF_MODULE_TO_VFC_MAPPING
				List<NodeTemplate> groupMembers = sdcCsarHelper.getMembersOfVfModule(nodeTemplate, group); // not yet available
				for (NodeTemplate vfcNode : groupMembers){
					SdncVFCModel vfcModel = new SdncVFCModel(sdcCsarHelper, vfcNode);
				
					try {
						cleanUpExistingToscaData("VF_MODULE_TO_VFC_MAPPING", "vf_module_customization_uuid", vfModuleModel.getCustomizationUUID());
						LOG.info("Call insertToscaData for VF_MODULE_TO_VFC_MAPPING customizationUUID = " + vfModuleModel.getCustomizationUUID());
						insertToscaData("insert into VF_MODULE_TO_VFC_MAPPING (vf_module_customization_uuid, vfc_customization_uuid, vm_type, vm_count) values (" + 
								vfModuleModel.getCustomizationUUID() + ", " + vfcModel.getCustomizationUUID() + ", \"" + vfcModel.getVmType() + "\", \"" + vfcModel.getVmCount() + "\")");
					} catch (IOException e) {
						LOG.error("Could not insert Tosca YAML data into the VF_MODULE_TO_VFC_MAPPING table ", e);
					}

				}

			}
			
			// For each VF, insert VFC_MODEL data
			List<NodeTemplate> vfcNodes = sdcCsarHelper.getVfcListByVf(vfNodeModel.getCustomizationUUIDNoQuotes());
			for (NodeTemplate vfcNode : vfcNodes){
				SdncVFCModel vfcModel = new SdncVFCModel(sdcCsarHelper, vfcNode);
			
				try {
					cleanUpExistingToscaData("VFC_MODEL", "customization_uuid", vfcModel.getCustomizationUUID());
					LOG.info("Call insertToscaData for VFC_MODEL customizationUUID = " + vfcModel.getCustomizationUUID());
					insertToscaData(vfcModel.getSql("VFC_MODEL", model_yaml));
				} catch (IOException e) {
					LOG.error("Could not insert Tosca YAML data into the VFC_MODEL table ", e);
				}

			}
			
			// For each VF, insert VF_TO_NETWORK_ROLE_MAPPING data
			List<NodeTemplate> cpNodes = sdcCsarHelper.getCpListByVf(vfNodeModel.getCustomizationUUIDNoQuotes());
			for (NodeTemplate cpNode : cpNodes){
		
				// Insert into VF_TO_NETWORK_ROLE_MAPPING vf_customization_uuid and network_role
				String cpNetworkRole = sdcCsarHelper.getNodeTemplatePropertyLeafValue(cpNode, "network_role_tag");
			
				try {
					cleanUpExistingToscaData("VF_TO_NETWORK_ROLE_MAPPING", "vf_customization_uuid", vfNodeModel.getCustomizationUUID());
					LOG.info("Call insertToscaData for VF_TO_NETWORK_ROLE_MAPPING vfCustomizationUUID = " + vfNodeModel.getCustomizationUUID());
					insertToscaData("insert into VF_TO_NETWORK_ROLE_MAPPING (vf_customization_uuid, network_role) values (" + 
					vfNodeModel.getCustomizationUUID() + ", \"" + cpNetworkRole + "\")");
				} catch (IOException e) {
					LOG.error("Could not insert Tosca YAML data into the VF_TO_NETWORK_ROLE_MAPPING table ", e);
				}
				
				// Insert VFC_TO_NETWORK_ROLE_MAPPING data
				Map<String, String> mappingParams = new HashMap<String, String>();
				//String cpNetworkRoleTag = "\"" + sdcCsarHelper.getNodeTemplatePropertyLeafValue(cpNode, SdcPropertyNames.PROPERTY_NAME_NETWORKROLETAG) + "\"";
				// extract network_role, network_role_tag and virtual_binding from this cpNode
				SdncBaseModel.addParameter("network_role", SdncBaseModel.extractValue(sdcCsarHelper, cpNode, "network_role"), mappingParams);
				SdncBaseModel.addParameter("network_role_tag", SdncBaseModel.extractValue(sdcCsarHelper, cpNode, "network_role_tag"), mappingParams);
				String virtualBinding = "\"" + SdncBaseModel.extractValue(sdcCsarHelper, cpNode, "requirements#virtualBinding") + "\""; 

				// get list of cpNodes and vfcNodes with matching virtualBinding
				List<Pair<NodeTemplate, NodeTemplate>> matchList = sdcCsarHelper.getNodeTemplatePairsByReqName(sdcCsarHelper.getCpListByVf(vfNodeModel.getCustomizationUUIDNoQuotes()), sdcCsarHelper.getVfcListByVf(vfNodeModel.getCustomizationUUIDNoQuotes()), virtualBinding);				
				for (Pair<NodeTemplate, NodeTemplate> match : matchList) {  // should be 1 match?
					
					// extract values from the left "CP" Node
					SdncBaseModel.addParameter("ipv4_use_dhcp", SdncBaseModel.extractBooleanValue(sdcCsarHelper, match.getLeft(), SdcPropertyNames.PROPERTY_NAME_NETWORKASSIGNMENTS_IPV4SUBNETDEFAULTASSIGNMENTS_DHCPENABLED), mappingParams);
					//SdncBaseModel.addParameter("ipv4_ip_version", SdncBaseModel.extractValue(sdcCsarHelper, match.getLeft(), SdcPropertyNames.PROPERTY_NAME_NETWORKASSIGNMENTS_IPV4SUBNETDEFAULTASSIGNMENTS_IPVERSION), mappingParams);
					SdncBaseModel.addParameter("ipv4_ip_version", "dummy_ipv4_vers", mappingParams);
					SdncBaseModel.addParameter("ipv6_use_dhcp", SdncBaseModel.extractBooleanValue(sdcCsarHelper, match.getLeft(), SdcPropertyNames.PROPERTY_NAME_NETWORKASSIGNMENTS_IPV6SUBNETDEFAULTASSIGNMENTS_DHCPENABLED), mappingParams);
					//SdncBaseModel.addParameter("ipv6_ip_version", SdncBaseModel.extractValue(sdcCsarHelper, match.getLeft(), SdcPropertyNames.PROPERTY_NAME_NETWORKASSIGNMENTS_IPV6SUBNETDEFAULTASSIGNMENTS_IPVERSION), mappingParams);
					SdncBaseModel.addParameter("ipv6_ip_version", "dummy_ipv6_vers", mappingParams);
					//String extcp_subnetpool_id = "\"" + SdncBaseModel.extractValue(sdcCsarHelper, match.getLeft(), SdcPropertyNames.PROPERTY_NAME_SUBNETPOOLID) + "\""; // need path to subnetpoolid 
					
					// extract values from the right "VFC" Node
					String vfcCustomizationUuid = "\"" + SdncBaseModel.extractValue(sdcCsarHelper, match.getRight().getMetadata(), "customization_uuid") + "\"";
					SdncBaseModel.addParameter("vm_type", SdncBaseModel.extractValue(sdcCsarHelper, match.getRight(), SdcPropertyNames.PROPERTY_NAME_VMTYPE), mappingParams);
					SdncBaseModel.addIntParameter("ipv4_count", SdncBaseModel.extractValue(sdcCsarHelper, match.getRight(), SdcPropertyNames.PROPERTY_NAME_NETWORKASSIGNMENTS_IPV4SUBNETDEFAULTASSIGNMENTS_MINSUBNETSCOUNT), mappingParams);
					SdncBaseModel.addIntParameter("ipv6_count", SdncBaseModel.extractValue(sdcCsarHelper, match.getRight(), SdcPropertyNames.PROPERTY_NAME_NETWORKASSIGNMENTS_IPV6SUBNETDEFAULTASSIGNMENTS_MINSUBNETSCOUNT), mappingParams);
				
					try {
						cleanUpExistingToscaData("VFC_TO_NETWORK_ROLE_MAPPING", "vfc_customization_uuid", vfcCustomizationUuid);
						LOG.info("Call insertToscaData for VFC_TO_NETWORK_ROLE_MAPPING vfcCustomizationUUID = " + vfcCustomizationUuid);
						insertToscaData(SdncBaseModel.getSql("VFC_TO_NETWORK_ROLE_MAPPING", "vfc_customization_uuid", vfcCustomizationUuid, "", mappingParams));
					} catch (IOException e) {
						LOG.error("Could not insert Tosca YAML data into the VFC_TO_NETWORK_ROLE_MAPPING table ", e);
					}
				
				}	
				
			} // CP loop	
			
		} // VF loop
		
		// Close ASDC TOSCA Parser factory - we are done processing this distribution
		factory.close();
		
		if ((artifact != null) && (data != null)) {
			LOG.info("Update to SDN-C succeeded");
			IDistributionClientResult deploymentStatus;
				deploymentStatus = client.sendDeploymentStatus(buildStatusMessage(
						client, data, artifact,
						DistributionStatusEnum.DEPLOY_OK));
		}
	
	}
	
	 private void cleanUpExistingToscaData(String tableName, String keyName, String keyValue) throws IOException
     {
         
            if (jdbcDataSource == null) {
            	 setJdbcDataSource();
            }
             try {
            	int rowCount = 0;
            	CachedRowSet data = jdbcDataSource.getData("SELECT * from " + tableName + " where " + keyName + " = " + keyValue + ";", null, "");
            	while(data.next()) { 
     				rowCount ++; 
            	}
            	if (rowCount != 0) {
                    LOG.info("cleanUpExistingToscaData: " + keyValue);
               		jdbcDataSource.writeData("DELETE from " + tableName + " where " + keyName + " = " + keyValue + ";", null, null);
            	}
            	 
			} catch (SQLException e) {				
				LOG.error("Could not clean up existing " + tableName  + " for " + keyValue, e);
			}    
			
     }

	
	 private void cleanUpExistingToscaServiceData(String serviceUUID) throws IOException
     {
         
            if (jdbcDataSource == null) {
            	 setJdbcDataSource();
            }
             try {
            	int rowCount = 0;
            	CachedRowSet data = jdbcDataSource.getData("SELECT * from SERVICE_MODEL where service_uuid = " + serviceUUID + ";", null, "");
            	while(data.next()) { 
     				rowCount ++; 
            	}
            	if (rowCount != 0) {
                    LOG.info("cleanUpExistingToscaData: " + serviceUUID);
               		jdbcDataSource.writeData("DELETE from NETWORK_MODEL where service_uuid = " + serviceUUID + ";", null, null);
               		jdbcDataSource.writeData("DELETE from SERVICE_MODEL where service_uuid = " + serviceUUID + ";", null, null);
            	}
            	 
			} catch (SQLException e) {				
				LOG.error("Could not clean up existing NETWORK_MODEL and SERVICE_MODEL for service_UUID " + serviceUUID, e);
			}    
			
     }

	
	 private void insertToscaData(String toscaDataString) throws IOException
     {
            LOG.debug("insertToscaData: " + toscaDataString);

            if (jdbcDataSource == null) {
            	 setJdbcDataSource();
            }
             try {

 				jdbcDataSource.writeData(toscaDataString, null, null);

			} catch (SQLException e) {
				LOG.error("Could not insert Tosca YAML data into the database ", e);
			}

     }


    private SdncArtifactType analyzeFileType(ArtifactTypeEnum artifactType, File spoolFile, Document spoolDoc) {

        if (artifactType != ArtifactTypeEnum.YANG_XML) {
            LOG.error("Unexpected artifact type - expecting YANG_XML, got "+artifactType);
			return (null);
        }

        // Examine outer tag

        try {


            Element root = spoolDoc.getDocumentElement();

            String rootName = root.getTagName();

            if (rootName.contains(":")) {
                String[] rootNameElems = rootName.split(":");
                rootName = rootNameElems[rootNameElems.length - 1];
            }

            if (rootName != null) {
            	SdncArtifactType mapEntry = config.getMapping(rootName);


                if (mapEntry == null) {

                    LOG.error("Unexpected file contents - root tag is "+rootName);
                }
				return(mapEntry);
            } else {
                LOG.error("Cannot get root tag from file");
				return(null);
            }

        } catch (Exception e) {
            LOG.error("Could not parse YANG_XML file "+spoolFile.getName(), e);
			return(null);
        }
    }

    private void scheduleDeployment(SdncArtifactType type, String svcName, String resourceName, IArtifactInfo artifactInfo, String spoolFileName, File spoolFile) {

        if (type.getPass() < deployList.length) {

            if (artifactInfo != null) {
                LOG.debug("Scheduling "+artifactInfo.getArtifactName()+" version "+artifactInfo.getArtifactVersion()+" for deployment");

                deployList[type.getPass()].add(new DeployableArtifact(type, svcName, resourceName, artifactInfo, spoolFile));
            } else {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");//dd/MM/yyyy
                Date now = new Date();
                String artifactVersion = sdfDate.format(now);
                LOG.debug("Scheduling "+spoolFileName+" version "+artifactVersion+" for deployment");
                String artifactName = spoolFileName;
                if (artifactInfo != null) {
                    artifactName = artifactInfo.getArtifactName();
                }
                deployList[type.getPass()].add(new DeployableArtifact(type, svcName, resourceName, artifactName, artifactVersion, spoolFile));
            }
        } else {
            LOG.info("Pass for type "+type.getTag()+" is "+type.getPass()+" which is not <= "+deployList.length);
        }
    }


    private DistributionStatusEnum deploySpoolFile(DeployableArtifact artifact) {

        DistributionStatusEnum deployResult = DistributionStatusEnum.DEPLOY_OK;

        StringBuffer msgBuffer = new StringBuffer();


        String namespace = config.getAsdcApiNamespace();
		if ((namespace == null) || (namespace.length() == 0)) {
            namespace="com:att:sdnctl:asdcapi";
        }

        msgBuffer.append("<input xmlns='");
        msgBuffer.append(namespace);
        msgBuffer.append("'>\n");

        String svcName = artifact.getSvcName();
        String resourceName = artifact.getResourceName();
        String artifactName = artifact.getArtifactName();

        if (svcName != null) {
            if (resourceName != null) {
                artifactName = svcName + "/" + resourceName + "/" + artifactName;
            } else {
                artifactName = svcName + "/" + artifactName;
            }
        }

        msgBuffer.append("<artifact-name>"+artifactName+"</artifact-name>\n");
        msgBuffer.append("<artifact-version>"+artifact.getArtifactVersion()+"</artifact-version>\n");


        try {
            BufferedReader rdr = new BufferedReader(new FileReader(artifact.getFile()));

            String curLine = rdr.readLine();

            while (curLine != null) {

                if (!curLine.startsWith("<?")) {
                    msgBuffer.append(curLine+"\n");
                }
                curLine = rdr.readLine();
            }
            rdr.close();

        } catch (Exception e) {
            LOG.error("Could not process spool file "+artifact.getFile().getName(), e);
			return(DistributionStatusEnum.DEPLOY_ERROR);
        }

        msgBuffer.append("</input>\n");


        byte[] msgBytes = msgBuffer.toString().getBytes();

        Document results = postRestXml(artifact.getType().getRpcUrl(config.getAsdcApiBaseUrl()), msgBytes);

        if (results == null) {

            deployResult = DistributionStatusEnum.DEPLOY_ERROR;
        } else {

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xp = xpf.newXPath();

            String asdcApiResponseCode = "500";

            try {

                asdcApiResponseCode = xp.evaluate("//asdc-api-response-code[position()=1]/text()", results.getDocumentElement());
            } catch (Exception e) {
                LOG.error("Caught exception retrying to evaluate xpath", e);
            }

            if (asdcApiResponseCode.contains("200")) {
                LOG.info("Update to SDN-C succeeded");
                deployResult = DistributionStatusEnum.DEPLOY_OK;
            } else {
                LOG.info("Update to SDN-C failed (response code "+asdcApiResponseCode+")");

                if (asdcApiResponseCode.contains("409")) {
                    deployResult = DistributionStatusEnum.ALREADY_DEPLOYED;
                } else {

                    deployResult = DistributionStatusEnum.DEPLOY_ERROR;
                }
            }
        }



		return(deployResult);
    }





    public static IDistributionStatusMessage buildStatusMessage(
            final IDistributionClient client, final INotificationData data,
            final IArtifactInfo relevantArtifact,
            final DistributionStatusEnum status) {
        IDistributionStatusMessage statusMessage = new IDistributionStatusMessage() {

            @Override
			public long getTimestamp() {
                long currentTimeMillis = System.currentTimeMillis();
                return currentTimeMillis;
            }

            @Override
			public DistributionStatusEnum getStatus() {
                return status;
            }

            @Override
			public String getDistributionID() {
                return data.getDistributionID();
            }

            @Override
			public String getConsumerID() {
                return client.getConfiguration().getConsumerID();
            }

            @Override
			public String getArtifactURL() {
                return relevantArtifact.getArtifactURL();
            }
        };
        return statusMessage;

    }

    private HttpURLConnection getRestXmlConnection(String urlString, String method) throws IOException
    {
        URL sdncUrl = new URL(urlString);
        Authenticator.setDefault(new SdncAuthenticator(config.getSdncUser(), config.getSdncPasswd()));

        HttpURLConnection conn = (HttpURLConnection) sdncUrl.openConnection();

        String authStr = config.getSdncUser()+":"+config.getSdncPasswd();
        String encodedAuthStr = new String(Base64.encodeBase64(authStr.getBytes()));

        conn.addRequestProperty("Authentication", "Basic "+encodedAuthStr);

        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/xml");
        conn.setRequestProperty("Accept", "application/xml");

        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

		return(conn);

    }

    private Document postRestXml(String urlString, byte[] msgBytes) {
        Document response = null;

        try {
			SdncOdlConnection odlConn = SdncOdlConnection.newInstance(urlString, config.getSdncUser(), config.getSdncPasswd());

			String sdncResp = odlConn.send("POST", "application/xml", new String(msgBytes));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();


			response = db.parse(new ByteArrayInputStream(sdncResp.getBytes()));
        } catch (Exception e) {
			LOG.error("Caught exception posting to ODL tier", e);
        }

		return(response);

    }

    private File applyXslts(File srcFile) {

        Document doc = null;


        File inFile = srcFile;
        File outFile = null;

        String xsltPathList = config.getXsltPathList();

		if ((xsltPathList == null) || (xsltPathList.length() == 0)) {
            outFile = inFile;
        } else {

            String[] xsltPaths = xsltPathList.split(",");

            for (String xsltPath : xsltPaths) {
                try{

                    outFile = File.createTempFile("tmp", "xml");
                    TransformerFactory factory = TransformerFactory.newInstance();
                    Source xslt = new StreamSource(new File(xsltPath));
                    Transformer transformer = factory.newTransformer(xslt);
                    Source text = new StreamSource(inFile);


                    transformer.transform(text, new StreamResult(outFile));

                    inFile = outFile;

                } catch (Exception e) {
                    LOG.error("Caught exception trying to apply XSLT template "+xsltPath, e);

                }

            }
        }

        // After transformations, parse transformed XML


		return(outFile);
    }

    private String escapeFilename(String str) {
        StringBuffer retval = new StringBuffer();

        for (int i = 0 ; i < str.length() ; i++) {
            char curchar = str.charAt(i);
            if (Character.isJavaIdentifierPart(curchar)) {
                retval.append(curchar);
            }
        }

		return(retval.toString());

    }

}
