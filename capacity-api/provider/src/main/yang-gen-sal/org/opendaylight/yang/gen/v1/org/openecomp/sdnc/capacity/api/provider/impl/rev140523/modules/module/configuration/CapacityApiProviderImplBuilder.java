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

package org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.NotificationService;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.capacity.api.provider.impl.DataBroker;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl
 *
 */
public class CapacityApiProviderImplBuilder implements Builder <org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl> {

    private DataBroker _dataBroker;
    private NotificationService _notificationService;
    private RpcRegistry _rpcRegistry;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>> augmentation = Collections.emptyMap();

    public CapacityApiProviderImplBuilder() {
    }

    public CapacityApiProviderImplBuilder(CapacityApiProviderImpl base) {
        this._dataBroker = base.getDataBroker();
        this._notificationService = base.getNotificationService();
        this._rpcRegistry = base.getRpcRegistry();
        if (base instanceof CapacityApiProviderImplImpl) {
            CapacityApiProviderImplImpl impl = (CapacityApiProviderImplImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public DataBroker getDataBroker() {
        return _dataBroker;
    }
    
    public NotificationService getNotificationService() {
        return _notificationService;
    }
    
    public RpcRegistry getRpcRegistry() {
        return _rpcRegistry;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public CapacityApiProviderImplBuilder setDataBroker(final DataBroker value) {
        this._dataBroker = value;
        return this;
    }
    
     
    public CapacityApiProviderImplBuilder setNotificationService(final NotificationService value) {
        this._notificationService = value;
        return this;
    }
    
     
    public CapacityApiProviderImplBuilder setRpcRegistry(final RpcRegistry value) {
        this._rpcRegistry = value;
        return this;
    }
    
    public CapacityApiProviderImplBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public CapacityApiProviderImplBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public CapacityApiProviderImpl build() {
        return new CapacityApiProviderImplImpl(this);
    }

    private static final class CapacityApiProviderImplImpl implements CapacityApiProviderImpl {

        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl.class;
        }

        private final DataBroker _dataBroker;
        private final NotificationService _notificationService;
        private final RpcRegistry _rpcRegistry;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>> augmentation = Collections.emptyMap();

        private CapacityApiProviderImplImpl(CapacityApiProviderImplBuilder base) {
            this._dataBroker = base.getDataBroker();
            this._notificationService = base.getNotificationService();
            this._rpcRegistry = base.getRpcRegistry();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public DataBroker getDataBroker() {
            return _dataBroker;
        }
        
        @Override
        public NotificationService getNotificationService() {
            return _notificationService;
        }
        
        @Override
        public RpcRegistry getRpcRegistry() {
            return _rpcRegistry;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_dataBroker);
            result = prime * result + Objects.hashCode(_notificationService);
            result = prime * result + Objects.hashCode(_rpcRegistry);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl)obj;
            if (!Objects.equals(_dataBroker, other.getDataBroker())) {
                return false;
            }
            if (!Objects.equals(_notificationService, other.getNotificationService())) {
                return false;
            }
            if (!Objects.equals(_rpcRegistry, other.getRpcRegistry())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                CapacityApiProviderImplImpl otherImpl = (CapacityApiProviderImplImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnc.capacity.api.provider.impl.rev140523.modules.module.configuration.CapacityApiProviderImpl>> e : augmentation.entrySet()) {
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
            java.lang.String name = "CapacityApiProviderImpl [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_dataBroker != null) {
                builder.append("_dataBroker=");
                builder.append(_dataBroker);
                builder.append(", ");
            }
            if (_notificationService != null) {
                builder.append("_notificationService=");
                builder.append(_notificationService);
                builder.append(", ");
            }
            if (_rpcRegistry != null) {
                builder.append("_rpcRegistry=");
                builder.append(_rpcRegistry);
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
