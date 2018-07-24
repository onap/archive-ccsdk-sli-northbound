package org.onap.ccsdk.sli.northbound.uebclient;
 
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
import org.onap.sdc.toscaparser.api.NodeTemplate;
import org.onap.sdc.toscaparser.api.Group;
import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
 
 public class SdncGroupModelTest {
 
 	@Test
 	public void testSdncGroupModelConstructor() {
 		ISdcCsarHelper mockCsarHelper = mock(ISdcCsarHelper.class);
 		NodeTemplate nodeTemplate = mock(NodeTemplate.class);
 		Group group = mock(Group.class);
		DBResourceManager mockDBResourceManager = mock(DBResourceManager.class);
		SdncUebConfiguration mockSdncUebConfiguration = mock(SdncUebConfiguration.class);
 		SdncGroupModel testSdncGroupModel = null;
		try {
			testSdncGroupModel = new SdncGroupModel(mockCsarHelper,group,nodeTemplate,mockSdncUebConfiguration,mockDBResourceManager);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		assertNotNull(testSdncGroupModel);
 	}
 
 }
 
