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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.List;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.capacity.information.ReservationEntityList;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation
 *
 */
public class CapacityInformationBuilder implements Builder <org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation> {

    private List<ReservationEntityList> _reservationEntityList;
    private java.lang.String _status;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>> augmentation = Collections.emptyMap();

    public CapacityInformationBuilder() {
    }

    public CapacityInformationBuilder(CapacityInformation base) {
        this._reservationEntityList = base.getReservationEntityList();
        this._status = base.getStatus();
        if (base instanceof CapacityInformationImpl) {
            CapacityInformationImpl impl = (CapacityInformationImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public List<ReservationEntityList> getReservationEntityList() {
        return _reservationEntityList;
    }
    
    public java.lang.String getStatus() {
        return _status;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public CapacityInformationBuilder setReservationEntityList(final List<ReservationEntityList> value) {
        this._reservationEntityList = value;
        return this;
    }
    
     
    public CapacityInformationBuilder setStatus(final java.lang.String value) {
        this._status = value;
        return this;
    }
    
    public CapacityInformationBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public CapacityInformationBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public CapacityInformation build() {
        return new CapacityInformationImpl(this);
    }

    private static final class CapacityInformationImpl implements CapacityInformation {

        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation.class;
        }

        private final List<ReservationEntityList> _reservationEntityList;
        private final java.lang.String _status;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>> augmentation = Collections.emptyMap();

        private CapacityInformationImpl(CapacityInformationBuilder base) {
            this._reservationEntityList = base.getReservationEntityList();
            this._status = base.getStatus();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public List<ReservationEntityList> getReservationEntityList() {
            return _reservationEntityList;
        }
        
        @Override
        public java.lang.String getStatus() {
            return _status;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_reservationEntityList);
            result = prime * result + Objects.hashCode(_status);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation)obj;
            if (!Objects.equals(_reservationEntityList, other.getReservationEntityList())) {
                return false;
            }
            if (!Objects.equals(_status, other.getStatus())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                CapacityInformationImpl otherImpl = (CapacityInformationImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation>> e : augmentation.entrySet()) {
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
            java.lang.String name = "CapacityInformation [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_reservationEntityList != null) {
                builder.append("_reservationEntityList=");
                builder.append(_reservationEntityList);
                builder.append(", ");
            }
            if (_status != null) {
                builder.append("_status=");
                builder.append(_status);
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
