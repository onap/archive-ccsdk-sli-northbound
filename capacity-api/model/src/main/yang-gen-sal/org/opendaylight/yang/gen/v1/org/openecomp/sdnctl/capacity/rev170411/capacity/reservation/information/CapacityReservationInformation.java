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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList;
import java.util.List;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationEntityList;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * <p>This class represents the following YANG schema fragment defined in module <b>CAPACITY-API</b>
 * <pre>
 * container capacity-reservation-information {
 *     leaf service-model {
 *         type string;
 *     }
 *     list reservation-entity-list {
 *         key "reservation-entity-id"
 *         leaf reservation-entity-type {
 *             type string;
 *         }
 *         leaf reservation-entity-id {
 *             type string;
 *         }
 *         list reservation-entity-data {
 *             key "name"
 *             leaf name {
 *                 type string;
 *             }
 *             leaf value {
 *                 type string;
 *             }
 *         }
 *     }
 *     list reservation-target-list {
 *         key "reservation-target-id"
 *         leaf reservation-target-type {
 *             type string;
 *         }
 *         leaf reservation-target-id {
 *             type string;
 *         }
 *         list reservation-target-data {
 *             key "name"
 *             leaf name {
 *                 type string;
 *             }
 *             leaf value {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>CAPACITY-API/capacity-reservation-information/capacity-reservation-information</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformationBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformationBuilder
 *
 */
public interface CapacityReservationInformation
    extends
    ChildOf<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation>,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnctl:capacity",
        "2017-04-11", "capacity-reservation-information").intern();

    /**
     * Could be ADIOD, DHV
     *
     *
     *
     * @return <code>java.lang.String</code> <code>serviceModel</code>, or <code>null</code> if not present
     */
    java.lang.String getServiceModel();
    
    /**
     * @return <code>java.util.List</code> <code>reservationEntityList</code>, or <code>null</code> if not present
     */
    List<ReservationEntityList> getReservationEntityList();
    
    /**
     * @return <code>java.util.List</code> <code>reservationTargetList</code>, or <code>null</code> if not present
     */
    List<ReservationTargetList> getReservationTargetList();

}

