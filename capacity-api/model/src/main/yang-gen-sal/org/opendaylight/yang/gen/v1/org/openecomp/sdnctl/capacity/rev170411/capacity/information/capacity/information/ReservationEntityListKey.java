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
import org.opendaylight.yangtools.yang.binding.Identifier;
import java.util.Objects;


public class ReservationEntityListKey
 implements Identifier<ReservationEntityList> {
    private static final long serialVersionUID = 73353765844102823L;
    private final java.lang.String _reservationEntityId;


    public ReservationEntityListKey(java.lang.String _reservationEntityId) {
    
    
        this._reservationEntityId = _reservationEntityId;
    }
    
    /**
     * Creates a copy from Source Object.
     *
     * @param source Source object
     */
    public ReservationEntityListKey(ReservationEntityListKey source) {
        this._reservationEntityId = source._reservationEntityId;
    }


    public java.lang.String getReservationEntityId() {
        return _reservationEntityId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(_reservationEntityId);
        return result;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ReservationEntityListKey other = (ReservationEntityListKey) obj;
        if (!Objects.equals(_reservationEntityId, other._reservationEntityId)) {
            return false;
        }
        return true;
    }

    @Override
    public java.lang.String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder(org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information.ReservationEntityListKey.class.getSimpleName()).append(" [");
        boolean first = true;
    
        if (_reservationEntityId != null) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("_reservationEntityId=");
            builder.append(_reservationEntityId);
         }
        return builder.append(']').toString();
    }
}

