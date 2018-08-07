package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm;

import java.io.IOException;
import java.util.List;

import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.PolicyConfig;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.PolicyData;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.PolicyEngineResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.PolicyProperty;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.ResourceModel;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.rest.SecureRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sun.jersey.api.client.ClientResponse;

public class PolicyManagerClient {

    private static Logger log = LoggerFactory.getLogger(PolicyManagerClient.class);

    public synchronized List<ResourceModel> getPolicy(final String policyName) throws Exception {

        SecureRestClient srclient = getSecureRestClient();
        List<ResourceModel> resourceModels = null;
        log.info("SecureRestClient created..");

        PolicyPropertiesReader propReader = new PolicyPropertiesReader();
        PolicyProperty prop = propReader.getPolicyProperties();

        String input = "{\"onapName\":\"SDNC\",\"policyName\":\"" + policyName + "\", \"unique\":\"true\"}";

        ClientResponse response = srclient.sendRequest(input, prop);

        PolicyEngineResponse[] responseEntity = response.getEntity(PolicyEngineResponse[].class);
        if (responseEntity != null && responseEntity.length > 0) {
            PolicyEngineResponse policyResponse = responseEntity[0];
            resourceModels = extractResourceModelsFromResponse(responseEntity);

            log.info(policyResponse.getConfig());
            log.info(resourceModels != null ? resourceModels.toString() : "resourceModels not found or null");
        }

        return resourceModels;
    }

    public List<ResourceModel> extractResourceModelsFromResponse(PolicyEngineResponse[] result)
            throws JsonParseException, JsonMappingException, IOException {

        List<ResourceModel> retVal = null;
        if (null != result && result.length > 0) {

            // There will be only one element in the result - confirmed with PDP developer
            // Also, due to escaped duble quoted strings in the JSON, the config element
            // will be deserialized
            // as a String rather than as PolicyConfig. Which is good anyway for our
            // processing, but FYI
            // So , get the String and separately pass it through a new ObjectMapper
            String configValue = result[0].getConfig();
            ObjectMapper om = getConfigDeserializerObjectMapper();
            PolicyConfig config = om.readValue(configValue, PolicyConfig.class);
            retVal = config.getContent().getResourceModels();
        }
        return retVal;
    }

    private ObjectMapper getConfigDeserializerObjectMapper() {

        // We need a special deserializer for Policy data. Depending on policy name sent
        // as input, the policy data returned
        // differs not only in content, but in structure too. In other words,
        // Polymorphism!!! There will be a(n) abstract
        // PolicyData and one child each for each policy. The SOLID ASSUMPTION here is
        // that there is one unique attribute in each
        // policy to distinguish it from one another. e.g. nf-role in subintf and
        // subnet-use in subnet--assign
        // If that turns out to be wrong, we will need another desrialization policy and
        // corresponding new deserializer

        PolicyDataDeserializer deserializer = new PolicyDataDeserializer();
        deserializer.registerExtendersByUniqueness("key-type", ResourceModel.class);

        SimpleModule module = new SimpleModule("PolymorphicPolicyDataDeserializerModule",
                new Version(1, 0, 0, null, null, null));
        module.addDeserializer(PolicyData.class, deserializer);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(module);

        // This is needed so subnetassign policy which returns only a single element can
        // be treated similar
        // to subintf selection policy which returns a list.
        om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        return om;
    }

    public SecureRestClient getSecureRestClient() {
        return new SecureRestClient();
    }

}
