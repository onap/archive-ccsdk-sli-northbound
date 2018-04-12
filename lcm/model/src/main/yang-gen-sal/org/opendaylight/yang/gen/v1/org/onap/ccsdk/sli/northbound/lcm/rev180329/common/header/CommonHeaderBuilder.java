package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header.Flags;
import java.util.Collections;
import java.util.Map;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ZULU;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader
 *
 */
public class CommonHeaderBuilder implements Builder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader> {

    private java.lang.String _apiVer;
    private Flags _flags;
    private java.lang.String _originatorId;
    private java.lang.String _requestId;
    private java.lang.String _subRequestId;
    private ZULU _timestamp;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>> augmentation = Collections.emptyMap();

    public CommonHeaderBuilder() {
    }

    public CommonHeaderBuilder(CommonHeader base) {
        this._apiVer = base.getApiVer();
        this._flags = base.getFlags();
        this._originatorId = base.getOriginatorId();
        this._requestId = base.getRequestId();
        this._subRequestId = base.getSubRequestId();
        this._timestamp = base.getTimestamp();
        if (base instanceof CommonHeaderImpl) {
            CommonHeaderImpl impl = (CommonHeaderImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public java.lang.String getApiVer() {
        return _apiVer;
    }
    
    public Flags getFlags() {
        return _flags;
    }
    
    public java.lang.String getOriginatorId() {
        return _originatorId;
    }
    
    public java.lang.String getRequestId() {
        return _requestId;
    }
    
    public java.lang.String getSubRequestId() {
        return _subRequestId;
    }
    
    public ZULU getTimestamp() {
        return _timestamp;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
     private static void check_apiVerLength(final String value) {
     }
    
    public CommonHeaderBuilder setApiVer(final java.lang.String value) {
    if (value != null) {
        check_apiVerLength(value);
    }
        this._apiVer = value;
        return this;
    }
    
     
    public CommonHeaderBuilder setFlags(final Flags value) {
        this._flags = value;
        return this;
    }
    
     
    public CommonHeaderBuilder setOriginatorId(final java.lang.String value) {
        this._originatorId = value;
        return this;
    }
    
     
    public CommonHeaderBuilder setRequestId(final java.lang.String value) {
        this._requestId = value;
        return this;
    }
    
     
    public CommonHeaderBuilder setSubRequestId(final java.lang.String value) {
        this._subRequestId = value;
        return this;
    }
    
     
    public CommonHeaderBuilder setTimestamp(final ZULU value) {
        this._timestamp = value;
        return this;
    }
    
    public CommonHeaderBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public CommonHeaderBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public CommonHeader build() {
        return new CommonHeaderImpl(this);
    }

    private static final class CommonHeaderImpl implements CommonHeader {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader.class;
        }

        private final java.lang.String _apiVer;
        private final Flags _flags;
        private final java.lang.String _originatorId;
        private final java.lang.String _requestId;
        private final java.lang.String _subRequestId;
        private final ZULU _timestamp;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>> augmentation = Collections.emptyMap();

        private CommonHeaderImpl(CommonHeaderBuilder base) {
            this._apiVer = base.getApiVer();
            this._flags = base.getFlags();
            this._originatorId = base.getOriginatorId();
            this._requestId = base.getRequestId();
            this._subRequestId = base.getSubRequestId();
            this._timestamp = base.getTimestamp();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public java.lang.String getApiVer() {
            return _apiVer;
        }
        
        @Override
        public Flags getFlags() {
            return _flags;
        }
        
        @Override
        public java.lang.String getOriginatorId() {
            return _originatorId;
        }
        
        @Override
        public java.lang.String getRequestId() {
            return _requestId;
        }
        
        @Override
        public java.lang.String getSubRequestId() {
            return _subRequestId;
        }
        
        @Override
        public ZULU getTimestamp() {
            return _timestamp;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_apiVer);
            result = prime * result + Objects.hashCode(_flags);
            result = prime * result + Objects.hashCode(_originatorId);
            result = prime * result + Objects.hashCode(_requestId);
            result = prime * result + Objects.hashCode(_subRequestId);
            result = prime * result + Objects.hashCode(_timestamp);
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
            if (!org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader other = (org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader)obj;
            if (!Objects.equals(_apiVer, other.getApiVer())) {
                return false;
            }
            if (!Objects.equals(_flags, other.getFlags())) {
                return false;
            }
            if (!Objects.equals(_originatorId, other.getOriginatorId())) {
                return false;
            }
            if (!Objects.equals(_requestId, other.getRequestId())) {
                return false;
            }
            if (!Objects.equals(_subRequestId, other.getSubRequestId())) {
                return false;
            }
            if (!Objects.equals(_timestamp, other.getTimestamp())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                CommonHeaderImpl otherImpl = (CommonHeaderImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>> e : augmentation.entrySet()) {
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
            java.lang.String name = "CommonHeader [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_apiVer != null) {
                builder.append("_apiVer=");
                builder.append(_apiVer);
                builder.append(", ");
            }
            if (_flags != null) {
                builder.append("_flags=");
                builder.append(_flags);
                builder.append(", ");
            }
            if (_originatorId != null) {
                builder.append("_originatorId=");
                builder.append(_originatorId);
                builder.append(", ");
            }
            if (_requestId != null) {
                builder.append("_requestId=");
                builder.append(_requestId);
                builder.append(", ");
            }
            if (_subRequestId != null) {
                builder.append("_subRequestId=");
                builder.append(_subRequestId);
                builder.append(", ");
            }
            if (_timestamp != null) {
                builder.append("_timestamp=");
                builder.append(_timestamp);
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
