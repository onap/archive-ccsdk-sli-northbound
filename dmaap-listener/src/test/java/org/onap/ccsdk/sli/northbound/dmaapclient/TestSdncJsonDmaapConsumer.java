/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.onap.ccsdk.sli.northbound.dmaapclient;

import org.junit.Test;

public class TestSdncJsonDmaapConsumer {
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

        String msg = "{\n" +
                "    \"input\" : {        \n" +
                "    }\n" +
                "}";

        consumer.processMsg(msg);
    }
}
