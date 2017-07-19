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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.module.Configuration;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.DataBroker;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * <p>This class represents the following YANG schema fragment defined in module <b>capacity-api-provider-impl</b>
 * <pre>
 * case capacity-api-provider-impl {
 *     container rpc-registry {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (org:openecomp:sdnc:capacity-api:provider:impl?revision=2014-05-23)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container notification-service {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (org:openecomp:sdnc:capacity-api:provider:impl?revision=2014-05-23)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container data-broker {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (org:openecomp:sdnc:capacity-api:provider:impl?revision=2014-05-23)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>capacity-api-provider-impl/modules/module/configuration/(org:openecomp:sdnc:capacity-api:provider:impl?revision=2014-05-23)capacity-api-provider-impl</i>
 *
 */
public interface CapacityApiProviderImpl
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>,
    Configuration
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnc:capacity-api:provider:impl",
        "2014-05-23", "capacity-api-provider-impl").intern();

    /**
     * @return <code>org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.RpcRegistry</code> <code>rpcRegistry</code>, or <code>null</code> if not present
     */
    RpcRegistry getRpcRegistry();
    
    /**
     * @return <code>org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService</code> <code>notificationService</code>, or <code>null</code> if not present
     */
    NotificationService getNotificationService();
    
    /**
     * @return <code>org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.DataBroker</code> <code>dataBroker</code>, or <code>null</code> if not present
     */
    DataBroker getDataBroker();

}

