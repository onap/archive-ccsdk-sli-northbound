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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation;
import java.util.List;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.reservation.entity.list.ReservationEntityData;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.Identifiable;


/**
 * <p>This class represents the following YANG schema fragment defined in module <b>CAPACITY-API</b>
 * <pre>
 * list reservation-entity-list {
 *     key "reservation-entity-id"
 *     leaf reservation-entity-type {
 *         type string;
 *     }
 *     leaf reservation-entity-id {
 *         type string;
 *     }
 *     list reservation-entity-data {
 *         key "name"
 *         leaf name {
 *             type string;
 *         }
 *         leaf value {
 *             type string;
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>CAPACITY-API/capacity-reservation-information/capacity-reservation-information/reservation-entity-list</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationEntityListBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationEntityListBuilder
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationEntityListKey
 *
 */
public interface ReservationEntityList
    extends
    ChildOf<CapacityReservationInformation>,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationEntityList>,
    Identifiable<ReservationEntityListKey>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnctl:capacity",
        "2017-04-11", "reservation-entity-list").intern();

    /**
     * Could be service-instance, VNF, etc
     *
     *
     *
     * @return <code>java.lang.String</code> <code>reservationEntityType</code>, or <code>null</code> if not present
     */
    java.lang.String getReservationEntityType();
    
    /**
     * Customer SIID Valuesiid-0001
     *
     *
     *
     * @return <code>java.lang.String</code> <code>reservationEntityId</code>, or <code>null</code> if not present
     */
    java.lang.String getReservationEntityId();
    
    /**
     * @return <code>java.util.List</code> <code>reservationEntityData</code>, or <code>null</code> if not present
     */
    List<ReservationEntityData> getReservationEntityData();
    
    /**
     * Returns Primary Key of Yang List Type
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationEntityListKey</code> <code>key</code>, or <code>null</code> if not present
     */
    ReservationEntityListKey getKey();

}

