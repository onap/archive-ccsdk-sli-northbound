package org.onap.ccsdk.sli.northbound.dmaapclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestANRChangesFromPolicyToSDNRDmaapConsumer {
    private static final String anrChangesFromPolicyToSDNRInput = "{\n" +
            "  \"body\": {\n" +
            "    \"input\": {\n" +
            "      \"CommonHeader\": {\n" +
            "        \"TimeStamp\": \"2018-11-30T09:13:37.368Z\",\n" +
            "        \"APIVer\": \"1.0\",\n" +
            "        \"RequestID\": \"722ee65a-8afd-48df-bf57-c152ae45bacc\",\n" +
            "        \"SubRequestID\": \"1\",\n" +
            "        \"RequestTrack\": {},\n" +
            "        \"Flags\": {}\n" +
            "      },\n" +
            "\"Action\": \"ModifyConfigANR\",\n" +
            "      \"Payload\": \"{ \\\"Configurations\\\":[ { \\\"data\\\":{ \\\"FAPService\\\":{ \\\"alias\\\":\\\"Cell1\\\", \\\"CellConfig\\\":{ \\\"LTE\\\":{ \\\"RAN\\\":{ \\\"Common\\\":{ \\\"CellIdentity\\\":\\\"1\\\" }, \\\"NeighborListInUse\\\" : { \\\"LTECellNumberOfEntries\\\" : \\\"1\\\" , \\\"LTECell\\\" : [{ \\\"PLMNID\\\" :\\\"plmnid1\\\", \\\"CID\\\":\\\"Chn0001\\\", \\\"PhyCellID\\\":\\\"3\\\", \\\"PNFName\\\":\\\"ncserver01\\\", \\\"Blacklisted\\\":\\\"false\\\"}] } } } } } } } ] }\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"version\": \"1.0\",\n" +
            "  \"rpc-name\": \"modifyconfiganr\",\n" +
            "  \"correlation-id\": \"722ee65a-8afd-48df-bf57-c152ae45bacc-1\",\n" +
            "  \"type\": \"request\"\n" +
            "}\n" +
            "";
    
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRPCMessageBodyResponse() throws Exception {
		Properties props = new Properties();
		
		ObjectMapper oMapper = new ObjectMapper();
		JsonNode anrChangesRootNode = oMapper.readTree(anrChangesFromPolicyToSDNRInput);
		JsonNode body = anrChangesRootNode.get("body");
		JsonNode input = body.get("input");
		JsonNode payload = input.get("Payload");
		String payloadText = payload.asText();
		JsonNode configurationsJsonNode = oMapper.readTree(payloadText);
		JsonNode configurations = configurationsJsonNode.get("Configurations");
		
		for(JsonNode dataNode:configurations) {
			String rpcMsgbody = new ANRChangesFromPolicyToSDNRDmaapConsumer(props).publish("src/main/resources/anr-pci-changes-from-policy-to-sdnr.vt", anrChangesFromPolicyToSDNRInput,dataNode);
	        
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
	}

	@Test(expected = InvalidMessageException.class)
	public void testProcessMsgNullMessage() throws Exception {
		ANRChangesFromPolicyToSDNRDmaapConsumer consumer = new ANRChangesFromPolicyToSDNRDmaapConsumer();
		consumer.processMsg(null);
	}

	@Test(expected = InvalidMessageException.class)
	public void testProcessMsgInvalidMessage() throws Exception {
		ANRChangesFromPolicyToSDNRDmaapConsumer consumer = new ANRChangesFromPolicyToSDNRDmaapConsumer();
		consumer.processMsg("test");
	}

	@Test
	public void testProcessMsgMissingActionHeader() throws Exception {
		ANRChangesFromPolicyToSDNRDmaapConsumer consumer = new ANRChangesFromPolicyToSDNRDmaapConsumer();
		consumer.processMsg("{\n" +
	            "  \"body\": {\n" +
	            "    \"input\": {\n" +
	            "      \"CommonHeader\": {\n" +
	            "        \"TimeStamp\": \"2018-11-30T09:13:37.368Z\",\n" +
	            "        \"APIVer\": \"1.0\",\n" +
	            "        \"RequestID\": \"722ee65a-8afd-48df-bf57-c152ae45bacc\",\n" +
	            "        \"SubRequestID\": \"1\",\n" +
	            "        \"RequestTrack\": {},\n" +
	            "        \"Flags\": {}\n" +
	            "      },\n" +
	            "\"NoAction\": \"ModifyConfigANR\",\n" +
	            "      \"Payload\": \"{ \\\"Configurations\\\":[ { \\\"data\\\":{ \\\"FAPService\\\":{ \\\"alias\\\":\\\"Cell1\\\", \\\"CellConfig\\\":{ \\\"LTE\\\":{ \\\"RAN\\\":{ \\\"Common\\\":{ \\\"CellIdentity\\\":\\\"1\\\" }, \\\"NeighborListInUse\\\" : { \\\"LTECellNumberOfEntries\\\" : \\\"1\\\" , \\\"LTECell\\\" : [{ \\\"PLMNID\\\" :\\\"plmnid1\\\", \\\"CID\\\":\\\"Chn0001\\\", \\\"PhyCellID\\\":\\\"3\\\", \\\"PNFName\\\":\\\"ncserver01\\\", \\\"Blacklisted\\\":\\\"false\\\"}] } } } } } } } ] }\"\n" +
	            "    }\n" +
	            "  },\n" +
	            "  \"version\": \"1.0\",\n" +
	            "  \"rpc-name\": \"modifyconfiganr\",\n" +
	            "  \"correlation-id\": \"722ee65a-8afd-48df-bf57-c152ae45bacc-1\",\n" +
	            "  \"type\": \"request\"\n" +
	            "}\n" +
	            "");
	}

	@Test
	public void testProcessMsgInvalidPayloadConfigurations() throws Exception {
		String msg = "{\n" +
	            "  \"body\": {\n" +
	            "    \"input\": {\n" +
	            "      \"CommonHeader\": {\n" +
	            "        \"TimeStamp\": \"2018-11-30T09:13:37.368Z\",\n" +
	            "        \"APIVer\": \"1.0\",\n" +
	            "        \"RequestID\": \"722ee65a-8afd-48df-bf57-c152ae45bacc\",\n" +
	            "        \"SubRequestID\": \"1\",\n" +
	            "        \"RequestTrack\": {},\n" +
	            "        \"Flags\": {}\n" +
	            "      },\n" +
	            "\"Action\": \"ModifyConfigANR\",\n" +
	            "      \"Payload\": \"{ \\\"Configurations\\\":{ { \\\"data\\\":{ \\\"FAPService\\\":{ \\\"alias\\\":\\\"Cell1\\\", \\\"CellConfig\\\":{ \\\"LTE\\\":{ \\\"RAN\\\":{ \\\"Common\\\":{ \\\"CellIdentity\\\":\\\"1\\\" }, \\\"NeighborListInUse\\\" : { \\\"LTECellNumberOfEntries\\\" : \\\"1\\\" , \\\"LTECell\\\" : [{ \\\"PLMNID\\\" :\\\"plmnid1\\\", \\\"CID\\\":\\\"Chn0001\\\", \\\"PhyCellID\\\":\\\"3\\\", \\\"PNFName\\\":\\\"ncserver01\\\", \\\"Blacklisted\\\":\\\"false\\\"}} } } } } } } } ] }\"\n" +
	            "    }\n" +
	            "  },\n" +
	            "  \"version\": \"1.0\",\n" +
	            "  \"rpc-name\": \"modifyconfiganr\",\n" +
	            "  \"correlation-id\": \"722ee65a-8afd-48df-bf57-c152ae45bacc-1\",\n" +
	            "  \"type\": \"request\"\n" +
	            "}\n" +
	            "";
		
		try {
			ANRChangesFromPolicyToSDNRDmaapConsumer consumer = new ANRChangesFromPolicyToSDNRDmaapConsumer();
			consumer.processMsg(msg);

		} catch (final InvalidMessageException e) {
			final String errorMsg = "Cannot parse payload value";
			assertEquals(errorMsg, e.getMessage());
		}
	}
}
