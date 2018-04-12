package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.Collections;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers;
import java.util.Map;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput
 *
 */
public class ConfigScaleOutInputBuilder implements Builder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput> {

    private Action _action;
    private ActionIdentifiers _actionIdentifiers;
    private CommonHeader _commonHeader;
    private Payload _payload;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>> augmentation = Collections.emptyMap();

    public ConfigScaleOutInputBuilder() {
    }
    public ConfigScaleOutInputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ActionIdentifiers arg) {
        this._actionIdentifiers = arg.getActionIdentifiers();
    }
    public ConfigScaleOutInputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader arg) {
        this._commonHeader = arg.getCommonHeader();
    }

    public ConfigScaleOutInputBuilder(ConfigScaleOutInput base) {
        this._action = base.getAction();
        this._actionIdentifiers = base.getActionIdentifiers();
        this._commonHeader = base.getCommonHeader();
        this._payload = base.getPayload();
        if (base instanceof ConfigScaleOutInputImpl) {
            ConfigScaleOutInputImpl impl = (ConfigScaleOutInputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }

    /**
     *Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader</li>
     * <li>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ActionIdentifiers</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader) {
            this._commonHeader = ((org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader)arg).getCommonHeader();
            isValidArg = true;
        }
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ActionIdentifiers) {
            this._actionIdentifiers = ((org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ActionIdentifiers)arg).getActionIdentifiers();
            isValidArg = true;
        }
        if (!isValidArg) {
            throw new IllegalArgumentException(
              "expected one of: [org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader, org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ActionIdentifiers] \n" +
              "but was: " + arg
            );
        }
    }

    public Action getAction() {
        return _action;
    }
    
    public ActionIdentifiers getActionIdentifiers() {
        return _actionIdentifiers;
    }
    
    public CommonHeader getCommonHeader() {
        return _commonHeader;
    }
    
    public Payload getPayload() {
        return _payload;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ConfigScaleOutInputBuilder setAction(final Action value) {
        this._action = value;
        return this;
    }
    
     
    public ConfigScaleOutInputBuilder setActionIdentifiers(final ActionIdentifiers value) {
        this._actionIdentifiers = value;
        return this;
    }
    
     
    public ConfigScaleOutInputBuilder setCommonHeader(final CommonHeader value) {
        this._commonHeader = value;
        return this;
    }
    
     
    public ConfigScaleOutInputBuilder setPayload(final Payload value) {
        this._payload = value;
        return this;
    }
    
    public ConfigScaleOutInputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public ConfigScaleOutInputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public ConfigScaleOutInput build() {
        return new ConfigScaleOutInputImpl(this);
    }

    private static final class ConfigScaleOutInputImpl implements ConfigScaleOutInput {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput.class;
        }

        private final Action _action;
        private final ActionIdentifiers _actionIdentifiers;
        private final CommonHeader _commonHeader;
        private final Payload _payload;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>> augmentation = Collections.emptyMap();

        private ConfigScaleOutInputImpl(ConfigScaleOutInputBuilder base) {
            this._action = base.getAction();
            this._actionIdentifiers = base.getActionIdentifiers();
            this._commonHeader = base.getCommonHeader();
            this._payload = base.getPayload();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public Action getAction() {
            return _action;
        }
        
        @Override
        public ActionIdentifiers getActionIdentifiers() {
            return _actionIdentifiers;
        }
        
        @Override
        public CommonHeader getCommonHeader() {
            return _commonHeader;
        }
        
        @Override
        public Payload getPayload() {
            return _payload;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_action);
            result = prime * result + Objects.hashCode(_actionIdentifiers);
            result = prime * result + Objects.hashCode(_commonHeader);
            result = prime * result + Objects.hashCode(_payload);
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
            if (!org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput other = (org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput)obj;
            if (!Objects.equals(_action, other.getAction())) {
                return false;
            }
            if (!Objects.equals(_actionIdentifiers, other.getActionIdentifiers())) {
                return false;
            }
            if (!Objects.equals(_commonHeader, other.getCommonHeader())) {
                return false;
            }
            if (!Objects.equals(_payload, other.getPayload())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ConfigScaleOutInputImpl otherImpl = (ConfigScaleOutInputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigScaleOutInput>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ConfigScaleOutInput [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_action != null) {
                builder.append("_action=");
                builder.append(_action);
                builder.append(", ");
            }
            if (_actionIdentifiers != null) {
                builder.append("_actionIdentifiers=");
                builder.append(_actionIdentifiers);
                builder.append(", ");
            }
            if (_commonHeader != null) {
                builder.append("_commonHeader=");
                builder.append(_commonHeader);
                builder.append(", ");
            }
            if (_payload != null) {
                builder.append("_payload=");
                builder.append(_payload);
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
