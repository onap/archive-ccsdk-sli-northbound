package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.Collections;
import java.util.Map;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers
 *
 */
public class ActionIdentifiersBuilder implements Builder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers> {

    private java.lang.String _serviceInstanceId;
    private java.lang.String _vfModuleId;
    private java.lang.String _vnfId;
    private java.lang.String _vnfcName;
    private java.lang.String _vserverId;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>> augmentation = Collections.emptyMap();

    public ActionIdentifiersBuilder() {
    }

    public ActionIdentifiersBuilder(ActionIdentifiers base) {
        this._serviceInstanceId = base.getServiceInstanceId();
        this._vfModuleId = base.getVfModuleId();
        this._vnfId = base.getVnfId();
        this._vnfcName = base.getVnfcName();
        this._vserverId = base.getVserverId();
        if (base instanceof ActionIdentifiersImpl) {
            ActionIdentifiersImpl impl = (ActionIdentifiersImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public java.lang.String getServiceInstanceId() {
        return _serviceInstanceId;
    }
    
    public java.lang.String getVfModuleId() {
        return _vfModuleId;
    }
    
    public java.lang.String getVnfId() {
        return _vnfId;
    }
    
    public java.lang.String getVnfcName() {
        return _vnfcName;
    }
    
    public java.lang.String getVserverId() {
        return _vserverId;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ActionIdentifiersBuilder setServiceInstanceId(final java.lang.String value) {
        this._serviceInstanceId = value;
        return this;
    }
    
     
    public ActionIdentifiersBuilder setVfModuleId(final java.lang.String value) {
        this._vfModuleId = value;
        return this;
    }
    
     
    public ActionIdentifiersBuilder setVnfId(final java.lang.String value) {
        this._vnfId = value;
        return this;
    }
    
     
    public ActionIdentifiersBuilder setVnfcName(final java.lang.String value) {
        this._vnfcName = value;
        return this;
    }
    
     
    public ActionIdentifiersBuilder setVserverId(final java.lang.String value) {
        this._vserverId = value;
        return this;
    }
    
    public ActionIdentifiersBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public ActionIdentifiersBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public ActionIdentifiers build() {
        return new ActionIdentifiersImpl(this);
    }

    private static final class ActionIdentifiersImpl implements ActionIdentifiers {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers.class;
        }

        private final java.lang.String _serviceInstanceId;
        private final java.lang.String _vfModuleId;
        private final java.lang.String _vnfId;
        private final java.lang.String _vnfcName;
        private final java.lang.String _vserverId;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>> augmentation = Collections.emptyMap();

        private ActionIdentifiersImpl(ActionIdentifiersBuilder base) {
            this._serviceInstanceId = base.getServiceInstanceId();
            this._vfModuleId = base.getVfModuleId();
            this._vnfId = base.getVnfId();
            this._vnfcName = base.getVnfcName();
            this._vserverId = base.getVserverId();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public java.lang.String getServiceInstanceId() {
            return _serviceInstanceId;
        }
        
        @Override
        public java.lang.String getVfModuleId() {
            return _vfModuleId;
        }
        
        @Override
        public java.lang.String getVnfId() {
            return _vnfId;
        }
        
        @Override
        public java.lang.String getVnfcName() {
            return _vnfcName;
        }
        
        @Override
        public java.lang.String getVserverId() {
            return _vserverId;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_serviceInstanceId);
            result = prime * result + Objects.hashCode(_vfModuleId);
            result = prime * result + Objects.hashCode(_vnfId);
            result = prime * result + Objects.hashCode(_vnfcName);
            result = prime * result + Objects.hashCode(_vserverId);
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
            if (!org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers other = (org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers)obj;
            if (!Objects.equals(_serviceInstanceId, other.getServiceInstanceId())) {
                return false;
            }
            if (!Objects.equals(_vfModuleId, other.getVfModuleId())) {
                return false;
            }
            if (!Objects.equals(_vnfId, other.getVnfId())) {
                return false;
            }
            if (!Objects.equals(_vnfcName, other.getVnfcName())) {
                return false;
            }
            if (!Objects.equals(_vserverId, other.getVserverId())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ActionIdentifiersImpl otherImpl = (ActionIdentifiersImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ActionIdentifiers [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_serviceInstanceId != null) {
                builder.append("_serviceInstanceId=");
                builder.append(_serviceInstanceId);
                builder.append(", ");
            }
            if (_vfModuleId != null) {
                builder.append("_vfModuleId=");
                builder.append(_vfModuleId);
                builder.append(", ");
            }
            if (_vnfId != null) {
                builder.append("_vnfId=");
                builder.append(_vnfId);
                builder.append(", ");
            }
            if (_vnfcName != null) {
                builder.append("_vnfcName=");
                builder.append(_vnfcName);
                builder.append(", ");
            }
            if (_vserverId != null) {
                builder.append("_vserverId=");
                builder.append(_vserverId);
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
