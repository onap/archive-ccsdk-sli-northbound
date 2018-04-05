package org.onap.ccsdk.sli.northbound.uebclient; 
 
import static org.junit.Assert.*; 
 
import static org.mockito.Mockito.*; 
 
import org.junit.Before; 
import org.junit.Test; 
import org.onap.sdc.tosca.parser.api.ISdcCsarHelper; 
import org.onap.sdc.toscaparser.api.NodeTemplate; 
 
public class SdncNodeModelTest { 
 
	SdncNodeModel sdncNodeModel; 
 
	@Before 
	public void setUp() throws Exception { 
		ISdcCsarHelper isdcCsarHelper = mock(ISdcCsarHelper.class); 
		NodeTemplate nodeTemplate = mock(NodeTemplate.class); 
		sdncNodeModel = new SdncNodeModel(isdcCsarHelper, nodeTemplate); 
		sdncNodeModel.setServiceUUID("0e8d757f-1c80-40af-85de-31d64f1f5af8"); 
		sdncNodeModel.setEcompGeneratedNaming("hello-world"); 
	} 
 
	@Test 
	public void testGetServiceUUID() { 
		String result = sdncNodeModel.getServiceUUID(); 
		assertNotNull(result != null); 
	} 
 
	@Test 
	public void testGetEcompGeneratedNaming() { 
		String result = sdncNodeModel.getEcompGeneratedNaming(); 
		assertEquals("hello-world", result); 
	} 
 
	@Test 
	public void testGetSqlString() { 
		String result = sdncNodeModel.getSql("TEST-HELLO"); 
		String test = "INSERT into NETWORK_MODEL (service_uuid, customization_uuid, model_yaml, ecomp_generated_naming) values (0e8d757f-1c80-40af-85de-31d64f1f5af8, \"\", \"TEST-HELLO\", \"hello-world\");"; 
		assertEquals(test, result); 
	} 
 
	@Test 
	public void testGetVpnBindingsSql() { 
		String result = sdncNodeModel.getVpnBindingsSql(); 
		assertNotNull(result); 
	} 
}
