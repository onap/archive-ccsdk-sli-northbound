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
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.reservation.target.list.ReservationTargetData;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList
 *
 */
public class ReservationTargetListBuilder implements Builder <org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList> {

    private ReservationTargetListKey _key;
    private List<ReservationTargetData> _reservationTargetData;
    private java.lang.String _reservationTargetId;
    private java.lang.String _reservationTargetType;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>> augmentation = Collections.emptyMap();

    public ReservationTargetListBuilder() {
    }

    public ReservationTargetListBuilder(ReservationTargetList base) {
        if (base.getKey() == null) {
            this._key = new ReservationTargetListKey(
                base.getReservationTargetId()
            );
            this._reservationTargetId = base.getReservationTargetId();
        } else {
            this._key = base.getKey();
            this._reservationTargetId = _key.getReservationTargetId();
        }
        this._reservationTargetData = base.getReservationTargetData();
        this._reservationTargetType = base.getReservationTargetType();
        if (base instanceof ReservationTargetListImpl) {
            ReservationTargetListImpl impl = (ReservationTargetListImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public ReservationTargetListKey getKey() {
        return _key;
    }
    
    public List<ReservationTargetData> getReservationTargetData() {
        return _reservationTargetData;
    }
    
    public java.lang.String getReservationTargetId() {
        return _reservationTargetId;
    }
    
    public java.lang.String getReservationTargetType() {
        return _reservationTargetType;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ReservationTargetListBuilder setKey(final ReservationTargetListKey value) {
        this._key = value;
        return this;
    }
    
     
    public ReservationTargetListBuilder setReservationTargetData(final List<ReservationTargetData> value) {
        this._reservationTargetData = value;
        return this;
    }
    
     
    public ReservationTargetListBuilder setReservationTargetId(final java.lang.String value) {
        this._reservationTargetId = value;
        return this;
    }
    
     
    public ReservationTargetListBuilder setReservationTargetType(final java.lang.String value) {
        this._reservationTargetType = value;
        return this;
    }
    
    public ReservationTargetListBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public ReservationTargetListBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public ReservationTargetList build() {
        return new ReservationTargetListImpl(this);
    }

    private static final class ReservationTargetListImpl implements ReservationTargetList {

        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList.class;
        }

        private final ReservationTargetListKey _key;
        private final List<ReservationTargetData> _reservationTargetData;
        private final java.lang.String _reservationTargetId;
        private final java.lang.String _reservationTargetType;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>> augmentation = Collections.emptyMap();

        private ReservationTargetListImpl(ReservationTargetListBuilder base) {
            if (base.getKey() == null) {
                this._key = new ReservationTargetListKey(
                    base.getReservationTargetId()
                );
                this._reservationTargetId = base.getReservationTargetId();
            } else {
                this._key = base.getKey();
                this._reservationTargetId = _key.getReservationTargetId();
            }
            this._reservationTargetData = base.getReservationTargetData();
            this._reservationTargetType = base.getReservationTargetType();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public ReservationTargetListKey getKey() {
            return _key;
        }
        
        @Override
        public List<ReservationTargetData> getReservationTargetData() {
            return _reservationTargetData;
        }
        
        @Override
        public java.lang.String getReservationTargetId() {
            return _reservationTargetId;
        }
        
        @Override
        public java.lang.String getReservationTargetType() {
            return _reservationTargetType;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>> E getAugmentation(java.lang.Class<E> augmentationType) {
            if (augmentationType == null) {
                throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
            }
            return (E) augmentation.get(augmentationType);
        }

        private int hash = 0;
        private volatile boolean hashValid = false;
        
        @Override
        public int hashCode() {
            if (hashValid) {
                return hash;
            }
        
            final int prime = 31;
            int result = 1;
            result = prime * result + Objects.hashCode(_key);
            result = prime * result + Objects.hashCode(_reservationTargetData);
            result = prime * result + Objects.hashCode(_reservationTargetId);
            result = prime * result + Objects.hashCode(_reservationTargetType);
            result = prime * result + Objects.hashCode(augmentation);
        
            hash = result;
            hashValid = true;
            return result;
        }

        @Override
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DataObject)) {
                return false;
            }
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList)obj;
            if (!Objects.equals(_key, other.getKey())) {
                return false;
            }
            if (!Objects.equals(_reservationTargetData, other.getReservationTargetData())) {
                return false;
            }
            if (!Objects.equals(_reservationTargetId, other.getReservationTargetId())) {
                return false;
            }
            if (!Objects.equals(_reservationTargetType, other.getReservationTargetType())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ReservationTargetListImpl otherImpl = (ReservationTargetListImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList>> e : augmentation.entrySet()) {
                    if (!e.getValue().equals(other.getAugmentation(e.getKey()))) {
                        return false;
                    }
                }
                // .. and give the other one the chance to do the same
                if (!obj.equals(this)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public java.lang.String toString() {
            java.lang.String name = "ReservationTargetList [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_key != null) {
                builder.append("_key=");
                builder.append(_key);
                builder.append(", ");
            }
            if (_reservationTargetData != null) {
                builder.append("_reservationTargetData=");
                builder.append(_reservationTargetData);
                builder.append(", ");
            }
            if (_reservationTargetId != null) {
                builder.append("_reservationTargetId=");
                builder.append(_reservationTargetId);
                builder.append(", ");
            }
            if (_reservationTargetType != null) {
                builder.append("_reservationTargetType=");
                builder.append(_reservationTargetType);
            }
            final int builderLength = builder.length();
            final int builderAdditionalLength = builder.substring(name.length(), builderLength).length();
            if (builderAdditionalLength > 2 && !builder.substring(builderLength - 2, builderLength).equals(", ")) {
                builder.append(", ");
            }
            builder.append("augmentation=");
            builder.append(augmentation.values());
            return builder.append(']').toString();
        }
    }

}
