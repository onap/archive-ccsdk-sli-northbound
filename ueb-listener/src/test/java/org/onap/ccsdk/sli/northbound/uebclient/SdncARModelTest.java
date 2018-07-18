package org.onap.ccsdk.sli.northbound.uebclient;
 
 import static org.junit.Assert.*;
 import static org.mockito.Mockito.*;
 
 import org.junit.Test;
 import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
 import org.onap.sdc.toscaparser.api.NodeTemplate;
 import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
 
 public class SdncARModelTest {
 
 	@Test
 	public void testSdncARModelConstructor() {
 		ISdcCsarHelper mockCsarHelper = mock(ISdcCsarHelper.class);
 		NodeTemplate nodeTemplate = mock(NodeTemplate.class);
 		DBResourceManager mockDBResourceManager = mock(DBResourceManager.class);
 		SdncARModel testSdncARModel = new SdncARModel(mockCsarHelper,nodeTemplate,mockDBResourceManager);
 		assertNotNull(testSdncARModel);
 	}
 
 }
 
