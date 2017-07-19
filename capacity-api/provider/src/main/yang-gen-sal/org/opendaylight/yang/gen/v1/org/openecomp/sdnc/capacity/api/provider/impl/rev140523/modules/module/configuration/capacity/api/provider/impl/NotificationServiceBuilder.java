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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService
 *
 */
public class NotificationServiceBuilder implements Builder <org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService> {

    private java.lang.Object _name;
    private java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> _type;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>> augmentation = Collections.emptyMap();

    public NotificationServiceBuilder() {
    }
    public NotificationServiceBuilder(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef arg) {
        this._type = arg.getType();
        this._name = arg.getName();
    }

    public NotificationServiceBuilder(NotificationService base) {
        this._name = base.getName();
        this._type = base.getType();
        if (base instanceof NotificationServiceImpl) {
            NotificationServiceImpl impl = (NotificationServiceImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }

    /**
     *Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef) {
            this._type = ((org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef)arg).getType();
            this._name = ((org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef)arg).getName();
            isValidArg = true;
        }
        if (!isValidArg) {
            throw new IllegalArgumentException(
              "expected one of: [org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef] \n" +
              "but was: " + arg
            );
        }
    }

    public java.lang.Object getName() {
        return _name;
    }
    
    public java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> getType() {
        return _type;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public NotificationServiceBuilder setName(final java.lang.Object value) {
        this._name = value;
        return this;
    }
    
     
    public NotificationServiceBuilder setType(final java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> value) {
        this._type = value;
        return this;
    }
    
    public NotificationServiceBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public NotificationServiceBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public NotificationService build() {
        return new NotificationServiceImpl(this);
    }

    private static final class NotificationServiceImpl implements NotificationService {

        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService.class;
        }

        private final java.lang.Object _name;
        private final java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> _type;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>> augmentation = Collections.emptyMap();

        private NotificationServiceImpl(NotificationServiceBuilder base) {
            this._name = base.getName();
            this._type = base.getType();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public java.lang.Object getName() {
            return _name;
        }
        
        @Override
        public java.lang.Class<? extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceType> getType() {
            return _type;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_name);
            result = prime * result + Objects.hashCode(_type);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService)obj;
            if (!Objects.equals(_name, other.getName())) {
                return false;
            }
            if (!Objects.equals(_type, other.getType())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                NotificationServiceImpl otherImpl = (NotificationServiceImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService>> e : augmentation.entrySet()) {
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
            java.lang.String name = "NotificationService [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_name != null) {
                builder.append("_name=");
                builder.append(_name);
                builder.append(", ");
            }
            if (_type != null) {
                builder.append("_type=");
                builder.append(_type);
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
