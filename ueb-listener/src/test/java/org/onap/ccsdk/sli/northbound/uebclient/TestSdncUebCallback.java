package org.onap.ccsdk.sli.northbound.uebclient;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
import org.openecomp.sdc.api.IDistributionClient;
import org.openecomp.sdc.api.notification.IArtifactInfo;
import org.openecomp.sdc.api.notification.INotificationData;
import org.openecomp.sdc.api.notification.IResourceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;

public class TestSdncUebCallback {


	private static final String CRTBL_SERVICE_MODEL = "CREATE TABLE `SERVICE_MODEL` (\n" +
			"  `service_uuid` varchar(255) NOT NULL,\n" +
			"  `model_yaml` longblob,\n" +
			"  `invariant_uuid` varchar(255) DEFAULT NULL,\n" +
			"  `version` varchar(255) DEFAULT NULL,\n" +
			"  `name` varchar(255) DEFAULT NULL,\n" +
			"  `description` varchar(1024) DEFAULT NULL,\n" +
			"  `type` varchar(255) DEFAULT NULL,\n" +
			"  `category` varchar(255) DEFAULT NULL,\n" +
			"  `ecomp_naming` char(1) DEFAULT NULL,\n" +
			"  `service_instance_name_prefix` varchar(255) DEFAULT NULL,\n" +
			"  `filename` varchar(100) DEFAULT NULL,\n" +
			"  `naming_policy` varchar(255) DEFAULT NULL,\n" +
			"  PRIMARY KEY (`service_uuid`)\n" +
			") ";

	private static final String CRTBL_NETWORK_MODEL = "CREATE TABLE `NETWORK_MODEL` (\n" +
			"  `customization_uuid` varchar(255) NOT NULL,\n" +
			"  `service_uuid` varchar(255) NOT NULL,\n" +
			"  `model_yaml` longblob,\n" +
			"  `invariant_uuid` varchar(255) DEFAULT NULL,\n" +
			"  `uuid` varchar(255) DEFAULT NULL,\n" +
			"  `network_type` varchar(255) DEFAULT NULL,\n" +
			"  `network_role` varchar(255) DEFAULT NULL,\n" +
			"  `network_technology` varchar(255) DEFAULT NULL,\n" +
			"  `network_scope` varchar(255) DEFAULT NULL,\n" +
			"  `naming_policy` varchar(255) DEFAULT NULL,\n" +
			"  `ecomp_generated_naming` char(1) DEFAULT NULL,\n" +
			"  `is_shared_network` char(1) DEFAULT NULL,\n" +
			"  `is_external_network` char(1) DEFAULT NULL,\n" +
			"  `is_provider_network` char(1) DEFAULT NULL,\n" +
			"  `physical_network_name` varchar(255) DEFAULT NULL,\n" +
			"  `is_bound_to_vpn` char(1) DEFAULT NULL,\n" +
			"  `vpn_binding` varchar(255) DEFAULT NULL,\n" +
			"  `use_ipv4` char(1) DEFAULT NULL,\n" +
			"  `ipv4_dhcp_enabled` char(1) DEFAULT NULL,\n" +
			"  `ipv4_ip_version` char(1) DEFAULT NULL,\n" +
			"  `ipv4_cidr_mask` varchar(255) DEFAULT NULL,\n" +
			"  `eipam_v4_address_plan` varchar(255) DEFAULT NULL,\n" +
			"  `use_ipv6` char(1) DEFAULT NULL,\n" +
			"  `ipv6_dhcp_enabled` char(1) DEFAULT NULL,\n" +
			"  `ipv6_ip_version` char(1) DEFAULT NULL,\n" +
			"  `ipv6_cidr_mask` varchar(255) DEFAULT NULL,\n" +
			"  `eipam_v6_address_plan` varchar(255) DEFAULT NULL,\n" +
			"  `version` varchar(255) DEFAULT NULL,\n" +
			"  PRIMARY KEY (`customization_uuid`),\n" +
			"  KEY `FK_NETWORK_MODEL` (`service_uuid`),\n" +
			"  CONSTRAINT `FK_NETWORK_MODEL` FOREIGN KEY (`service_uuid`) REFERENCES `SERVICE_MODEL` (`service_uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
			")";

