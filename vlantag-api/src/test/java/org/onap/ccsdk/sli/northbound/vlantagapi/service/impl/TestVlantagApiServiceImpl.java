package org.onap.ccsdk.sli.northbound.vlantagapi.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceResponse;
import org.onap.ccsdk.sli.adaptors.rm.data.AllocationStatus;
import org.onap.ccsdk.sli.adaptors.util.str.StrUtil;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.exception.VlantagApiException;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.impl.VlantagApiServiceImpl;

import com.sun.jersey.api.client.ClientResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagRequest;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagRequestInput;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.PingResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagRequest;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagRequestInput;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.PolicyManagerClient;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.AllowedRanges;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.Elements;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.PolicyEngineResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.ResourceModel;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.rest.SecureRestClient;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.util.MockResourceAllocator;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
public class TestVlantagApiServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(TestVlantagApiServiceImpl.class);

    VlantagApiServiceImpl service;
    private VlantagApiServiceImpl serviceSpy;

    protected static PolicyManagerClient policyEngine, policyEngineSpy;
    protected static MockResourceAllocator mockResourceAllocator;
    protected static MockResourceAllocator mockRA;
    protected static AllocationStatus mockStatus;
    protected static ClientResponse mockClientResponse;
    protected static SecureRestClient mockSrclient;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() throws Exception {
        service = new VlantagApiServiceImpl();
        serviceSpy = PowerMockito.spy(service);

        policyEngine = new PolicyManagerClient();
        policyEngineSpy = PowerMockito.spy(policyEngine);
        mockResourceAllocator = new MockResourceAllocator();
        mockRA = PowerMockito.spy(mockResourceAllocator);

        mockClientResponse = mock(ClientResponse.class);
        mockSrclient = mock(SecureRestClient.class);

        PolicyEngineResponse peResponse = new PolicyEngineResponse();
        peResponse.setConfig(
                "{\"riskLevel\":\"4\",\"riskType\":\"test\",\"policyName\":\"SRIOV_VlanTag_1810_ADIOD_VPE\",\"service\":\"vlantagResourceModel\",\"guard\":\"False\",\"description\":\"SRIOV_VlanTag_1810_ADIOD_VPE\",\"templateVersion\":\"1607\",\"priority\":\"4\",\"version\":\"20180709\",\"content\":{\"policy-instance-name\":\"SRIOV_VlanTag_1810_ADIOD_VPE\",\"resource-models\":[{\"data-store\":\"FALSE\",\"elements\":[{\"allowed-range\":[{\"min\":\"3553\",\"max\":\"3562\"}],\"recycle-vlantag-range\":\"TRUE\",\"overwrite\":\"FALSE\",\"vlantag-name\":\"VPE-Cust\"}],\"scope\":\"SITE\",\"vlan-type\":\"vlan-id-outer\",\"resource-resolution-recipe\":\"#BSB# VPE-Cust #ESB#\",\"resource-vlan-role\":\"outer-tag\"},{\"data-store\":\"TRUE\",\"elements\":[{\"allowed-range\":[{\"min\":\"3503\",\"max\":\"3503\"}],\"element-vlan-role\":\"outer-tag\",\"recycle-vlantag-range\":\"TRUE\",\"overwrite\":\"FALSE\",\"vlantag-name\":\"VPE-Cust-Outer\"},{\"allowed-range\":[{\"min\":\"4001\",\"max\":\"4012\"}],\"element-vlan-role\":\"outer-tag\",\"recycle-vlantag-range\":\"TRUE\",\"overwrite\":\"FALSE\",\"vlantag-name\":\"VPE-Core1\"},{\"allowed-range\":[{\"min\":\"4001\",\"max\":\"4012\"}],\"element-vlan-role\":\"outer-tag\",\"recycle-vlantag-range\":\"TRUE\",\"overwrite\":\"FALSE\",\"vlantag-name\":\"VPE-Core2\"}],\"scope\":\"SITE\",\"vlan-type\":\"vlan-id-filter\",\"resource-resolution-recipe\":\"#BSB# VPE-Cust-Outer, VPE-Core1, VPE-Core2 #ESB#\"}]}}");
        PolicyEngineResponse[] peResponses = new PolicyEngineResponse[1];
        peResponses[0] = peResponse;

        PowerMockito.doReturn(policyEngineSpy).when(serviceSpy).getPolicyManagerClient();
        PowerMockito.doReturn(mockSrclient).when(policyEngineSpy).getSecureRestClient();
        PowerMockito.doReturn(mockClientResponse).when(mockSrclient).sendRequest(any(), any());
        PowerMockito.doReturn(peResponses).when(mockClientResponse).getEntity(PolicyEngineResponse[].class);

        serviceSpy.setResourceAllocator(mockRA);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_assign_sucess_001() throws Exception {

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");
        input.setScopeId("some-scope-id");
        input.setKey("some-key");

        AssignVlanTagRequest request = new AssignVlanTagRequest();
        List<AssignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);
        request.setInput(inputs);

        // PowerMockito.doReturn(mockStatus.Success).when(mockRA).reserve(any(), any(),
        // any(), any());
        AssignVlanTagResponse response = serviceSpy.assignVlanTag(request);

        StrUtil.info(log, response);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_unassign_sucess_001() throws Exception {

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");
        input.setKey("some-key");

        UnassignVlanTagRequest request = new UnassignVlanTagRequest();
        List<UnassignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);
        request.setInput(inputs);

        PowerMockito.doReturn(AllocationStatus.Success).when(mockRA).release(any(), any());
        UnassignVlanTagResponse response = serviceSpy.unassignVlanTag(request);

        StrUtil.info(log, response);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_ping_sucess_001() throws Exception {

        PowerMockito.doReturn(AllocationStatus.Success).when(mockRA).release(any(), any());
        PingResponse response = serviceSpy.getPing("Vlantag API Service");

        StrUtil.info(log, response);
        Assert.assertTrue(response.getMessage().contains("Ping response : Vlantag API Service Time : "));
    }

    @Test
    public void test_resolveRecipe_001() {

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, #BSB# VPE-Core2 #ESB#, VPE-Core3 #ESB#");
        // model.setResourceResolutionRecipe("[ VPE-Core1, [ VPE-Core2 ], VPE-Core3 ]");

        List<ResourceResponse> rl = new ArrayList<>();
        ResourceResponse response = new ResourceResponse();
        response.endPointPosition = "VPE-Core1";
        response.resourceAllocated = "3901";

        rl.add(response);

        response = new ResourceResponse();
        response.endPointPosition = "VPE-Core2";
        response.resourceAllocated = "3902";

        rl.add(response);

        response = new ResourceResponse();
        response.endPointPosition = "VPE-Core3";
        response.resourceAllocated = "3903";

        rl.add(response);

        service.resolveRecipe(model, rl);

    }

    @Test
    public void test_resolveResourceElementValue_001() throws VlantagApiException {

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123, 123, 234 ]");
        input.setScopeId("scope-id");
        input.setVlanType("vlan-type");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, VPE-Core2, VPE-Core3 #ESB#");
        // model.setResourceResolutionRecipe("[ VPE-Core1, [ VPE-Core2 ], VPE-Core3 ]");

        List<ResourceResponse> rl = new ArrayList<>();
        ResourceResponse response = new ResourceResponse();
        response.endPointPosition = "VPE-Core1";
        response.resourceAllocated = "3901";

        rl.add(response);

        response = new ResourceResponse();
        response.endPointPosition = "VPE-Core2";
        response.resourceAllocated = "3902";

        rl.add(response);

        response = new ResourceResponse();
        response.endPointPosition = "VPE-Core3";
        response.resourceAllocated = "3903";

        rl.add(response);

        String resourceValue = service.resolveResourceElementValue(input, model, element);

        Assert.assertTrue(resourceValue.equals("123"));
    }

    @Test(expected = VlantagApiException.class)
    public void test_resolveResourceElementValue_002() throws VlantagApiException {

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123]");
        input.setScopeId("scope-id");
        input.setVlanType("vlan-type");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, VPE-Core2, VPE-Core3 #ESB#");
        // model.setResourceResolutionRecipe("[ VPE-Core1, [ VPE-Core2 ], VPE-Core3 ]");

        List<ResourceResponse> rl = new ArrayList<>();
        ResourceResponse response = new ResourceResponse();
        response.endPointPosition = "VPE-Core1";
        response.resourceAllocated = "3901";

        rl.add(response);

        response = new ResourceResponse();
        response.endPointPosition = "VPE-Core2";
        response.resourceAllocated = "3902";

        rl.add(response);

        response = new ResourceResponse();
        response.endPointPosition = "VPE-Core3";
        response.resourceAllocated = "3903";

        rl.add(response);

        service.resolveResourceElementValue(input, model, element);
    }

    @Test
    public void test_validateElements_assign_vlantagName_001() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage(
                "Vlantag Name missing for Element in Resource Model Policy for Vlan Type : vlan-id-outer");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123, 123, 234 ]");
        input.setScopeId("scope-id");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        service.validateElements(elements, input);

    }

    @Test
    public void test_validateElements_assign_allowedRanges_002() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage(
                "Allowed Ranges missing for Element in Resource Model Policy for Vlan Type : vlan-id-outer");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123, 123, 234 ]");
        input.setScopeId("scope-id");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        service.validateElements(elements, input);

    }

    @Test
    public void test_validateElements_assign_vlantagElements_003() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("No Vlantag Elements found in Resource Model Policy for Vlan Type : vlan-id-outer");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123, 123, 234 ]");
        input.setScopeId("scope-id");

        List<Elements> elements = new ArrayList<>();

        service.validateElements(elements, input);

    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_validateElements_assign_Success_004() throws VlantagApiException {

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123, 123, 234 ]");
        input.setScopeId("scope-id");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        List<AllowedRanges> allowedRanges = new ArrayList<>();
        AllowedRanges range = new AllowedRanges();
        range.setMin("200");
        range.setMax("300");
        allowedRanges.add(range);
        element.setAllowedRanges(allowedRanges);

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        service.validateElements(elements, input);

    }

    @Test
    public void test_validateElements_unassign_vlantagName_001() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage(
                "Vlantag Name missing for Element in Resource Model Policy for Vlan Type : vlan-id-outer");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        service.validateElements(elements, input);

    }

    @Test
    public void test_validateElements_unassign_vlantagElements_003() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("No Vlantag Elements found in Resource Model Policy for Vlan Type : vlan-id-outer");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");

        List<Elements> elements = new ArrayList<>();

        service.validateElements(elements, input);

    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_validateElements_unassign_Success_004() throws VlantagApiException {

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        List<AllowedRanges> allowedRanges = new ArrayList<>();
        AllowedRanges range = new AllowedRanges();
        range.setMin("200");
        range.setMax("300");
        allowedRanges.add(range);
        element.setAllowedRanges(allowedRanges);

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        service.validateElements(elements, input);

    }

    @Test
    public void test_validateModel_assign_resourceModel_001() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("No Matching Policy Resource Model found for Vlan Type : vlan-id-outer");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123]");
        input.setScopeId("scope-id");

        service.validateModel(null, input);
    }

    @Test
    public void test_validateModel_assign_resolutionRecipe_002() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("Resource Resolution Recipe is null in Resource Model for Vlan Type : vlan-id-outer");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123]");
        input.setScopeId("scope-id");

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");

        service.validateModel(model, input);
    }

    @Test
    public void test_validateModel_assign_scope_003() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("Scope is null in Resource Model for Vlan Type : vlan-id-outer");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123]");
        input.setScopeId("scope-id");

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, VPE-Core2, VPE-Core3 #ESB#");

        service.validateModel(model, input);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_validateModel_assign_success_004() throws VlantagApiException {

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123]");
        input.setScopeId("scope-id");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        List<AllowedRanges> allowedRanges = new ArrayList<>();
        AllowedRanges range = new AllowedRanges();
        range.setMin("200");
        range.setMax("300");
        allowedRanges.add(range);
        element.setAllowedRanges(allowedRanges);

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, VPE-Core2, VPE-Core3 #ESB#");
        model.setScope("VPE");
        model.setElements(elements);

        service.validateModel(model, input);
    }

    @Test
    public void test_validateModel_unassign_resourceModel_001() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("No Matching Policy Resource Model found for Vlan Type : vlan-id-outer");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");

        service.validateModel(null, input);
    }

    @Test
    public void test_validateModel_unassign_resolutionRecipe_002() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("Resource Resolution Recipe is null in Resource Model for Vlan Type : vlan-id-outer");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");

        service.validateModel(model, input);
    }

    @Test
    public void test_validateModel_unassign_scope_003() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("Scope is null in Resource Model for Vlan Type : vlan-id-outer");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, VPE-Core2, VPE-Core3 #ESB#");

        service.validateModel(model, input);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_validateModel_unassign_success_004() throws VlantagApiException {

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance-name");
        input.setVlanType("vlan-id-outer");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        List<AllowedRanges> allowedRanges = new ArrayList<>();
        AllowedRanges range = new AllowedRanges();
        range.setMin("200");
        range.setMax("300");
        allowedRanges.add(range);
        element.setAllowedRanges(allowedRanges);

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-inner");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, VPE-Core2, VPE-Core3 #ESB#");
        model.setScope("VPE");
        model.setElements(elements);

        service.validateModel(model, input);
    }

    @Test
    public void test_validateRequest_assign_request_001() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Assign Request is null.");

        AssignVlanTagRequest request = null;
        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_assign_requestInput_002() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Assign Request Input is null or empty.");

        AssignVlanTagRequest request = new AssignVlanTagRequest();

        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_assign_policyInstanceName_003() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Assign Request policy-instance-name is null.");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123]");
        input.setScopeId("scope-id");
        input.setVlanType("vlan-type");

        AssignVlanTagRequest request = new AssignVlanTagRequest();
        List<AssignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_assign_resourceName_004() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Assign Request vlan-type is null.");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance");
        input.setResourceValue("[ 123]");
        input.setScopeId("scope-id");

        AssignVlanTagRequest request = new AssignVlanTagRequest();
        List<AssignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_assign_scopeId_005() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Assign Request scope-id is null.");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");
        input.setResourceValue("[ 123]");
        input.setVlanType("vlan-type");

        AssignVlanTagRequest request = new AssignVlanTagRequest();
        List<AssignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_assign_resourceKey_006() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Assign Request key is null.");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");
        input.setScopeId("scope-id");
        input.setResourceValue("[ 123]");
        input.setVlanType("vlan-type");

        AssignVlanTagRequest request = new AssignVlanTagRequest();
        List<AssignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_validateRequest_assign_success_007() throws VlantagApiException {

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");
        input.setScopeId("scope-id");
        input.setResourceValue("[ 123]");
        input.setVlanType("vlan-type");
        input.setKey("some-key");

        AssignVlanTagRequest request = new AssignVlanTagRequest();
        List<AssignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_unassign_request_001() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Unassign Request is null.");

        UnassignVlanTagRequest request = null;
        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_unassign_requestInput_002() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Unassign Request Input is null or empty.");

        UnassignVlanTagRequest request = new UnassignVlanTagRequest();
        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_unassign_policyInstanceName_003() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Unassign Request policy-instance-name is null.");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setVlanType("vlan-id-outer");

        UnassignVlanTagRequest request = new UnassignVlanTagRequest();
        List<UnassignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_unassign_resourceName_004() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Unassign Request resource-name is null.");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance");

        UnassignVlanTagRequest request = new UnassignVlanTagRequest();
        List<UnassignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test
    public void test_validateRequest_unassign_resourceKey_005() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage("VlanTag Unassign Request key is null.");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");

        UnassignVlanTagRequest request = new UnassignVlanTagRequest();
        List<UnassignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_validateRequest_unassign_success_006() throws VlantagApiException {

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setKey("some-key");
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");

        UnassignVlanTagRequest request = new UnassignVlanTagRequest();
        List<UnassignVlanTagRequestInput> inputs = new ArrayList<>();
        inputs.add(input);

        request.setInput(inputs);

        service.validateRequest(request);
    }

    @Test
    public void test_validate_assign_failure_001() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage(
                "No Resource Models available in Policy Manager for Policy Instance Name : some-policy-instance");

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");

        List<ResourceModel> resourceModels = new ArrayList<>();

        service.validate(resourceModels, input);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_validate_assign_sucess_002() throws VlantagApiException {

        AssignVlanTagRequestInput input = new AssignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        List<AllowedRanges> allowedRanges = new ArrayList<>();
        AllowedRanges range = new AllowedRanges();
        range.setMin("200");
        range.setMax("300");
        allowedRanges.add(range);
        element.setAllowedRanges(allowedRanges);

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-outer");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, VPE-Core2, VPE-Core3 #ESB#");
        model.setScope("VPE");
        model.setElements(elements);

        List<ResourceModel> resourceModels = new ArrayList<>();
        resourceModels.add(model);

        service.validate(resourceModels, input);
    }

    @Test
    public void test_validate_unassign_failure_001() throws VlantagApiException {

        expectedEx.expect(VlantagApiException.class);
        expectedEx.expectMessage(
                "No Resource Models available in Policy Manager for Policy Instance Name : some-policy-instance");

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");

        List<ResourceModel> resourceModels = new ArrayList<>();

        service.validate(resourceModels, input);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void test_validate_unassign_sucess_002() throws VlantagApiException {

        UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
        input.setPolicyInstanceName("some-policy-instance");
        input.setVlanType("vlan-id-outer");

        Elements element = new Elements();
        element.setElementVlanRole("element-vlan-role");
        element.setOverwrite("FALSE");
        element.setRecycleVlantagRange("TRUE");
        element.setVlantagName("VPE-Core2");

        List<AllowedRanges> allowedRanges = new ArrayList<>();
        AllowedRanges range = new AllowedRanges();
        range.setMin("200");
        range.setMax("300");
        allowedRanges.add(range);
        element.setAllowedRanges(allowedRanges);

        List<Elements> elements = new ArrayList<>();
        elements.add(element);

        ResourceModel model = new ResourceModel();
        model.setVlanType("vlan-id-outer");
        model.setResourceResolutionRecipe("#BSB# VPE-Core1, VPE-Core2, VPE-Core3 #ESB#");
        model.setScope("VPE");
        model.setElements(elements);

        List<ResourceModel> resourceModels = new ArrayList<>();
        resourceModels.add(model);

        service.validate(resourceModels, input);
    }
}
