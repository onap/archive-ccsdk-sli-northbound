package org.onap.ccsdk.sli.northbound.dmaapclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestPciChangesFromPolicyToSDNRDmaapConsumer {
    private static final String pciChangesFromPolicyToSDNRInput = "{\n" + 
    		"	\"body\": {\n" + 
    		"		\"input\": {\n" + 
    		"			\"CommonHeader\": {\n" + 
    		"				\"TimeStamp\": \"2018-11-30T09:13:37.368Z\",\n" + 
    		"				\"APIVer\": \"1.0\",\n" + 
    		"				\"RequestID\": \"9d2d790e-a5f0-11e8-98d0-529269fb1459\",\n" + 
    		"				\"SubRequestID\": \"1\",\n" + 
    		"				\"RequestTrack\": {},\n" + 
    		"				\"Flags\": {}\n" + 
    		"			},\n" + 
    		"			\"Action\": \"ModifyConfig\",\n" + 
    		"			\"Payload\": \"{\\\"Configurations\\\":[{\\\"data\\\":{\\\"FAPService\\\":{\\\"alias\\\":\\\"Chn0330\\\",\\\"X0005b9Lte\\\":{\\\"phyCellIdInUse\\\":6,\\\"pnfName\\\":\\\"ncserver23\\\"},\\\"CellConfig\\\":{\\\"LTE\\\":{\\\"RAN\\\":{\\\"Common\\\":{\\\"CellIdentity\\\":\\\"Chn0330\\\"}}}}}}},{\\\"data\\\":{\\\"FAPService\\\":{\\\"alias\\\":\\\"Chn0331\\\",\\\"X0005b9Lte\\\":{\\\"phyCellIdInUse\\\":7,\\\"pnfName\\\":\\\"ncserver23\\\"},\\\"CellConfig\\\":{\\\"LTE\\\":{\\\"RAN\\\":{\\\"Common\\\":{\\\"CellIdentity\\\":\\\"Chn0331\\\"}}}}}}}]}\"\n" + 
    		"		}\n" + 
    		"	},\n" + 
    		"	\"version\": \"1.0\",\n" + 
    		"	\"rpc-name\": \"modifyconfig\",\n" + 
    		"	\"correlation-id\": \"9d2d790e-a5f0-11e8-98d0-529269fb1459-1\",\n" + 
    		"	\"type\": \"request\"\n" + 
    		"}";
    
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRPCMessageBodyResponse() throws Exception {
		Properties props = new Properties();
		
		ObjectMapper oMapper = new ObjectMapper();
		JsonNode pciChangesRootNode = oMapper.readTree(pciChangesFromPolicyToSDNRInput);
		JsonNode body = pciChangesRootNode.get("body");
		JsonNode input = body.get("input");
		JsonNode payload = input.get("Payload");
		String payloadText = payload.asText();
		JsonNode configurationsJsonNode = oMapper.readTree(payloadText);
 
	    String rpcMsgbody = new PciChangesFromPolicyToSDNRDmaapConsumer(props).publish("src/main/resources/anr-pci-changes-from-policy-to-sdnr.vt", pciChangesFromPolicyToSDNRInput,configurationsJsonNode);
        
        JsonNode rootNode;
        try {
        	rootNode = oMapper.readTree(rpcMsgbody);
        } catch (Exception e) {
            throw new InvalidMessageException("Cannot parse json object", e);
        }       

        assertTrue(rootNode.get("input").get("module-name") != null); 
        assertTrue(rootNode.get("input").get("rpc-name") != null); 
        assertTrue(rootNode.get("input").get("mode") != null); 
        assertTrue(rootNode.get("input").get("sli-parameter") != null); 
        
	}

	@Test(expected = InvalidMessageException.class)
	public void testProcessMsgNullMessage() throws Exception {
		PciChangesFromPolicyToSDNRDmaapConsumer consumer = new PciChangesFromPolicyToSDNRDmaapConsumer();
		consumer.processMsg(null);
	}

	@Test(expected = InvalidMessageException.class)
	public void testProcessMsgInvalidMessage() throws Exception {
		PciChangesFromPolicyToSDNRDmaapConsumer consumer = new PciChangesFromPolicyToSDNRDmaapConsumer();
		consumer.processMsg("test");
	}

	@Test
	public void testProcessMsgMissingActionHeader() throws Exception {
		PciChangesFromPolicyToSDNRDmaapConsumer consumer = new PciChangesFromPolicyToSDNRDmaapConsumer();
		consumer.processMsg("{\n" + 
				"	\"body\": {\n" + 
				"		\"input\": {\n" + 
				"			\"CommonHeader\": {\n" + 
				"				\"TimeStamp\": \"2018-11-30T09:13:37.368Z\",\n" + 
				"				\"APIVer\": \"1.0\",\n" + 
				"				\"RequestID\": \"9d2d790e-a5f0-11e8-98d0-529269fb1459\",\n" + 
				"				\"SubRequestID\": \"1\",\n" + 
				"				\"RequestTrack\": {},\n" + 
				"				\"Flags\": {}\n" + 
				"			},\n" + 
				"			\"RenamedAction\": \"ModifyConfig\",\n" + 
				"			\"Payload\": {\n" + 
				"				\"Configurations \": {\n" + 
				"					\"data \": {\n" + 
				"						\"FAPService \": {\n" + 
				"							\"alias\": \"Chn0330\",\n" + 
				"							\"X0005b9Lte\": {\n" + 
				"								\"phyCellIdInUse\": 6,\n" + 
				"								\"pnfName\": \"ncserver23\"\n" + 
				"							},\n" + 
				"							\"CellConfig\": {\n" + 
				"								\"LTE\": {\n" + 
				"									\"RAN\": {\n" + 
				"										\"Common\": {\n" + 
				"											\"CellIdentity\": \"Chn0330\"\n" + 
				"										}\n" + 
				"									}\n" + 
				"								}\n" + 
				"							}\n" + 
				"						}\n" + 
				"					}\n" + 
				"				}\n" + 
				"\n" + 
				"			}\n" + 
				"		}\n" + 
				"	},\n" + 
				"	\"version\": \"1.0\",\n" + 
				"	\"rpc-name\": \"modifyconfig\",\n" + 
				"	\"correlation-id\": \"9d2d790e-a5f0-11e8-98d0-529269fb1459-1\",\n" + 
				"	\"type\": \"request\"\n" + 
				"}");
	}

	@Test
	public void testProcessMsgInvalidPayloadConfigurations() throws Exception {
		String msg = "{\n" + 
				"	\"body\": {\n" + 
				"		\"input\": {\n" + 
				"			\"CommonHeader\": {\n" + 
				"				\"TimeStamp\": \"2018-11-30T09:13:37.368Z\",\n" + 
				"				\"APIVer\": \"1.0\",\n" + 
				"				\"RequestID\": \"9d2d790e-a5f0-11e8-98d0-529269fb1459\",\n" + 
				"				\"SubRequestID\": \"1\",\n" + 
				"				\"RequestTrack\": {},\n" + 
				"				\"Flags\": {}\n" + 
				"			},\n" + 
				"			\"Action\": \"ModifyConfig\",\n" + 
				"			\"Payload\": {\n" + 
				"				\"Configurations \": {\n" + 
				"					\"data \": {\n" + 
				"						\"FAPService \": {\n" + 
				"							\"alias\": \"Chn0330\",\n" + 
				"							\"X0005b9Lte\": {\n" + 
				"								\"phyCellIdInUse\": 6,\n" + 
				"								\"pnfName\": \"ncserver23\"\n" + 
				"							},\n" + 
				"							\"CellConfig\": {\n" + 
				"								\"LTE\": {\n" + 
				"									\"RAN\": {\n" + 
				"										\"Common\": {\n" + 
				"											\"CellIdentity\": \"Chn0330\"\n" + 
				"										}\n" + 
				"									}\n" + 
				"								}\n" + 
				"							}\n" + 
				"						}\n" + 
				"					}\n" + 
				"				}\n" + 
				"\n" + 
				"			}\n" + 
				"		}\n" + 
				"	},\n" + 
				"	\"version\": \"1.0\",\n" + 
				"	\"rpc-name\": \"modifyconfig\",\n" + 
				"	\"correlation-id\": \"9d2d790e-a5f0-11e8-98d0-529269fb1459-1\",\n" + 
				"	\"type\": \"request\"\n" + 
				"}";
		
		try {
			PciChangesFromPolicyToSDNRDmaapConsumer consumer = new PciChangesFromPolicyToSDNRDmaapConsumer();
			consumer.processMsg(msg);

		} catch (final InvalidMessageException e) {
			final String errorMsg = "Configurations is not of Type Array. Could not read configuration changes";
			assertEquals(errorMsg, e.getMessage());
		}
	}
}
