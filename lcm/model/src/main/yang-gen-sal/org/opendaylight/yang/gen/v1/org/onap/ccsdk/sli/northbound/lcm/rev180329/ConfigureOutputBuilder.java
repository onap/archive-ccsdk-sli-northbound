package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.Status;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.Collections;
import java.util.Map;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput
 *
 */
public class ConfigureOutputBuilder implements Builder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput> {

    private CommonHeader _commonHeader;
    private Payload _payload;
    private Status _status;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>> augmentation = Collections.emptyMap();

    public ConfigureOutputBuilder() {
    }
    public ConfigureOutputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Status arg) {
        this._status = arg.getStatus();
    }
    public ConfigureOutputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader arg) {
        this._commonHeader = arg.getCommonHeader();
    }

    public ConfigureOutputBuilder(ConfigureOutput base) {
        this._commonHeader = base.getCommonHeader();
        this._payload = base.getPayload();
        this._status = base.getStatus();
        if (base instanceof ConfigureOutputImpl) {
            ConfigureOutputImpl impl = (ConfigureOutputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }

    /**
     *Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader</li>
     * <li>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Status</li>
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
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Status) {
            this._status = ((org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Status)arg).getStatus();
            isValidArg = true;
        }
        if (!isValidArg) {
            throw new IllegalArgumentException(
              "expected one of: [org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader, org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Status] \n" +
              "but was: " + arg
            );
        }
    }

    public CommonHeader getCommonHeader() {
        return _commonHeader;
    }
    
    public Payload getPayload() {
        return _payload;
    }
    
    public Status getStatus() {
        return _status;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ConfigureOutputBuilder setCommonHeader(final CommonHeader value) {
        this._commonHeader = value;
        return this;
    }
    
     
    public ConfigureOutputBuilder setPayload(final Payload value) {
        this._payload = value;
        return this;
    }
    
     
    public ConfigureOutputBuilder setStatus(final Status value) {
        this._status = value;
        return this;
    }
    
    public ConfigureOutputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public ConfigureOutputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public ConfigureOutput build() {
        return new ConfigureOutputImpl(this);
    }

    private static final class ConfigureOutputImpl implements ConfigureOutput {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput.class;
        }

        private final CommonHeader _commonHeader;
        private final Payload _payload;
        private final Status _status;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>> augmentation = Collections.emptyMap();

        private ConfigureOutputImpl(ConfigureOutputBuilder base) {
            this._commonHeader = base.getCommonHeader();
            this._payload = base.getPayload();
            this._status = base.getStatus();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public CommonHeader getCommonHeader() {
            return _commonHeader;
        }
        
        @Override
        public Payload getPayload() {
            return _payload;
        }
        
        @Override
        public Status getStatus() {
            return _status;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_commonHeader);
            result = prime * result + Objects.hashCode(_payload);
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
            if (!org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput other = (org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput)obj;
            if (!Objects.equals(_commonHeader, other.getCommonHeader())) {
                return false;
            }
            if (!Objects.equals(_payload, other.getPayload())) {
                return false;
            }
            if (!Objects.equals(_status, other.getStatus())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ConfigureOutputImpl otherImpl = (ConfigureOutputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigureOutput>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ConfigureOutput [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_commonHeader != null) {
                builder.append("_commonHeader=");
                builder.append(_commonHeader);
                builder.append(", ");
            }
            if (_payload != null) {
                builder.append("_payload=");
                builder.append(_payload);
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
