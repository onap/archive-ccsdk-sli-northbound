/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 * 							reserved.
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

package org.onap.ccsdk.sli.northbound.asdcapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.md.sal.binding.test.AbstractConcurrentDataBrokerTest;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.rev170201.VfLicenseModelUpdateInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.rev170201.VfLicenseModelUpdateOutput;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAsdcApiApi extends AbstractConcurrentDataBrokerTest {

    private AsdcApiProvider asdcApiProvider;
    private static final Logger LOG = LoggerFactory.getLogger(AsdcApiProvider.class);

    @Before
    public void setUp() throws Exception {
        if (null == asdcApiProvider) {
            DataBroker dataBroker = getDataBroker();
            NotificationPublishService mockNotification = mock(NotificationPublishService.class);
            RpcProviderRegistry mockRpcRegistry = mock(RpcProviderRegistry.class);
            AsdcApiSliClient mockSliClient = mock(AsdcApiSliClient.class);
            asdcApiProvider = new AsdcApiProvider(dataBroker, mockNotification, mockRpcRegistry, mockSliClient);
        }
    }

    //Testcase should return error 503 when No service logic active for ASDC-API.
    @Test
    public void testVfLicenseModelUpdate() {

        VfLicenseModelUpdateInputBuilder inputBuilder = new VfLicenseModelUpdateInputBuilder();

        inputBuilder.setArtifactName("abc");
        inputBuilder.setArtifactVersion("1");

        // TODO: currently initialize SvcLogicServiceClient is failing, need to fix
        java.util.concurrent.Future<RpcResult<VfLicenseModelUpdateOutput>> future = asdcApiProvider
                                                                          .vfLicenseModelUpdate(inputBuilder.build());
        RpcResult<VfLicenseModelUpdateOutput> rpcResult = null;
        try {
            rpcResult = future.get();
        } catch (Exception e) {
            fail("Error : " + e);
        }
        LOG.info("result: {}", rpcResult);
        assertEquals("503", rpcResult.getResult().getAsdcApiResponseCode());
    }

    //Input parameter validation
    @Test
    public void testVfLicenseModelUpdateInputValidation() {

        VfLicenseModelUpdateInputBuilder inputBuilder = new VfLicenseModelUpdateInputBuilder();

        inputBuilder.setArtifactName("abc");
        inputBuilder.setArtifactVersion("1");

        java.util.concurrent.Future<RpcResult<VfLicenseModelUpdateOutput>> future = asdcApiProvider
                                                                                           .vfLicenseModelUpdate(null);
        assertNull(future);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVfLicenseModelUpdateValidation1() {

        VfLicenseModelUpdateInputBuilder inputBuilder = new VfLicenseModelUpdateInputBuilder();

        java.util.concurrent.Future<RpcResult<VfLicenseModelUpdateOutput>> future = asdcApiProvider
                .vfLicenseModelUpdate(inputBuilder.build());
        RpcResult<VfLicenseModelUpdateOutput> rpcResult = null;
        try {
            rpcResult = future.get();
        } catch (Exception e) {
            fail("Error : " + e);
        }
    }
}
