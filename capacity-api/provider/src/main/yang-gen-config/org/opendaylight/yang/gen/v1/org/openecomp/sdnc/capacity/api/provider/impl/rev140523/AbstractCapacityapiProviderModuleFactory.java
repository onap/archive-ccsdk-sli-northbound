/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 * 						reserved.
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

/*
* Generated file
*
* Generated from: yang module name: capacity-api-provider-impl yang module local name: capacity-api-provider-impl
* Generated by: org.opendaylight.controller.config.yangjmxgenerator.plugin.JMXGenerator
* Generated at: Tue Jul 18 14:01:17 EDT 2017
*
* Do not modify this file unless it is present under src/main directory
*/
package org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523;

import org.opendaylight.controller.config.spi.Module;
import org.opendaylight.controller.config.api.ModuleIdentifier;

@org.opendaylight.yangtools.yang.binding.annotations.ModuleQName(namespace = "org:openecomp:sdnc:capacity-api:provider:impl", name = "capacity-api-provider-impl", revision = "2014-05-23")

public abstract class AbstractCapacityapiProviderModuleFactory implements org.opendaylight.controller.config.spi.ModuleFactory {
    public static final java.lang.String NAME = "capacity-api-provider-impl";

    private static final java.util.Set<Class<? extends org.opendaylight.controller.config.api.annotations.AbstractServiceInterface>> serviceIfcs;

    @Override
    public final String getImplementationName() {
        return NAME;
    }

    static {
        serviceIfcs = java.util.Collections.emptySet();
    }

    @Override
    public final boolean isModuleImplementingServiceInterface(Class<? extends org.opendaylight.controller.config.api.annotations.AbstractServiceInterface> serviceInterface) {
        for (Class<?> ifc: serviceIfcs) {
            if (serviceInterface.isAssignableFrom(ifc)){
                return true;
            }
        }
        return false;
    }

    @Override
    public java.util.Set<Class<? extends org.opendaylight.controller.config.api.annotations.AbstractServiceInterface>> getImplementedServiceIntefaces() {
        return serviceIfcs;
    }

    @Override
    public org.opendaylight.controller.config.spi.Module createModule(String instanceName, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.osgi.framework.BundleContext bundleContext) {
        return instantiateModule(instanceName, dependencyResolver, bundleContext);
    }

    @Override
    public org.opendaylight.controller.config.spi.Module createModule(String instanceName, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.controller.config.api.DynamicMBeanWithInstance old, org.osgi.framework.BundleContext bundleContext) throws Exception {
        org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule oldModule;
        try {
            oldModule = (org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule) old.getModule();
        } catch(Exception e) {
            return handleChangedClass(dependencyResolver, old, bundleContext);
        }
        org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule module = instantiateModule(instanceName, dependencyResolver, oldModule, old.getInstance(), bundleContext);
        module.setRpcRegistry(oldModule.getRpcRegistry());
        module.setDataBroker(oldModule.getDataBroker());
        module.setNotificationService(oldModule.getNotificationService());

        return module;
    }

    public org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule instantiateModule(String instanceName, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule oldModule, java.lang.AutoCloseable oldInstance, org.osgi.framework.BundleContext bundleContext) {
        return new org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule(new org.opendaylight.controller.config.api.ModuleIdentifier(NAME, instanceName), dependencyResolver, oldModule, oldInstance);
    }

    public org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule instantiateModule(String instanceName, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.osgi.framework.BundleContext bundleContext) {
        return new org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule(new org.opendaylight.controller.config.api.ModuleIdentifier(NAME, instanceName), dependencyResolver);
    }

    public org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule handleChangedClass(org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.controller.config.api.DynamicMBeanWithInstance old, org.osgi.framework.BundleContext bundleContext) throws Exception {
        String instanceName = old.getModule().getIdentifier().getInstanceName();
        org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule newModule = new org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule(new ModuleIdentifier(NAME, instanceName), dependencyResolver);
        Module oldModule = old.getModule();
        Class<? extends Module> oldModuleClass = oldModule.getClass();

        newModule.setRpcRegistry( (javax.management.ObjectName) oldModuleClass.getMethod("getRpcRegistry").invoke(oldModule));
        newModule.setDataBroker( (javax.management.ObjectName) oldModuleClass.getMethod("getDataBroker").invoke(oldModule));
        newModule.setNotificationService( (javax.management.ObjectName) oldModuleClass.getMethod("getNotificationService").invoke(oldModule));

        return newModule;
    }

    @Deprecated
    public org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule handleChangedClass(org.opendaylight.controller.config.api.DynamicMBeanWithInstance old) throws Exception {
        throw new UnsupportedOperationException("Class reloading is not supported");
    }

    @Override
    public java.util.Set<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule> getDefaultModules(org.opendaylight.controller.config.api.DependencyResolverFactory dependencyResolverFactory, org.osgi.framework.BundleContext bundleContext) {
        return new java.util.HashSet<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.CapacityapiProviderModule>();
    }

}
