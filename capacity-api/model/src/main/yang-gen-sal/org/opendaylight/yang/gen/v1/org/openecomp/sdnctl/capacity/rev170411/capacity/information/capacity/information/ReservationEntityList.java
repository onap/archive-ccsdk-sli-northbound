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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information.reservation.entity.list.ReservationTargetList;
import java.util.List;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.Identifiable;


/**
 * <p>This class represents the following YANG schema fragment defined in module <b>CAPACITY-API</b>
 * <pre>
 * list reservation-entity-list {
 *     key "reservation-entity-id"
 *     leaf reservation-entity-id {
 *         type string;
 *     }
 *     leaf status {
 *         type string;
 *     }
 *     list reservation-target-list {
 *         key "reservation-target-id"
 *         leaf reservation-target-id {
 *             type string;
 *         }
 *         leaf status {
 *             type string;
 *         }
 *         list resource-list {
 *             key "resource-name"
 *             leaf resource-name {
 *                 type string;
 *             }
 *             leaf status {
 *                 type string;
 *             }
 *             leaf allocated {
 *                 type string;
 *             }
 *             leaf available {
 *                 type string;
 *             }
 *             leaf used {
 *                 type string;
 *             }
 *             leaf limit {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>CAPACITY-API/capacity-information/capacity-information/reservation-entity-list</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information.ReservationEntityListBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information.ReservationEntityListBuilder
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information.ReservationEntityListKey
 *
 */
public interface ReservationEntityList
    extends
    ChildOf<CapacityInformation>,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information.ReservationEntityList>,
    Identifiable<ReservationEntityListKey>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnctl:capacity",
        "2017-04-11", "reservation-entity-list").intern();

    /**
     * Valid Values:ADIOD: VPE
     *
     *
     *
     * @return <code>java.lang.String</code> <code>reservationEntityId</code>, or <code>null</code> if not present
     */
    java.lang.String getReservationEntityId();
    
    /**
     * @return <code>java.lang.String</code> <code>status</code>, or <code>null</code> if not present
     */
    java.lang.String getStatus();
    
    /**
     * @return <code>java.util.List</code> <code>reservationTargetList</code>, or <code>null</code> if not present
     */
    List<ReservationTargetList> getReservationTargetList();
    
    /**
     * Returns Primary Key of Yang List Type
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information.ReservationEntityListKey</code> <code>key</code>, or <code>null</code> if not present
     */
    ReservationEntityListKey getKey();

}

