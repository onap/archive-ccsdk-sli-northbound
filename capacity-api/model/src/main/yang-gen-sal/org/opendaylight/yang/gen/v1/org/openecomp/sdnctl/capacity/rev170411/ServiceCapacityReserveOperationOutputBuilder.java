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
import java.util.Objects;
import java.util.Collections;
import java.util.Map;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.information.CapacityInformation;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput
 *
 */
public class ServiceCapacityReserveOperationOutputBuilder implements Builder <org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput> {

    private CapacityInformation _capacityInformation;
    private java.lang.String _responseCode;
    private java.lang.String _responseMessage;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>> augmentation = Collections.emptyMap();

    public ServiceCapacityReserveOperationOutputBuilder() {
    }
    public ServiceCapacityReserveOperationOutputBuilder(org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityInformation arg) {
        this._capacityInformation = arg.getCapacityInformation();
    }

    public ServiceCapacityReserveOperationOutputBuilder(ServiceCapacityReserveOperationOutput base) {
        this._capacityInformation = base.getCapacityInformation();
        this._responseCode = base.getResponseCode();
        this._responseMessage = base.getResponseMessage();
        if (base instanceof ServiceCapacityReserveOperationOutputImpl) {
            ServiceCapacityReserveOperationOutputImpl impl = (ServiceCapacityReserveOperationOutputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }

    /**
     *Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityInformation</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityInformation) {
            this._capacityInformation = ((org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityInformation)arg).getCapacityInformation();
            isValidArg = true;
        }
        if (!isValidArg) {
            throw new IllegalArgumentException(
              "expected one of: [org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityInformation] \n" +
              "but was: " + arg
            );
        }
    }

    public CapacityInformation getCapacityInformation() {
        return _capacityInformation;
    }
    
    public java.lang.String getResponseCode() {
        return _responseCode;
    }
    
    public java.lang.String getResponseMessage() {
        return _responseMessage;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ServiceCapacityReserveOperationOutputBuilder setCapacityInformation(final CapacityInformation value) {
        this._capacityInformation = value;
        return this;
    }
    
     
    public ServiceCapacityReserveOperationOutputBuilder setResponseCode(final java.lang.String value) {
        this._responseCode = value;
        return this;
    }
    
     
    public ServiceCapacityReserveOperationOutputBuilder setResponseMessage(final java.lang.String value) {
        this._responseMessage = value;
        return this;
    }
    
    public ServiceCapacityReserveOperationOutputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public ServiceCapacityReserveOperationOutputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public ServiceCapacityReserveOperationOutput build() {
        return new ServiceCapacityReserveOperationOutputImpl(this);
    }

    private static final class ServiceCapacityReserveOperationOutputImpl implements ServiceCapacityReserveOperationOutput {

        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput.class;
        }

        private final CapacityInformation _capacityInformation;
        private final java.lang.String _responseCode;
        private final java.lang.String _responseMessage;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>> augmentation = Collections.emptyMap();

        private ServiceCapacityReserveOperationOutputImpl(ServiceCapacityReserveOperationOutputBuilder base) {
            this._capacityInformation = base.getCapacityInformation();
            this._responseCode = base.getResponseCode();
            this._responseMessage = base.getResponseMessage();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public CapacityInformation getCapacityInformation() {
            return _capacityInformation;
        }
        
        @Override
        public java.lang.String getResponseCode() {
            return _responseCode;
        }
        
        @Override
        public java.lang.String getResponseMessage() {
            return _responseMessage;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_capacityInformation);
            result = prime * result + Objects.hashCode(_responseCode);
            result = prime * result + Objects.hashCode(_responseMessage);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput)obj;
            if (!Objects.equals(_capacityInformation, other.getCapacityInformation())) {
                return false;
            }
            if (!Objects.equals(_responseCode, other.getResponseCode())) {
                return false;
            }
            if (!Objects.equals(_responseMessage, other.getResponseMessage())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ServiceCapacityReserveOperationOutputImpl otherImpl = (ServiceCapacityReserveOperationOutputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReserveOperationOutput>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ServiceCapacityReserveOperationOutput [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_capacityInformation != null) {
                builder.append("_capacityInformation=");
                builder.append(_capacityInformation);
                builder.append(", ");
            }
            if (_responseCode != null) {
                builder.append("_responseCode=");
                builder.append(_responseCode);
                builder.append(", ");
            }
            if (_responseMessage != null) {
                builder.append("_responseMessage=");
                builder.append(_responseMessage);
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
