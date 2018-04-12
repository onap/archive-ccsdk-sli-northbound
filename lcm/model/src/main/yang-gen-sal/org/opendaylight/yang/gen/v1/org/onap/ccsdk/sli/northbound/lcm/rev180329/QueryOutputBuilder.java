package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.Status;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.query.output.QueryResults;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput
 *
 */
public class QueryOutputBuilder implements Builder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput> {

    private CommonHeader _commonHeader;
    private List<QueryResults> _queryResults;
    private Status _status;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>> augmentation = Collections.emptyMap();

    public QueryOutputBuilder() {
    }
    public QueryOutputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Status arg) {
        this._status = arg.getStatus();
    }
    public QueryOutputBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader arg) {
        this._commonHeader = arg.getCommonHeader();
    }

    public QueryOutputBuilder(QueryOutput base) {
        this._commonHeader = base.getCommonHeader();
        this._queryResults = base.getQueryResults();
        this._status = base.getStatus();
        if (base instanceof QueryOutputImpl) {
            QueryOutputImpl impl = (QueryOutputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>) base;
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
    
    public List<QueryResults> getQueryResults() {
        return _queryResults;
    }
    
    public Status getStatus() {
        return _status;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public QueryOutputBuilder setCommonHeader(final CommonHeader value) {
        this._commonHeader = value;
        return this;
    }
    
     
    public QueryOutputBuilder setQueryResults(final List<QueryResults> value) {
        this._queryResults = value;
        return this;
    }
    
     
    public QueryOutputBuilder setStatus(final Status value) {
        this._status = value;
        return this;
    }
    
    public QueryOutputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public QueryOutputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public QueryOutput build() {
        return new QueryOutputImpl(this);
    }

    private static final class QueryOutputImpl implements QueryOutput {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput.class;
        }

        private final CommonHeader _commonHeader;
        private final List<QueryResults> _queryResults;
        private final Status _status;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>> augmentation = Collections.emptyMap();

        private QueryOutputImpl(QueryOutputBuilder base) {
            this._commonHeader = base.getCommonHeader();
            this._queryResults = base.getQueryResults();
            this._status = base.getStatus();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>>singletonMap(e.getKey(), e.getValue());
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
        public List<QueryResults> getQueryResults() {
            return _queryResults;
        }
        
        @Override
        public Status getStatus() {
            return _status;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_queryResults);
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
            if (!org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput other = (org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput)obj;
            if (!Objects.equals(_commonHeader, other.getCommonHeader())) {
                return false;
            }
            if (!Objects.equals(_queryResults, other.getQueryResults())) {
                return false;
            }
            if (!Objects.equals(_status, other.getStatus())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                QueryOutputImpl otherImpl = (QueryOutputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>>, Augmentation<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>> e : augmentation.entrySet()) {
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
            java.lang.String name = "QueryOutput [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_commonHeader != null) {
                builder.append("_commonHeader=");
                builder.append(_commonHeader);
                builder.append(", ");
            }
            if (_queryResults != null) {
                builder.append("_queryResults=");
                builder.append(_queryResults);
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
