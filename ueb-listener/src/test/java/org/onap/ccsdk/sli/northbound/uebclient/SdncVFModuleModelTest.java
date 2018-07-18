package org.onap.ccsdk.sli.northbound.uebclient; 
 
import static org.junit.Assert.*; 
import static org.mockito.Mockito.mock; 
 
import org.junit.Test; 
import org.onap.sdc.tosca.parser.api.ISdcCsarHelper; 
import org.onap.sdc.toscaparser.api.Group; 
import org.onap.ccsdk.sli.northbound.uebclient.SdncVFModel;
 
public class SdncVFModuleModelTest { 
 
	@Test 
	public void testSdncVFModuleModelConstructor() { 
		Group mockGroup = mock(Group.class); 
		ISdcCsarHelper mockCsarHelper = mock(ISdcCsarHelper.class); 
		SdncVFModel mockSdncVFModel = mock(SdncVFModel.class); 
		SdncVFModuleModel testSdncVFModel = new SdncVFModuleModel(mockCsarHelper, mockGroup, mockSdncVFModel); 
		assertNotNull(testSdncVFModel); 
	} 
 
}
