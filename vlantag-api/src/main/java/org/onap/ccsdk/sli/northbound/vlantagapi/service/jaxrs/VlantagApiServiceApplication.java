package org.onap.ccsdk.sli.northbound.vlantagapi.service.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.onap.ccsdk.sli.northbound.vlantagapi.service.impl.VlantagApiServiceImpl;

public class VlantagApiServiceApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        singletons.add(VlantagApiServiceImpl.getInstance());
        return singletons;
    }

}
