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
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput
 *
 */
public class TestOutputBuilder implements Builder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput> {

    private CommonHeader _commonHeader;
    private Status _status;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>> augmentation = Collections.emptyMap();

    public TestOutputBuilder() {
    }
    public TestOutputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Status arg) {
        this._status = arg.getStatus();
    }
    public TestOutputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader arg) {
        this._commonHeader = arg.getCommonHeader();
    }

    public TestOutputBuilder(TestOutput base) {
        this._commonHeader = base.getCommonHeader();
        this._status = base.getStatus();
        if (base instanceof TestOutputImpl) {
            TestOutputImpl impl = (TestOutputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>) base;
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
    
    public Status getStatus() {
        return _status;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public TestOutputBuilder setCommonHeader(final CommonHeader value) {
        this._commonHeader = value;
        return this;
    }
    
     
    public TestOutputBuilder setStatus(final Status value) {
        this._status = value;
        return this;
    }
    
    public TestOutputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public TestOutputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public TestOutput build() {
        return new TestOutputImpl(this);
    }

    private static final class TestOutputImpl implements TestOutput {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput.class;
        }

        private final CommonHeader _commonHeader;
        private final Status _status;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>> augmentation = Collections.emptyMap();

        private TestOutputImpl(TestOutputBuilder base) {
            this._commonHeader = base.getCommonHeader();
            this._status = base.getStatus();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>>singletonMap(e.getKey(), e.getValue());
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
        public Status getStatus() {
            return _status;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            if (!org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput other = (org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput)obj;
            if (!Objects.equals(_commonHeader, other.getCommonHeader())) {
                return false;
            }
            if (!Objects.equals(_status, other.getStatus())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                TestOutputImpl otherImpl = (TestOutputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.TestOutput>> e : augmentation.entrySet()) {
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
            java.lang.String name = "TestOutput [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_commonHeader != null) {
                builder.append("_commonHeader=");
                builder.append(_commonHeader);
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
