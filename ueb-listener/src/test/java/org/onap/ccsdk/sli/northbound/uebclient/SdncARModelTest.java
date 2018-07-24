package org.onap.ccsdk.sli.northbound.uebclient;
 
 import static org.junit.Assert.*;
 import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
 import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
 import org.onap.sdc.toscaparser.api.NodeTemplate;
 import org.onap.ccsdk.sli.core.dblib.DBResourceManager;
 
 public class SdncARModelTest {
 
	 SdncARModel testSdncARModel = null;
	 
	 @Before
	 public void setUp() throws Exception {
 		ISdcCsarHelper mockCsarHelper = mock(ISdcCsarHelper.class);
 		NodeTemplate nodeTemplate = mock(NodeTemplate.class);
 		DBResourceManager mockDBResourceManager = mock(DBResourceManager.class);
 		testSdncARModel = new SdncARModel(mockCsarHelper,nodeTemplate,mockDBResourceManager);
 		assertNotNull(testSdncARModel);
 	}
 
	@Test 
	public void testSetGetSubcategory() { 
		String subcategory = "test-subcategory";
		testSdncARModel.setSubcategory(subcategory);
		String result = testSdncARModel.getSubcategory(); 
		assertEquals(subcategory, result); 
	}
	
	@Test 
	public void testInsertAllottedResourceModelData() { 
		try {
			testSdncARModel.insertAllottedResourceModelData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
 }
