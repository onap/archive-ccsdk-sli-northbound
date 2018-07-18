package org.onap.ccsdk.sli.northbound.uebclient; 
 
import static org.junit.Assert.*; 
import static org.mockito.Mockito.*; 
 
 
import org.junit.Before; 
import org.junit.Test; 
import org.onap.sdc.tosca.parser.api.ISdcCsarHelper; 
import org.onap.sdc.toscaparser.api.NodeTemplate; 
import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
 
public class SdncVFCModelTest { 
 
	SdncVFCModel testSdncVFCModel; 
 
	@Before 
	public void setup() { 
		ISdcCsarHelper mockCsarHelper = mock(ISdcCsarHelper.class); 
		NodeTemplate mockNodeTemplate = mock(NodeTemplate.class); 
		DBResourceManager mockDBResourceManager = mock(DBResourceManager.class); 
		testSdncVFCModel = new SdncVFCModel(mockCsarHelper, mockNodeTemplate, mockDBResourceManager); 
		testSdncVFCModel.setVmType("Test-type"); 
		testSdncVFCModel.setVmCount("5"); 
 
	} 
 
	@Test 
	public void testSdncVFCModelGetVmType() { 
		assertEquals(testSdncVFCModel.getVmType(), "Test-type"); 
	} 
 
	@Test 
	public void testSdncVFCModelGetVmCount() { 
		assertEquals(testSdncVFCModel.getVmCount(), "5"); 
	} 
 
}
