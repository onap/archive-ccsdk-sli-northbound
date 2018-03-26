/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.onap.ccsdk.sli.northbound.dmaapclient;

import org.junit.Test;

public class TestSdncFlatJsonDmaapConsumer {
    private static final String DMAAP_LISTENER_PROPERTIES = "dmaap-listener.properties";
    private static final String DMAAP_LISTENER_PROPERTIES_DIR = "src/test/resources";

    @Test(expected = InvalidMessageException.class)
    public void testProcessMsg_shouldThrowException() throws Exception {
        SdncFlatJsonDmaapConsumer consumer = new SdncFlatJsonDmaapConsumer();
        consumer.processMsg(null);
    }

    @Test(expected = InvalidMessageException.class)
    public void testProcessMsgNullFieldMap_shouldThrowException() throws Exception {
        SdncFlatJsonDmaapConsumer consumer = new SdncFlatJsonDmaapConsumer();

        String msg = "{\"cambria.partition\":\"AAI\",\n" +
                "    \"event-header\":\n" +
                "    {\n" +
                "      \"severity\":\"NORMAL\",\n" +
                "      \"entity-type\":\"esr-thirdparty-sdnc\",\n" +
                "      \"top-entity-type\":\"esr-thirdparty-sdnc\",\n" +
                "      \"entity-link\":\"aai/v11/external-system/esr-thirdparty-sdnc-list/esr-thirdparty-sdnc/IP-WAN-Controller-1\",\n" +
                "      \"event-type\":\"AAI-EVENT\",\n" +
                "      \"domain\":\"dev\",\n" +
                "      \"action\":\"UPDATE\",\n" +
                "      \"sequence-number\":\"0\",\n" +
                "      \"id\":\"bf4df797-759a-4684-a63c-393b7d40ed55\",\n" +
                "      \"source-name\":\"postman\",\n" +
                "      \"version\":\"v11\",\n" +
                "      \"timestamp\":\"20180104-09:57:58:721\"\n" +
                "    },\n" +
                "    \"entity\":\n" +
                "    {\n" +
                "      \"thirdparty-sdnc-id\":\"IP-WAN-Controller-1\",\n" +
                "      \"relationship-list\":\n" +
                "        {\n" +
                "          \"relationship\":\n" +
                "          [\n" +
                "            {\n" +
                "              \"related-to\":\"pnf\",\n" +
                "      \"relationship-data\":\n" +
                "              [\n" +
                "              {\n" +
                "                \"relationship-value\":\"a8098c1a-f86e-11da-bd1a-00112444be1e\",\n" +
                "                \"relationship-key\":\"pnf.pnf-name\"\n" +
                "              }\n" +
                "              ],\n" +
                "              \"related-link\":\"aai/v11/network/pnfs/pnf/a8098c1a-f86e-11da-bd1a-00112444be1e\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "      \"resource-version\":\"1515059878654\",\n" +
                "      \"location\":\"Core\",\n" +
                "      \"product-name\":\"AC-WAN\",\n" +
                "      \"esr-system-info-list\":\n" +
                "      {\"esr-system-info\":\n" +
                "      [\n" +
                "        {\n" +
                "          \"esr-system-info-id\":\"IP-WAN-Controller-ESR-1\",\n" +
                "          \"system-type\":\"example-system-type-val-12078\",\n" +
                "          \"service-url\":\"https://182.2.61.24:18002\",\n" +
                "          \"ssl-cacert\":\"example-ssl-cacert-val-20589\",\n" +
                "          \"type\":\"WAN\",\n" +
                "          \"ssl-insecure\":true,\n" +
                "          \"system-status\":\"example-system-status-val-23435\",\n" +
                "          \"version\":\"V3R1\",\n" +
                "          \"passive\":true,\n" +
                "          \"password\":\"Admin@12345\",\n" +
                "          \"protocol\":\"RESTCONF\",\n" +
                "          \"ip-address\":\"182.2.61.24\",\n" +
                "          \"cloud-domain\":\"example-cloud-domain-val-76077\",\n" +
                "          \"user-name\":\"admin\",\n" +
                "          \"system-name\":\"IP-WAN-Controller\",\n" +
                "          \"port\":\"18002\",\n" +
                "          \"vendor\":\"IP-WAN\",\n" +
                "          \"resource-version\":\"1515059878666\",\n" +
                "          \"remote-path\":\"example-remotepath-val-5833\",\n" +
                "          \"default-tenant\":\"example-default-tenant-val-71148\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                " }";

        consumer.processMsg(msg);
    }
}
