/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 *             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.ccsdk.sli.northbound.dmaapclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ANRChangesFromPolicyToSDNRDmaapConsumer extends SdncDmaapConsumerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(ANRChangesFromPolicyToSDNRDmaapConsumer.class);
    private static final String SDNC_ENDPOINT = "SDNC.endpoint";
    private static final String TEMPLATE = "SDNC.template";
    private static final String DMAAPLISTENERROOT = "DMAAPLISTENERROOT";

    private static final String PARAMETER_NAME = "parameter-name";
    private static final String STRING_VALUE = "string-value";
    private static final String GENERIC_NEIGHBOR_CONFIGURATION_INPUT = "generic-neighbor-configuration-input.";
    private static final String GENERIC_NEIGHBOR_CONFIGURATION_INPUT_NEIGHBOR_LIST_IN_USE = GENERIC_NEIGHBOR_CONFIGURATION_INPUT.concat("neighbor-list-in-use");
    private static final String EVENT_HEADER = "event-header";
	private static final String ACTION = "Action";
	private static final String PAYLOAD = "Payload";
	private static final String CONFIGURATIONS = "Configurations";
	private static final String MODIFY_CONFIG_ANR = "ModifyConfigANR";
	private static final String MAP_FILE_NAME = "anr-changes-from-policy-to-sdnr";
	private static final String DATA = "data";
	private static final String FAP_SERVICE = "FAPService";
	private static final String SLI_PARAMETERS = "sli_parameters";
	private static final String RPC_NAME = "rpc-name";
	private static final String BODY = "body";
	private static final String INPUT = "input";

    private String rootDir;

    protected VelocityEngine velocityEngine;

    public ANRChangesFromPolicyToSDNRDmaapConsumer() {
        velocityEngine = new VelocityEngine();
        Properties props = new Properties();
        rootDir = System.getenv(DMAAPLISTENERROOT);

        if ((rootDir == null) || (rootDir.length() == 0)) {
            rootDir = "/opt/app/dmaap-listener/lib/";
        }
        else {
            rootDir = rootDir + "/lib/";
        }

        props.put("file.resource.loader.path", rootDir);
        velocityEngine.init(props);
    }

    /*
     * for testing purposes
     */
    ANRChangesFromPolicyToSDNRDmaapConsumer(Properties props) {
        velocityEngine = new VelocityEngine();
        velocityEngine.init(props);
    }

    protected String publish(String templatePath, String jsonString, JsonNode dataNode) throws IOException, InvalidMessageException
    {
        if (templatePath.contains("anr-pci-changes-from-policy-to-sdnr")){
            return publishANRChangesFromPolicyToSDNR(templatePath, dataNode);
        } else {
            return publishFullMessage(templatePath, jsonString);
        }
    }

    private String publishFullMessage(String templatePath, String jsonString) throws IOException
    {
        JSONObject jsonObj = new JSONObject(jsonString);
        VelocityContext context = new VelocityContext();
        for(Object key : jsonObj.keySet())
        {
            context.put((String)key, jsonObj.get((String)key));
        }

        String id = jsonObj.getJSONObject(EVENT_HEADER).get("id").toString();
        context.put("req_id", id);

        context.put("curr_time", Instant.now());

        ObjectMapper oMapper = new ObjectMapper();

        String rpcMsgbody = oMapper.writeValueAsString(jsonString);
        context.put("full_message", rpcMsgbody);

        Writer writer = new StringWriter();
        velocityEngine.mergeTemplate(templatePath, "UTF-8", context, writer);
        writer.flush();

        return writer.toString();
    }

    private String publishANRChangesFromPolicyToSDNR(String templatePath, JsonNode dataNode) throws IOException, InvalidMessageException
    {
    	VelocityContext context = new VelocityContext();
		
		String RPC_NAME_KEY_IN_VT = "rpc_name";
    	String RPC_NAME_VALUE_IN_VT = "generic-neighbor-configuration";
    	
    	String CELL_CONFIG = "CellConfig";
    	String ALIAS_LABEL = "alias";
    	String LTE = "LTE";
    	String RAN = "RAN";
    	String LTE_CELL = "LTECell";
    	String NEIGHBOR_LIST_IN_USE = "NeighborListInUse";
    	
        JSONObject numberOfEntries = new JSONObject();
        JSONObject alias = new JSONObject();
        JSONArray sliParametersArray = new JSONArray();
        
        String aliasValue =  dataNode.get(DATA).get(FAP_SERVICE).get(ALIAS_LABEL).textValue();
        
        JsonNode nbrListInUse = dataNode.get(DATA).get(FAP_SERVICE).get(CELL_CONFIG).get(LTE).get(RAN).get(NEIGHBOR_LIST_IN_USE).get(LTE_CELL);
        
        int entryCount = 0;
        
        if(nbrListInUse.isArray()) {
        	for(JsonNode lteCell:nbrListInUse) {
        		sliParametersArray.put(new JSONObject().put(PARAMETER_NAME, GENERIC_NEIGHBOR_CONFIGURATION_INPUT_NEIGHBOR_LIST_IN_USE+"["+entryCount+"]."+"plmnid")
            			.put(STRING_VALUE, lteCell.get("PLMNID")));
        		sliParametersArray.put(new JSONObject().put(PARAMETER_NAME, GENERIC_NEIGHBOR_CONFIGURATION_INPUT_NEIGHBOR_LIST_IN_USE+"["+entryCount+"]."+"cid")
            			.put(STRING_VALUE, lteCell.get("CID")));
        		sliParametersArray.put(new JSONObject().put(PARAMETER_NAME, GENERIC_NEIGHBOR_CONFIGURATION_INPUT_NEIGHBOR_LIST_IN_USE+"["+entryCount+"]."+"phy-cell-id")
            			.put(STRING_VALUE, lteCell.get("PhyCellID")));
        		sliParametersArray.put(new JSONObject().put(PARAMETER_NAME, GENERIC_NEIGHBOR_CONFIGURATION_INPUT_NEIGHBOR_LIST_IN_USE+"["+entryCount+"]."+"pnf-name")
            			.put(STRING_VALUE, lteCell.get("PNFName")));
        		sliParametersArray.put(new JSONObject().put(PARAMETER_NAME, GENERIC_NEIGHBOR_CONFIGURATION_INPUT_NEIGHBOR_LIST_IN_USE+"["+entryCount+"]."+"blacklisted")
            			.put(STRING_VALUE, lteCell.get("Blacklisted")));
        		
        		entryCount++;
        	}
        	
        	alias.put(PARAMETER_NAME, GENERIC_NEIGHBOR_CONFIGURATION_INPUT+ALIAS_LABEL);
            alias.put(STRING_VALUE, aliasValue);
            
            numberOfEntries.put(PARAMETER_NAME, GENERIC_NEIGHBOR_CONFIGURATION_INPUT+"number-of-neighbor-cell-entries");
            numberOfEntries.put(STRING_VALUE, entryCount);
            
            sliParametersArray.put(alias);
            sliParametersArray.put(numberOfEntries);
            
            context.put(SLI_PARAMETERS, sliParametersArray);
            
            context.put(RPC_NAME_KEY_IN_VT, RPC_NAME_VALUE_IN_VT);

            Writer writer = new StringWriter();
            velocityEngine.mergeTemplate(templatePath, "UTF-8", context, writer);
            writer.flush();

            return writer.toString();
        	
        }else {
        	throw new InvalidMessageException("nbrListInUse is not of Type Array. Could not read neighbor list elements");
        }
        
    }

    @Override
    public void processMsg(String msg) throws InvalidMessageException {

    	if (msg == null) {
            throw new InvalidMessageException("Null message");
        }

        ObjectMapper oMapper = new ObjectMapper();
        JsonNode anrChangesRootNode;
        try {
        	anrChangesRootNode = oMapper.readTree(msg);
        } catch (Exception e) {
            throw new InvalidMessageException("Cannot parse json object", e);
        }

        JsonNode rpcname = anrChangesRootNode.get(RPC_NAME);
        if(rpcname == null) {
        	 LOG.info("Missing rpc-name node.");
        	 return;
        }
        
        if(!MODIFY_CONFIG_ANR.toLowerCase().equals(rpcname.textValue())) {
            LOG.info("Unknown rpc name {}", rpcname);
            return;
        }
        
        JsonNode body = anrChangesRootNode.get(BODY);
        if(body == null) {
        	 LOG.info("Missing body node.");
        	 return;
        }
        
        JsonNode input = body.get(INPUT);
        if(input == null) {
        	 LOG.info("Missing input node.");
        	 return;
        }
        
        JsonNode action = input.get(ACTION);
        if(action == null) {
        	 LOG.info("Missing action node.");
        	 return;
        }
        
        if(!MODIFY_CONFIG_ANR.equals(action.textValue())) {
            LOG.info("Unknown Action {}", action);
            return;
        }
        
        JsonNode payload = input.get(PAYLOAD);
        if(payload == null) {
            LOG.info("Missing payload node.");
            return;
        }

        String payloadText = payload.asText();
        
        if(!payloadText.contains(CONFIGURATIONS)) {
       	 LOG.info("Missing configurations node.");
       	 return;
       }
        
       JsonNode configurationsJsonNode;
	    try {
	    	configurationsJsonNode = oMapper.readTree(payloadText);
	    } catch (Exception e) {
	        throw new InvalidMessageException("Cannot parse payload value", e);
	    }

        String mapFilename = rootDir + MAP_FILE_NAME + ".map";
        Map<String, String> fieldMap = loadMap(mapFilename);
        if (fieldMap == null) {
            return;
        }

        if (!fieldMap.containsKey(SDNC_ENDPOINT)) {
            return;
        }
        String sdncEndpoint = fieldMap.get(SDNC_ENDPOINT);

        if (!fieldMap.containsKey(TEMPLATE)) {
            throw new InvalidMessageException("No SDNC template known for message ");
        }
        String templateName = fieldMap.get(TEMPLATE);
        
        JsonNode configurations = configurationsJsonNode.get(CONFIGURATIONS);
        
        if(configurations.isArray()) {
        	for(JsonNode dataNode:configurations) {
    	        if(dataNode.get(DATA).get(FAP_SERVICE) == null) {
    	            LOG.info("Could not make a rpc call. Missing fapService node for dataNode element::", dataNode.textValue());
    	        }else {
    	        	try {
            	        
                        String rpcMsgbody = publish(templateName, msg, dataNode);
                        String odlUrlBase = getProperty("sdnc.odl.url-base");
                        String odlUser = getProperty("sdnc.odl.user");
                        String odlPassword = getProperty("sdnc.odl.password");

                        if ((odlUrlBase != null) && (odlUrlBase.length() > 0)) {
                            SdncOdlConnection conn = SdncOdlConnection.newInstance(odlUrlBase + "/" + sdncEndpoint, odlUser, odlPassword);

                            conn.send("POST", "application/json", rpcMsgbody);
                        } else {
                            LOG.info("POST message body would be:\n" + rpcMsgbody);
                        }
                    } catch (Exception e) {
                        LOG.error("Unable to process message", e);
                    }
    	        }
        	}
        }else {
        	throw new InvalidMessageException("Configurations is not of Type Array. Could not read configuration changes");
        }
    }

    private Map<String, String> loadMap(String mapFilename) {
        File mapFile = new File(mapFilename);

        if (!mapFile.canRead()) {
            LOG.error(String.format("Cannot read map file (%s)", mapFilename));
            return null;
        }

        Map<String, String> results = new HashMap<>();
        try (BufferedReader mapReader = new BufferedReader(new FileReader(mapFile))) {

            String curLine;

            while ((curLine = mapReader.readLine()) != null) {
                curLine = curLine.trim();

                if ((curLine.length() > 0) && (!curLine.startsWith("#")) && curLine.contains("=>")) {
                    String[] entry = curLine.split("=>");
                    if (entry.length == 2) {
                        results.put(entry[0].trim(), entry[1].trim());
                    }
                }
            }
            mapReader.close();
        } catch (Exception e) {
            LOG.error("Caught exception reading map " + mapFilename, e);
            return null;
        }

        return results;
    }

}
