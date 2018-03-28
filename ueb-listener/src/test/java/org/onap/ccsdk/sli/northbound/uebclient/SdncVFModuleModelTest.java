package org.onap.ccsdk.sli.northbound.uebclient; 
 
import static org.junit.Assert.*; 
import static org.mockito.Mockito.mock; 
 
import org.junit.Test; 
import org.openecomp.sdc.tosca.parser.api.ISdcCsarHelper; 
import org.openecomp.sdc.toscaparser.api.Group; 
 
public class SdncVFModuleModelTest { 
 
	@Test 
	public void testSdncVFModuleModelConstructor() { 
		Group mockGroup = mock(Group.class); 
		ISdcCsarHelper mockCsarHelper = mock(ISdcCsarHelper.class); 
		SdncVFModuleModel testSdncVFModel = new SdncVFModuleModel(mockCsarHelper, mockGroup); 
		assertNotNull(testSdncVFModel); 
	} 
 
}