package org.onap.ccsdk.sli.northbound.vlantagapi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.onap.ccsdk.sli.adaptors.ra.ResourceAllocator;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceEntity;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceRequest;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceResponse;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceTarget;
import org.onap.ccsdk.sli.adaptors.rm.data.AllocationStatus;
import org.onap.ccsdk.sli.adaptors.rm.data.Range;
import org.onap.ccsdk.sli.adaptors.rm.data.ResourceType;
import org.onap.ccsdk.sli.adaptors.util.str.StrUtil;

import org.onap.ccsdk.sli.northbound.vlantagapi.service.api.VlantagApi;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.exception.VlantagApiException;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagRequest;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagRequestInput;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagResponseOutput;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.PingResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagRequest;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagRequestInput;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagResponseOutput;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.VlanTag;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.PolicyManagerClient;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.AllowedRanges;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.Elements;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.ResourceModel;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VlantagApiServiceImpl implements VlantagApi {

    private static final VlantagApi INSTANCE = new VlantagApiServiceImpl();
    private static final Logger log = LoggerFactory.getLogger(VlantagApiServiceImpl.class);
    private static final String BEGIN_SQUAREBRACKET_RC = "#BSB#";
    private static final String END_SQUAREBRACKET_RC = "#ESB#";

    private ResourceAllocator resourceAllocator;

    public static VlantagApi getInstance() {
        return INSTANCE;
    }

    @Override
    public AssignVlanTagResponse assignVlanTag(AssignVlanTagRequest request) throws Exception {
        List<AssignVlanTagResponseOutput> outputList = new ArrayList<>();

        try {
            validateRequest(request);
            List<AssignVlanTagRequestInput> vlanTagRequests = request.getInput();

            HashMap<String, List<ResourceResponse>> reservedResources = new HashMap<>();

            PolicyManagerClient policyClient = getPolicyManagerClient();

            for (AssignVlanTagRequestInput input : vlanTagRequests) {
                log.info("PolicyManagerClient called..for policy : {}", input.getPolicyInstanceName());

                List<ResourceModel> resourceModels = policyClient.getPolicy(input.getPolicyInstanceName());
                ResourceModel model = validate(resourceModels, input);
                List<ResourceResponse> rl = new ArrayList<>();

                for (Elements elements : model.getElements()) {

                    ResourceEntity re = new ResourceEntity();
                    ResourceTarget rt = new ResourceTarget();
                    ResourceRequest rr = new ResourceRequest();
                    List<ResourceResponse> rsList = new ArrayList<>();

                    prepareResourceAllocatorObjects(input, model, elements, re, rt, rr);
                    if (resourceAllocator == null) {
                        this.getResourceAllocator();
                    }
                    if (resourceAllocator != null) {
                        AllocationStatus status = resourceAllocator.reserve(re, rt, rr, rsList);

                        if (AllocationStatus.Success.equals(status))
                            rl.addAll(rsList);
                        else {
                            rollbackVlanTags(reservedResources);
                            throw new VlantagApiException(
                                    "Failed to reserve VlanTags for Element : {}. " + elements.getVlantagName()
                                            + ". Rolling back vlantags for other elements (if any).");
                        }
                    } else
                        throw new VlantagApiException(
                                "ResourceAllocator not available. Failed to Assign VlanTags for Element : "
                                        + elements.getVlantagName()
                                        + ". Rolling back vlantags for other elements (if any).");

                }
                reservedResources.put(model.getVlanType(), rl);
                outputList.add(prepareVlanTagResponse(input, model, rl));
            }

        } catch (Exception e) {
            log.error("Exception : " + e.getMessage(), e);

            AssignVlanTagResponse response = new AssignVlanTagResponse();
            response.setErrorCode(500);
            response.setErrorMessage(e.getMessage());
            return response;
        }

        AssignVlanTagResponse response = new AssignVlanTagResponse();
        response.setErrorCode(200);
        response.setErrorMessage("Success");
        response.setOutput(outputList);
        return response;
    }

    private void prepareResourceAllocatorObjects(AssignVlanTagRequestInput input, ResourceModel model, Elements element,
            ResourceEntity re, ResourceTarget rt, ResourceRequest rr) throws VlantagApiException {
        log.info("Preparing RA Objects for Vlan Type : " + input.getVlanType() + " and Element : "
                + element.getVlantagName());
        re.resourceEntityId = input.getKey();
        re.resourceEntityType = model.getKeyType() == null ? "DEFAULT" : model.getKeyType();
        re.resourceEntityVersion = "1";

        rt.resourceTargetId = input.getScopeId();
        rt.resourceTargetType = model.getScope() == null ? "DEFAULT" : model.getScope();

        rr.resourceName = input.getVlanType();
        rr.serviceModel = input.getPolicyInstanceName();
        rr.endPointPosition = element.getVlantagName();
        rr.resourceType = ResourceType.Range;
        rr.applicationId = "SDNC";
        rr.rangeMaxOverride = -1;
        rr.rangeMinOverride = -1;

        List<Range> rangeList = new ArrayList<>();
        for (AllowedRanges allowedRange : element.getAllowedRanges()) {
            Range range = new Range();

            if (allowedRange.getMin() != null && !allowedRange.getMin().isEmpty())
                range.min = Integer.parseInt(allowedRange.getMin());

            if (allowedRange.getMax() != null && !allowedRange.getMax().isEmpty())
                range.max = Integer.parseInt(allowedRange.getMax());

            rangeList.add(range);
        }

        rr.rangeOverrideList = rangeList;

        String resourceValue = resolveResourceElementValue(input, model, element);
        if (resourceValue != null) {
            rr.rangeRequestedNumbers = resourceValue; /* Manually provided values */

            /*
             * If the override flag is TRUE, then add the resource value also in the range,
             * so it will ignore the current range min-max in the policy. Persist in the DB
             * if available else Fail.
             */
            if (element.getOverwrite() != null && element.getOverwrite().equalsIgnoreCase("TRUE")) {
                Range range = new Range();
                range.min = Integer.parseInt(resourceValue);
                rangeList.add(range);
            }
        }

        StrUtil.info(log, re);
        StrUtil.info(log, rt);
        StrUtil.info(log, rr);

    }

    protected String resolveResourceElementValue(AssignVlanTagRequestInput input, ResourceModel model, Elements element)
            throws VlantagApiException {
        String recipe = trimBrackets(model.getResourceResolutionRecipe().trim());

        if (input.getResourceValue() != null && !input.getResourceValue().trim().isEmpty()
                && !input.getResourceValue().contains("$")) {
            log.info("Resource Value : " + input.getResourceValue());
            String resourceValue = trimBrackets(input.getResourceValue());

            String[] vlantagNames = recipe.split(",");
            String[] resourceValues = resourceValue.split(",");

            try {
                for (int i = 0; i < vlantagNames.length; i++) {
                    if (vlantagNames[i].trim().equalsIgnoreCase(element.getVlantagName().trim()))
                        return resourceValues[i].trim();
                }
            } catch (IndexOutOfBoundsException e) {
                throw new VlantagApiException("No Matching Resource Value found from Recipe : \""
                        + model.getResourceResolutionRecipe() + "\" for Vlantag Name : " + element.getVlantagName());
            }

        }
        return null;
    }

    ResourceModel validate(List<ResourceModel> resourceModels, AssignVlanTagRequestInput input)
            throws VlantagApiException {
        ResourceModel targetModel = null;
        if (resourceModels != null && !resourceModels.isEmpty()) {
            for (ResourceModel model : resourceModels) {
                if (model.getVlanType().equals(input.getVlanType())) {
                    targetModel = model;
                    break;
                }
            }
            validateModel(targetModel, input);
        } else
            throw new VlantagApiException("No Resource Models available in Policy Manager for Policy Instance Name : "
                    + input.getPolicyInstanceName());
        return targetModel;
    }

    void validateModel(ResourceModel model, AssignVlanTagRequestInput input) throws VlantagApiException {

        if (model == null)
            throw new VlantagApiException(
                    "No Matching Policy Resource Model found for Vlan Type : " + input.getVlanType());
        else {
            if (model.getResourceResolutionRecipe() == null || model.getResourceResolutionRecipe().isEmpty())
                throw new VlantagApiException(
                        "Resource Resolution Recipe is null in Resource Model for Vlan Type : " + input.getVlanType());

            if (model.getScope() == null || model.getScope().isEmpty())
                throw new VlantagApiException("Scope is null in Resource Model for Vlan Type : " + input.getVlanType());

            List<Elements> elements = model.getElements();
            validateElements(elements, input);
        }

    }

    void validateElements(List<Elements> elements, AssignVlanTagRequestInput input) throws VlantagApiException {
        if (elements != null && !elements.isEmpty()) {
            for (Elements element : elements) {
                if (element.getVlantagName() == null)
                    throw new VlantagApiException(
                            "Vlantag Name missing for Element in Resource Model Policy for Vlan Type : "
                                    + input.getVlanType());
                if (element.getAllowedRanges() == null || element.getAllowedRanges().isEmpty())
                    throw new VlantagApiException(
                            "Allowed Ranges missing for Element in Resource Model Policy for Vlan Type : "
                                    + input.getVlanType());
            }
        } else
            throw new VlantagApiException(
                    "No Vlantag Elements found in Resource Model Policy for Vlan Type : " + input.getVlanType());

    }

    PolicyManagerClient getPolicyManagerClient() {
        return new PolicyManagerClient();
    }

    void validateRequest(AssignVlanTagRequest request) throws VlantagApiException {
        if (request == null)
            throw new VlantagApiException("VlanTag Assign Request is null.");

        List<AssignVlanTagRequestInput> inputList = request.getInput();

        if (inputList == null || inputList.isEmpty())
            throw new VlantagApiException("VlanTag Assign Request Input is null or empty.");

        for (AssignVlanTagRequestInput input : inputList) {
            if (input.getPolicyInstanceName() == null || input.getPolicyInstanceName().isEmpty())
                throw new VlantagApiException("VlanTag Assign Request policy-instance-name is null.");

            if (input.getVlanType() == null || input.getVlanType().isEmpty())
                throw new VlantagApiException("VlanTag Assign Request vlan-type is null.");

            if (input.getScopeId() == null || input.getScopeId().isEmpty())
                throw new VlantagApiException("VlanTag Assign Request scope-id is null.");

            if (input.getKey() == null || input.getKey().isEmpty())
                throw new VlantagApiException("VlanTag Assign Request key is null.");
        }

    }

    private AssignVlanTagResponseOutput prepareVlanTagResponse(AssignVlanTagRequestInput input, ResourceModel model,
            List<ResourceResponse> rl) {
        AssignVlanTagResponseOutput ro = new AssignVlanTagResponseOutput();
        List<VlanTag> vlanTagList = new ArrayList<>();

        if (rl != null && !rl.isEmpty()) {
            ro.setResourceName(input.getResourceName());
            ro.setResourceValue(resolveRecipe(model, rl));
            ro.setResourceVlanRole(model.getResourceVlanRole());
            if (model.getDataStore() != null && !model.getDataStore().isEmpty()) {
                for (ResourceResponse rr : rl) {
                    VlanTag tag = new VlanTag();
                    Optional<Elements> optionalElements = model.getElements().stream()
                            .filter(element -> element.getVlantagName().equalsIgnoreCase(rr.endPointPosition))
                            .findFirst();
                    optionalElements.ifPresent(element -> tag.setElementVlanRole(element.getElementVlanRole()));

                    tag.setVlanUuid(UUID.randomUUID().toString());
                    tag.setVlantagName(rr.endPointPosition);
                    tag.setVlantagValue(rr.resourceAllocated);
                    vlanTagList.add(tag);
                }

            }
            ro.setStoredElements(vlanTagList);
        }

        return ro;
    }

    protected String resolveRecipe(ResourceModel model, List<ResourceResponse> rl) {
        String recipe = model.getResourceResolutionRecipe().trim();
        String resourceValue = recipe;

        if (recipe.contains(BEGIN_SQUAREBRACKET_RC)) {
            recipe = recipe.replace(BEGIN_SQUAREBRACKET_RC, "");
            resourceValue = resourceValue.replace(BEGIN_SQUAREBRACKET_RC, "[ ");
        }

        if (recipe.contains(END_SQUAREBRACKET_RC)) {
            recipe = recipe.replace(END_SQUAREBRACKET_RC, "");
            resourceValue = resourceValue.replace(END_SQUAREBRACKET_RC, " ]");
        }

        String[] vlantagNames = recipe.split(",");

        for (String vlantagName : vlantagNames) {
            for (ResourceResponse rr : rl) {
                if (vlantagName.trim().equalsIgnoreCase(rr.endPointPosition)) {
                    resourceValue = resourceValue.replace(vlantagName, " " + rr.resourceAllocated);
                    break;
                }
            }
        }

        log.info(resourceValue);

        return resourceValue;
    }

    private void rollbackVlanTags(HashMap<String, List<ResourceResponse>> reservedResources) {
        if (reservedResources != null && !reservedResources.isEmpty()) {
            reservedResources.forEach((resourceName, rrList) -> {
                rrList.forEach(rr -> {

                    UnassignVlanTagRequest unassignRequest = new UnassignVlanTagRequest();
                    List<UnassignVlanTagRequestInput> inputList = new ArrayList<>();
                    // TODO
                    UnassignVlanTagRequestInput input = new UnassignVlanTagRequestInput();
                    // input.setPolicyInstanceName(policyInstanceName);
                    // input.setKey();
                    input.setVlanType(resourceName);
                    inputList.add(input);
                    unassignRequest.setInput(inputList);

                });

            });
        }

    }

    @Override
    public UnassignVlanTagResponse unassignVlanTag(UnassignVlanTagRequest request) throws Exception {
        UnassignVlanTagResponse response = new UnassignVlanTagResponse();
        List<UnassignVlanTagResponseOutput> output = new ArrayList<>();

        try {
            PolicyManagerClient policyClient = getPolicyManagerClient();

            validateRequest(request);
            List<UnassignVlanTagRequestInput> vlanTagRequests = request.getInput();

            for (UnassignVlanTagRequestInput input : vlanTagRequests) {
                List<ResourceModel> resourceModels = policyClient.getPolicy(input.getPolicyInstanceName());
                ResourceModel model = validate(resourceModels, input);

                for (Elements elements : model.getElements()) {

                    ResourceEntity re = new ResourceEntity();
                    re.resourceEntityId = input.getKey();
                    re.resourceEntityType = model.getKeyType() == null ? "DEFAULT" : model.getKeyType();
                    re.resourceEntityVersion = "1";

                    ResourceRequest rr = new ResourceRequest();
                    rr.endPointPosition = elements.getVlantagName();

                    if (resourceAllocator == null) {
                        this.getResourceAllocator();
                    }
                    if (resourceAllocator != null) {
                        AllocationStatus status = resourceAllocator.release(re, rr);

                        if (AllocationStatus.Success.equals(status)) {
                            UnassignVlanTagResponseOutput ro = new UnassignVlanTagResponseOutput();
                            ro.setKey(input.getKey());
                            ro.setVlanType(input.getVlanType());
                            ro.setVlantagName(elements.getVlantagName());
                            output.add(ro);
                        } else {
                            throw new VlantagApiException(
                                    "Failed to release VlanTags for Element : " + elements.getVlantagName() + ".");
                        }
                    } else
                        throw new VlantagApiException(
                                "ResourceAllocator not available. Failed to Unassign VlanTags for Element : "
                                        + elements.getVlantagName()
                                        + ". Rolling back vlantags for other elements (if any).");
                }
            }
        } catch (Exception e) {
            log.error("Exception : " + e.getMessage(), e);

            response.setErrorCode(500);
            response.setErrorMessage(e.getMessage());
            return response;
        }

        response.setOutput(output);
        response.setErrorCode(200);
        response.setErrorMessage("Success");
        return response;
    }

    ResourceModel validate(List<ResourceModel> resourceModels, UnassignVlanTagRequestInput input)
            throws VlantagApiException {
        ResourceModel targetModel = null;
        if (resourceModels != null && !resourceModels.isEmpty()) {
            for (ResourceModel model : resourceModels) {
                if (model.getVlanType().equals(input.getVlanType())) {
                    targetModel = model;
                    break;
                }
            }
            validateModel(targetModel, input);
        } else
            throw new VlantagApiException("No Resource Models available in Policy Manager for Policy Instance Name : "
                    + input.getPolicyInstanceName());
        return targetModel;
    }

    void validateModel(ResourceModel model, UnassignVlanTagRequestInput input) throws VlantagApiException {
        if (model == null)
            throw new VlantagApiException(
                    "No Matching Policy Resource Model found for Vlan Type : " + input.getVlanType());
        else {
            if (model.getResourceResolutionRecipe() == null || model.getResourceResolutionRecipe().isEmpty())
                throw new VlantagApiException(
                        "Resource Resolution Recipe is null in Resource Model for Vlan Type : " + input.getVlanType());

            if (model.getScope() == null || model.getScope().isEmpty())
                throw new VlantagApiException("Scope is null in Resource Model for Vlan Type : " + input.getVlanType());

            List<Elements> elements = model.getElements();
            validateElements(elements, input);
        }

    }

    protected void validateElements(List<Elements> elements, UnassignVlanTagRequestInput input)
            throws VlantagApiException {
        if (elements != null && !elements.isEmpty()) {
            for (Elements element : elements) {
                if (element.getVlantagName() == null)
                    throw new VlantagApiException(
                            "Vlantag Name missing for Element in Resource Model Policy for Vlan Type : "
                                    + input.getVlanType());
            }
        } else
            throw new VlantagApiException(
                    "No Vlantag Elements found in Resource Model Policy for Vlan Type : " + input.getVlanType());

    }

    protected void validateRequest(UnassignVlanTagRequest request) throws VlantagApiException {
        if (request == null)
            throw new VlantagApiException("VlanTag Unassign Request is null.");

        List<UnassignVlanTagRequestInput> inputList = request.getInput();
        if (inputList == null || inputList.isEmpty())
            throw new VlantagApiException("VlanTag Unassign Request Input is null or empty.");

        for (UnassignVlanTagRequestInput input : inputList) {
            if (input.getPolicyInstanceName() == null || input.getPolicyInstanceName().isEmpty())
                throw new VlantagApiException("VlanTag Unassign Request policy-instance-name is null.");

            if (input.getVlanType() == null || input.getVlanType().isEmpty())
                throw new VlantagApiException("VlanTag Unassign Request resource-name is null.");

            if (input.getKey() == null || input.getKey().isEmpty())
                throw new VlantagApiException("VlanTag Unassign Request key is null.");
        }

    }

    @Override
    public PingResponse getPing(String name) {
        PingResponse ping = new PingResponse();
        ping.setMessage("Ping response : " + name + " Time : " + new Date());
        return ping;
    }

    protected void getResourceAllocator() {
        BundleContext bctx = FrameworkUtil.getBundle(ResourceAllocator.class).getBundleContext();
        ServiceReference<?> sref = bctx.getServiceReference(ResourceAllocator.class.getName());
        this.resourceAllocator = (ResourceAllocator) bctx.getService(sref);
    }

    protected void setResourceAllocator(ResourceAllocator ra) {
        this.resourceAllocator = ra;
    }

    protected String trimBrackets(String recipe) {
        if (recipe != null) {
            if (recipe.contains(BEGIN_SQUAREBRACKET_RC))
                recipe = recipe.replace(BEGIN_SQUAREBRACKET_RC, "");
            if (recipe.contains(END_SQUAREBRACKET_RC))
                recipe = recipe.replace(END_SQUAREBRACKET_RC, "");
            if (recipe.contains("["))
                recipe = recipe.replace("[", "");
            if (recipe.contains("]"))
                recipe = recipe.replace("]", "");
        }
        return recipe;
    }

}