	private static final String CRTBL_ALLOTTED_RESOURCE_MODEL = "CREATE TABLE `ALLOTTED_RESOURCE_MODEL` (\n" +
			"  `customization_uuid` varchar(255) NOT NULL,\n" +
			"  `model_yaml` longblob,\n" +
			"  `invariant_uuid` varchar(255) DEFAULT NULL,\n" +
			"  `uuid` varchar(255) DEFAULT NULL,\n" +
			"  `version` varchar(255) DEFAULT NULL,\n" +
			"  `naming_policy` varchar(255) DEFAULT NULL,\n" +
			"  `ecomp_generated_naming` char(1) DEFAULT NULL,\n" +
			"  `depending_service` varchar(255) DEFAULT NULL,\n" +
			"  `role` varchar(255) DEFAULT NULL,\n" +
			"  `type` varchar(255) DEFAULT NULL,\n" +
			"  `service_dependency` varchar(255) DEFAULT NULL,\n" +
			"  `allotted_resource_type` varchar(255) DEFAULT NULL,\n" +
			"  PRIMARY KEY (`customization_uuid`)\n" +
			") ";

	private static final String CRTBL_VFC_MODEL = "CREATE TABLE `VFC_MODEL` (\n" +
			"  `customization_uuid` varchar(255) NOT NULL,\n" +
			"  `model_yaml` longblob,\n" +
			"  `invariant_uuid` varchar(255) DEFAULT NULL,\n" +
			"  `uuid` varchar(255) DEFAULT NULL,\n" +
			"  `version` varchar(255) DEFAULT NULL,\n" +
			"  `naming_policy` varchar(255) DEFAULT NULL,\n" +
			"  `ecomp_generated_naming` char(1) DEFAULT NULL,\n" +
			"  `nfc_function` varchar(255) DEFAULT NULL,\n" +
			"  `nfc_naming_code` varchar(255) DEFAULT NULL,\n" +
			"  `vm_type` varchar(255) DEFAULT NULL,\n" +
			"  `vm_type_tag` varchar(255) DEFAULT NULL,\n" +
			"  `vm_image_name` varchar(255) DEFAULT NULL,\n" +
			"  `vm_flavor_name` varchar(255) DEFAULT NULL,\n" +
			"  `high_availability` varchar(255) DEFAULT NULL,\n" +
			"  `nfc_naming` varchar(255) DEFAULT NULL,\n" +
			"  `min_instances` int(11) DEFAULT NULL,\n" +
			"  `max_instances` int(11) DEFAULT NULL,\n" +
			"  PRIMARY KEY (`customization_uuid`)\n" +
			") ";

	private static final String CRTBL_VF_MODEL = "CREATE TABLE `VF_MODEL` (\n" +
			"  `customization_uuid` varchar(255) NOT NULL,\n" +
			"  `model_yaml` longblob,\n" +
			"  `invariant_uuid` varchar(255) DEFAULT NULL,\n" +
			"  `uuid` varchar(255) DEFAULT NULL,\n" +
			"  `version` varchar(255) DEFAULT NULL,\n" +
			"  `name` varchar(255) DEFAULT NULL,\n" +
			"  `naming_policy` varchar(255) DEFAULT NULL,\n" +
			"  `ecomp_generated_naming` char(1) DEFAULT NULL,\n" +
			"  `avail_zone_max_count` int(11) DEFAULT NULL,\n" +
			"  `nf_function` varchar(255) DEFAULT NULL,\n" +
			"  `nf_code` varchar(255) DEFAULT NULL,\n" +
			"  `nf_type` varchar(255) DEFAULT NULL,\n" +
			"  `nf_role` varchar(255) DEFAULT NULL,\n" +
			"  `vendor` varchar(255) DEFAULT NULL,\n" +
			"  `vendor_version` varchar(255) DEFAULT NULL,\n" +
			"  PRIMARY KEY (`customization_uuid`)\n" +
			")";

