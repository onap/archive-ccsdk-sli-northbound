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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList
 *
 */
public class ReservationEntityListBuilder implements Builder <org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList> {

    private ReservationEntityListKey _key;
    private java.lang.String _reservationEntityId;
    private java.lang.String _reservationEntityType;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>> augmentation = Collections.emptyMap();

    public ReservationEntityListBuilder() {
    }

    public ReservationEntityListBuilder(ReservationEntityList base) {
        if (base.getKey() == null) {
            this._key = new ReservationEntityListKey(
                base.getReservationEntityId()
            );
            this._reservationEntityId = base.getReservationEntityId();
        } else {
            this._key = base.getKey();
            this._reservationEntityId = _key.getReservationEntityId();
        }
        this._reservationEntityType = base.getReservationEntityType();
        if (base instanceof ReservationEntityListImpl) {
            ReservationEntityListImpl impl = (ReservationEntityListImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public ReservationEntityListKey getKey() {
        return _key;
    }
    
    public java.lang.String getReservationEntityId() {
        return _reservationEntityId;
    }
    
    public java.lang.String getReservationEntityType() {
        return _reservationEntityType;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ReservationEntityListBuilder setKey(final ReservationEntityListKey value) {
        this._key = value;
        return this;
    }
    
     
    public ReservationEntityListBuilder setReservationEntityId(final java.lang.String value) {
        this._reservationEntityId = value;
        return this;
    }
    
     
    public ReservationEntityListBuilder setReservationEntityType(final java.lang.String value) {
        this._reservationEntityType = value;
        return this;
    }
    
    public ReservationEntityListBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public ReservationEntityListBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public ReservationEntityList build() {
        return new ReservationEntityListImpl(this);
    }

    private static final class ReservationEntityListImpl implements ReservationEntityList {

        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList.class;
        }

        private final ReservationEntityListKey _key;
        private final java.lang.String _reservationEntityId;
        private final java.lang.String _reservationEntityType;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>> augmentation = Collections.emptyMap();

        private ReservationEntityListImpl(ReservationEntityListBuilder base) {
            if (base.getKey() == null) {
                this._key = new ReservationEntityListKey(
                    base.getReservationEntityId()
                );
                this._reservationEntityId = base.getReservationEntityId();
            } else {
                this._key = base.getKey();
                this._reservationEntityId = _key.getReservationEntityId();
            }
            this._reservationEntityType = base.getReservationEntityType();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public ReservationEntityListKey getKey() {
            return _key;
        }
        
        @Override
        public java.lang.String getReservationEntityId() {
            return _reservationEntityId;
        }
        
        @Override
        public java.lang.String getReservationEntityType() {
            return _reservationEntityType;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_reservationEntityId);
            result = prime * result + Objects.hashCode(_reservationEntityType);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList)obj;
            if (!Objects.equals(_key, other.getKey())) {
                return false;
            }
            if (!Objects.equals(_reservationEntityId, other.getReservationEntityId())) {
                return false;
            }
            if (!Objects.equals(_reservationEntityType, other.getReservationEntityType())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ReservationEntityListImpl otherImpl = (ReservationEntityListImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ReservationEntityList [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_key != null) {
                builder.append("_key=");
                builder.append(_key);
                builder.append(", ");
            }
            if (_reservationEntityId != null) {
                builder.append("_reservationEntityId=");
                builder.append(_reservationEntityId);
                builder.append(", ");
            }
            if (_reservationEntityType != null) {
                builder.append("_reservationEntityType=");
                builder.append(_reservationEntityType);
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
