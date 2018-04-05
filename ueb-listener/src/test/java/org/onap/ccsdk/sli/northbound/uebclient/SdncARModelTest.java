package org.onap.ccsdk.sli.northbound.uebclient;
 
 import static org.junit.Assert.*;
 import static org.mockito.Mockito.*;
 
 import org.junit.Test;
 import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
 import org.onap.sdc.toscaparser.api.NodeTemplate;
 
 public class SdncARModelTest {
 
 	@Test
 	public void testSdncARModelConstructor() {
 		ISdcCsarHelper mockCsarHelper = mock(ISdcCsarHelper.class);
 		NodeTemplate nodeTemplate = mock(NodeTemplate.class);
 		SdncARModel testSdncARModel = new SdncARModel(mockCsarHelper,nodeTemplate);
 		assertNotNull(testSdncARModel);
 	}
 
 }
 
