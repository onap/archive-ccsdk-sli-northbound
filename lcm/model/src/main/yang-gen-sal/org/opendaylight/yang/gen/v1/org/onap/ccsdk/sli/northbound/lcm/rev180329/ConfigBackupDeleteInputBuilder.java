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
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput
 *
 */
public class ConfigBackupDeleteInputBuilder implements Builder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput> {

    private Action _action;
    private ActionIdentifiers _actionIdentifiers;
    private CommonHeader _commonHeader;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>> augmentation = Collections.emptyMap();

    public ConfigBackupDeleteInputBuilder() {
    }
    public ConfigBackupDeleteInputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ActionIdentifiers arg) {
        this._actionIdentifiers = arg.getActionIdentifiers();
    }
    public ConfigBackupDeleteInputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader arg) {
        this._commonHeader = arg.getCommonHeader();
    }

    public ConfigBackupDeleteInputBuilder(ConfigBackupDeleteInput base) {
        this._action = base.getAction();
        this._actionIdentifiers = base.getActionIdentifiers();
        this._commonHeader = base.getCommonHeader();
        if (base instanceof ConfigBackupDeleteInputImpl) {
            ConfigBackupDeleteInputImpl impl = (ConfigBackupDeleteInputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>) base;
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
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ConfigBackupDeleteInputBuilder setAction(final Action value) {
        this._action = value;
        return this;
    }
    
     
    public ConfigBackupDeleteInputBuilder setActionIdentifiers(final ActionIdentifiers value) {
        this._actionIdentifiers = value;
        return this;
    }
    
     
    public ConfigBackupDeleteInputBuilder setCommonHeader(final CommonHeader value) {
        this._commonHeader = value;
        return this;
    }
    
    public ConfigBackupDeleteInputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public ConfigBackupDeleteInputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public ConfigBackupDeleteInput build() {
        return new ConfigBackupDeleteInputImpl(this);
    }

    private static final class ConfigBackupDeleteInputImpl implements ConfigBackupDeleteInput {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput.class;
        }

        private final Action _action;
        private final ActionIdentifiers _actionIdentifiers;
        private final CommonHeader _commonHeader;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>> augmentation = Collections.emptyMap();

        private ConfigBackupDeleteInputImpl(ConfigBackupDeleteInputBuilder base) {
            this._action = base.getAction();
            this._actionIdentifiers = base.getActionIdentifiers();
            this._commonHeader = base.getCommonHeader();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>>singletonMap(e.getKey(), e.getValue());
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
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            if (!org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput other = (org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput)obj;
            if (!Objects.equals(_action, other.getAction())) {
                return false;
            }
            if (!Objects.equals(_actionIdentifiers, other.getActionIdentifiers())) {
                return false;
            }
            if (!Objects.equals(_commonHeader, other.getCommonHeader())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ConfigBackupDeleteInputImpl otherImpl = (ConfigBackupDeleteInputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ConfigBackupDeleteInput [";
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
