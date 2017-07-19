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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411;
import org.opendaylight.yangtools.yang.binding.RpcService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import java.util.concurrent.Future;


/**
 * Interface for implementing the following YANG RPCs defined in module <b>CAPACITY-API</b>
 * <pre>
 * rpc service-capacity-reserve-operation {
 *     input {
 *         container capacity-reservation-information {
 *             leaf service-model {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id"
 *                 leaf reservation-entity-type {
 *                     type string;
 *                 }
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 list reservation-entity-data {
 *                     key "name"
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *             list reservation-target-list {
 *                 key "reservation-target-id"
 *                 leaf reservation-target-type {
 *                     type string;
 *                 }
 *                 leaf reservation-target-id {
 *                     type string;
 *                 }
 *                 list reservation-target-data {
 *                     key "name"
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *         }
 *     }
 *     
 *     output {
 *         leaf response-code {
 *             type string;
 *         }
 *         leaf response-message {
 *             type string;
 *         }
 *         container capacity-information {
 *             leaf status {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id"
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 leaf status {
 *                     type string;
 *                 }
 *                 list reservation-target-list {
 *                     key "reservation-target-id"
 *                     leaf reservation-target-id {
 *                         type string;
 *                     }
 *                     leaf status {
 *                         type string;
 *                     }
 *                     list resource-list {
 *                         key "resource-name"
 *                         leaf resource-name {
 *                             type string;
 *                         }
 *                         leaf status {
 *                             type string;
 *                         }
 *                         leaf allocated {
 *                             type string;
 *                         }
 *                         leaf available {
 *                             type string;
 *                         }
 *                         leaf used {
 *                             type string;
 *                         }
 *                         leaf limit {
 *                             type string;
 *                         }
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * rpc service-capacity-check-operation {
 *     input {
 *         container capacity-reservation-information {
 *             leaf service-model {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id"
 *                 leaf reservation-entity-type {
 *                     type string;
 *                 }
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 list reservation-entity-data {
 *                     key "name"
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *             list reservation-target-list {
 *                 key "reservation-target-id"
 *                 leaf reservation-target-type {
 *                     type string;
 *                 }
 *                 leaf reservation-target-id {
 *                     type string;
 *                 }
 *                 list reservation-target-data {
 *                     key "name"
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *         }
 *     }
 *     
 *     output {
 *         leaf response-code {
 *             type string;
 *         }
 *         leaf response-message {
 *             type string;
 *         }
 *         container capacity-information {
 *             leaf status {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id"
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 leaf status {
 *                     type string;
 *                 }
 *                 list reservation-target-list {
 *                     key "reservation-target-id"
 *                     leaf reservation-target-id {
 *                         type string;
 *                     }
 *                     leaf status {
 *                         type string;
 *                     }
 *                     list resource-list {
 *                         key "resource-name"
 *                         leaf resource-name {
 *                             type string;
 *                         }
 *                         leaf status {
 *                             type string;
 *                         }
 *                         leaf allocated {
 *                             type string;
 *                         }
 *                         leaf available {
 *                             type string;
 *                         }
 *                         leaf used {
 *                             type string;
 *                         }
 *                         leaf limit {
 *                             type string;
 *                         }
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * rpc service-capacity-release-operation {
 *     input {
 *         list reservation-entity-list {
 *             key "reservation-entity-id"
 *             leaf reservation-entity-id {
 *                 type string;
 *             }
 *             leaf reservation-entity-type {
 *                 type string;
 *             }
 *         }
 *     }
 *     
 *     output {
 *         leaf response-code {
 *             type string;
 *         }
 *         leaf response-message {
 *             type string;
 *         }
 *     }
 * }
 * </pre>
 *
 */
public interface CAPACITYAPIService
    extends
    RpcService
{




    Future<RpcResult<ServiceCapacityReserveOperationOutput>> serviceCapacityReserveOperation(ServiceCapacityReserveOperationInput input);
    
    Future<RpcResult<ServiceCapacityCheckOperationOutput>> serviceCapacityCheckOperation(ServiceCapacityCheckOperationInput input);
    
    Future<RpcResult<ServiceCapacityReleaseOperationOutput>> serviceCapacityReleaseOperation(ServiceCapacityReleaseOperationInput input);

}

