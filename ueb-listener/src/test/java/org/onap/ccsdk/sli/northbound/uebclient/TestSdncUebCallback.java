package org.onap.ccsdk.sli.northbound.uebclient;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
import org.openecomp.sdc.api.IDistributionClient;
import org.openecomp.sdc.api.notification.INotificationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;

public class TestSdncUebCallback {

	 private static final Logger LOG = LoggerFactory
	            .getLogger(TestSdncUebCallback.class);
	SdncUebConfiguration config;
	DBResourceManager dblibSvc;
	DB db;

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


		dblibSvc = new DBResourceManager(props);
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

}
