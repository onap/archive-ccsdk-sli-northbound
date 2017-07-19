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
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation;
import java.util.Objects;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput
 *
 */
public class ServiceCapacityCheckOperationInputBuilder implements Builder <org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput> {

    private CapacityReservationInformation _capacityReservationInformation;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>> augmentation = Collections.emptyMap();

    public ServiceCapacityCheckOperationInputBuilder() {
    }
    public ServiceCapacityCheckOperationInputBuilder(org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation arg) {
        this._capacityReservationInformation = arg.getCapacityReservationInformation();
    }

    public ServiceCapacityCheckOperationInputBuilder(ServiceCapacityCheckOperationInput base) {
        this._capacityReservationInformation = base.getCapacityReservationInformation();
        if (base instanceof ServiceCapacityCheckOperationInputImpl) {
            ServiceCapacityCheckOperationInputImpl impl = (ServiceCapacityCheckOperationInputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }

    /**
     *Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation) {
            this._capacityReservationInformation = ((org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation)arg).getCapacityReservationInformation();
            isValidArg = true;
        }
        if (!isValidArg) {
            throw new IllegalArgumentException(
              "expected one of: [org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation] \n" +
              "but was: " + arg
            );
        }
    }

    public CapacityReservationInformation getCapacityReservationInformation() {
        return _capacityReservationInformation;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ServiceCapacityCheckOperationInputBuilder setCapacityReservationInformation(final CapacityReservationInformation value) {
        this._capacityReservationInformation = value;
        return this;
    }
    
    public ServiceCapacityCheckOperationInputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public ServiceCapacityCheckOperationInputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public ServiceCapacityCheckOperationInput build() {
        return new ServiceCapacityCheckOperationInputImpl(this);
    }

    private static final class ServiceCapacityCheckOperationInputImpl implements ServiceCapacityCheckOperationInput {

        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput.class;
        }

        private final CapacityReservationInformation _capacityReservationInformation;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>> augmentation = Collections.emptyMap();

        private ServiceCapacityCheckOperationInputImpl(ServiceCapacityCheckOperationInputBuilder base) {
            this._capacityReservationInformation = base.getCapacityReservationInformation();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public CapacityReservationInformation getCapacityReservationInformation() {
            return _capacityReservationInformation;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_capacityReservationInformation);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput)obj;
            if (!Objects.equals(_capacityReservationInformation, other.getCapacityReservationInformation())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ServiceCapacityCheckOperationInputImpl otherImpl = (ServiceCapacityCheckOperationInputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityCheckOperationInput>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ServiceCapacityCheckOperationInput [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_capacityReservationInformation != null) {
                builder.append("_capacityReservationInformation=");
                builder.append(_capacityReservationInformation);
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
