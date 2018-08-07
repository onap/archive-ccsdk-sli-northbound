package org.onap.ccsdk.sli.northbound.vlantagapi.service.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.onap.ccsdk.sli.core.sli.SvcLogicException;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagRequest;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.AssignVlanTagResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.PingResponse;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagRequest;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.model.UnassignVlanTagResponse;

@Path("/")
public interface VlantagApi {

    @POST
    @Path("/v1/assign")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    AssignVlanTagResponse assignVlanTag(@Valid AssignVlanTagRequest body) throws SvcLogicException, Exception;

    @POST
    @Path("/v1/unassign")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    UnassignVlanTagResponse unassignVlanTag(@Valid UnassignVlanTagRequest body) throws SvcLogicException, Exception;

    @GET
    @Path("/v1/ping/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    PingResponse getPing(@PathParam("name") String name);

}
