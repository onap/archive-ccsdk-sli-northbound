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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.Module;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * <p>This class represents the following YANG schema fragment defined in module <b>capacity-api-provider-impl</b>
 * <pre>
 * container data-broker {
 *     leaf type {
 *         type leafref;
 *     }
 *     leaf name {
 *         type leafref;
 *     }
 *     uses service-ref {
 *         refine (org:openecomp:sdnc:capacity-api:provider:impl?revision=2014-05-23)type {
 *             leaf type {
 *                 type leafref;
 *             }
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>capacity-api-provider-impl/modules/module/configuration/(org:openecomp:sdnc:capacity-api:provider:impl?revision=2014-05-23)capacity-api-provider-impl/data-broker</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.DataBrokerBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.DataBrokerBuilder
 *
 */
public interface DataBroker
    extends
    ChildOf<Module>,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.DataBroker>,
    ServiceRef
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnc:capacity-api:provider:impl",
        "2014-05-23", "data-broker").intern();


}

