package org.onap.ccsdk.sli.northbound.vlantagapi.service.util;

import java.util.List;

import org.onap.ccsdk.sli.adaptors.ra.ResourceAllocator;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceEntity;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceRequest;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceResponse;
import org.onap.ccsdk.sli.adaptors.ra.comp.ResourceTarget;
import org.onap.ccsdk.sli.adaptors.rm.data.AllocationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockResourceAllocator extends ResourceAllocator {
    protected static final Logger logger = LoggerFactory.getLogger(MockResourceAllocator.class);

    /*
     * public MockResourceAllocator() { super(); }
     */

    public AllocationStatus reserve(ResourceEntity sd, ResourceTarget rt, ResourceRequest rr,
            List<ResourceResponse> rsList) throws Exception {

        ResourceResponse rres = new ResourceResponse();
        rres.endPointPosition = "VPE-Core1";
        rres.resourceAllocated = "2001";
        rres.resourceName = "vlan-id-outer";

        rsList.add(rres);

        return AllocationStatus.Success;
    }
}
