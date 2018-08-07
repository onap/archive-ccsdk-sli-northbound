package org.onap.ccsdk.sli.northbound.vlantagapi.service.rest;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.PolicyProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class SecureRestClient {

    private static final Logger log = LoggerFactory.getLogger(SecureRestClient.class);

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        };
    }

    public ClientResponse sendRequest(String input, PolicyProperty prop) throws Exception {
        Client client = null;
        ClientResponse response = null;
        WebResource webResource = null;
        SSLContext sslContext = null;
        log.info(input);

        SecureRestClientTrustManager secureRestClientTrustManager = new SecureRestClientTrustManager();
        sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new javax.net.ssl.TrustManager[] { secureRestClientTrustManager }, null);

        DefaultClientConfig defaultClientConfig = new DefaultClientConfig();
        defaultClientConfig.getProperties().put(
                com.sun.jersey.client.urlconnection.HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
                new com.sun.jersey.client.urlconnection.HTTPSProperties(getHostnameVerifier(), sslContext));

        client = Client.create(defaultClientConfig);
        client.setConnectTimeout(5000);

        String tt = "application/json";
        String tt1 = tt + ";charset=UTF-8";

        webResource = client.resource(prop.getUrl());
        WebResource.Builder webResourceBuilder = webResource.accept(tt).type(tt1);

        webResourceBuilder.header("ClientAuth", prop.getClientAuth());
        webResourceBuilder.header("Authorization", prop.getAuthorization());
        webResourceBuilder.header("Environment", prop.getEnvironment());
        webResourceBuilder.header("Content-Type", tt);

        log.info("SecureRestClient post..started");
        response = webResourceBuilder.type("application/json").post(ClientResponse.class, input);

        log.info(response.toString());
        if (response.getStatus() != 200) {
            throw new Exception("Error while retrieving policy from policy manager. ");
        }

        webResource = null;
        client.destroy();
        client = null;

        return response;
    }
}