	private static final String CRTBL_VF_MODULE_MODEL = "CREATE TABLE `VF_MODULE_MODEL` (\n" +
			"  `customization_uuid` varchar(255) NOT NULL,\n" +
			"  `model_yaml` longblob,\n" +
			"  `invariant_uuid` varchar(255) DEFAULT NULL,\n" +
			"  `uuid` varchar(255) DEFAULT NULL,\n" +
			"  `version` varchar(255) DEFAULT NULL,\n" +
			"  `vf_module_type` varchar(255) DEFAULT NULL,\n" +
			"  `availability_zone_count` int(11) DEFAULT NULL,\n" +
			"  `ecomp_generated_vm_assignments` char(1) DEFAULT NULL,\n" +
			"  PRIMARY KEY (`customization_uuid`)\n" +
			")";

	 private static final Logger LOG = LoggerFactory
	            .getLogger(TestSdncUebCallback.class);
	SdncUebConfiguration config;
	DBResourceManager dblibSvc;
	DB db;
	List<IArtifactInfo > processLevelArtifactList;
	List<IArtifactInfo > serviceLevelArtifactList;
	ArrayList<IResourceInstance> resourceList;
	IArtifactInfo mockProcessArtifact1;
	IArtifactInfo mockProcessArtifact2;
	IArtifactInfo mockProcessArtifact3;
	IArtifactInfo mockServiceArtifact1;
	IResourceInstance resource;
	

	@Before
	public void setUp() throws Exception {
		config = new SdncUebConfiguration("src/test/resources");


		URL propUrl = getClass().getResource("/dblib.properties");

		InputStream propStr = getClass().getResourceAsStream("/dblib.properties");

		Properties props = new Properties();

		props.load(propStr);


		// Start MariaDB4j database
		DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
		config.setPort(0); // 0 => autom. detect free port
		db = DB.newEmbeddedDB(config.build());
		db.start();


		// Override jdbc URL and database name
		props.setProperty("org.onap.ccsdk.sli.jdbc.database", "test");
		props.setProperty("org.onap.ccsdk.sli.jdbc.url", config.getURL("test"));


		// Create dblib connection
		dblibSvc = new DBResourceManager(props);

		// Create TOSCA tables
		dblibSvc.writeData(CRTBL_SERVICE_MODEL, null, null);
		dblibSvc.writeData(CRTBL_NETWORK_MODEL, null, null);
		dblibSvc.writeData(CRTBL_VFC_MODEL, null, null);
		dblibSvc.writeData(CRTBL_VF_MODEL, null, null);
		dblibSvc.writeData(CRTBL_VF_MODULE_MODEL, null, null);
		dblibSvc.writeData(CRTBL_ALLOTTED_RESOURCE_MODEL, null, null);
		
		processLevelArtifactList = new ArrayList<>();
		serviceLevelArtifactList = new ArrayList<>();
		resourceList = new ArrayList<>();

		
		mockProcessArtifact1 = mock(IArtifactInfo.class);
		when(mockProcessArtifact1.getArtifactName()).thenReturn("mockProcessArtifact1");
		when(mockProcessArtifact1.getArtifactType()).thenReturn("HEAT");
		when(mockProcessArtifact1.getArtifactURL()).thenReturn("https://asdc.sdc.com/v1/catalog/services/srv1/2.0/resources/aaa/1.0/artifacts/aaa.yml");
		when(mockProcessArtifact1.getArtifactChecksum()).thenReturn("123tfg123 1234ftg");
		when(mockProcessArtifact1.getArtifactTimeout()).thenReturn(110);
		
		mockProcessArtifact2 = mock(IArtifactInfo.class);
		when(mockProcessArtifact1.getArtifactName()).thenReturn("mockProcessArtifact2");
		when(mockProcessArtifact1.getArtifactType()).thenReturn("DG_XML");
		when(mockProcessArtifact1.getArtifactURL()).thenReturn("https://asdc.sdc.com/v1/catalog/services/srv1/2.0/resources/aaa/1.0/artifacts/aaa.yml");
		when(mockProcessArtifact1.getArtifactChecksum()).thenReturn("456jhgt 1234ftg");
		when(mockProcessArtifact1.getArtifactTimeout()).thenReturn(110);
		
		mockProcessArtifact3 = mock(IArtifactInfo.class);
		when(mockProcessArtifact1.getArtifactName()).thenReturn("mockProcessArtifact3");
		when(mockProcessArtifact1.getArtifactType()).thenReturn("HEAT");
		when(mockProcessArtifact1.getArtifactURL()).thenReturn("https://asdc.sdc.com/v1/catalog/services/srv1/2.0/resources/aaa/1.0/artifacts/aaa.yml");
		when(mockProcessArtifact1.getArtifactChecksum()).thenReturn("123tfg123 543gtd");
		when(mockProcessArtifact1.getArtifactTimeout()).thenReturn(110);
		
		
		mockServiceArtifact1 = mock(IArtifactInfo.class);
		when(mockServiceArtifact1.getArtifactName()).thenReturn("mockProcessArtifact4");
		when(mockServiceArtifact1.getArtifactType()).thenReturn("HEAT");
		when(mockServiceArtifact1.getArtifactURL()).thenReturn("https://asdc.sdc.com/v1/catalog/services/srv1/2.0/resources/aaa/1.0/artifacts/aaa.yml");
		when(mockServiceArtifact1.getArtifactChecksum()).thenReturn("123t3455 543gtd");
		when(mockServiceArtifact1.getArtifactTimeout()).thenReturn(110);
		
		resource = mock(IResourceInstance.class);
	}

	@After
	public void tearDown() throws Exception {
		// Move anything in archive back to incoming
        String curFileName = "";

        Path incomingPath = new File(config.getIncomingDir()).toPath();
        File archiveDir = new File(config.getArchiveDir());
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(archiveDir.toPath())) {
            for (Path file: stream) {
            		Files.move(file, incomingPath.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            LOG.warn("Cannot replace spool file {}", curFileName, x);
        }

        db.stop();

	}

	@Test
	public void test() {

		IDistributionClient iDistClient = mock(IDistributionClient.class);
		SdncUebCallback cb = new SdncUebCallback(iDistClient, config);
		cb.setJdbcDataSource(dblibSvc);

		INotificationData iData = mock(INotificationData.class);
		cb.activateCallback(iData);
	}
	
	
	
	@Test
	public void testServiceAndProcessArtifactsactivateCallback() {

		try {
		processLevelArtifactList.add(mockProcessArtifact1);
		processLevelArtifactList.add(mockProcessArtifact2);
		processLevelArtifactList.add(mockProcessArtifact3);
		
		resourceList.add(resource);
		serviceLevelArtifactList.add(mockServiceArtifact1);
		when(resource.getArtifacts()).thenReturn(serviceLevelArtifactList);
		when(resource.getResourceName()).thenReturn("Resource_service_name");


		IDistributionClient iDistClient1 = mock(IDistributionClient.class);
		INotificationData mockData = mock(INotificationData.class);
		when(mockData.getResources()).thenReturn(resourceList);
		when(mockData.getServiceName()).thenReturn("Test_service_name");
		when(mockData.getServiceArtifacts()).thenReturn(processLevelArtifactList);
		
		SdncUebCallback cb1 = new SdncUebCallback(iDistClient1, config);
		cb1.activateCallback(mockData);
		assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
