package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import java.io.Serializable;
import java.beans.ConstructorProperties;
import com.google.common.base.Preconditions;
import java.util.Objects;

/**
 * The payload can be any valid JSON string value. Json escape characters need to be added when required to include an inner json within the payload to make it a valid json string value
 *
 */
public class Payload
 implements Serializable {
    private static final long serialVersionUID = -1825016986636880732L;
    private final java.lang.String _value;


    @ConstructorProperties("value")
    public Payload(java.lang.String _value) {
    
    
        Preconditions.checkNotNull(_value, "Supplied value may not be null");
    
    
        this._value = _value;
    }
    
    /**
     * Creates a copy from Source Object.
     *
     * @param source Source object
     */
    public Payload(Payload source) {
        this._value = source._value;
    }

    public static Payload getDefaultInstance(String defaultValue) {
        return new Payload(defaultValue);
    }

    public java.lang.String getValue() {
        return _value;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(_value);
        return result;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Payload other = (Payload) obj;
        if (!Objects.equals(_value, other._value)) {
            return false;
        }
        return true;
    }

    @Override
    public java.lang.String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder(org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Payload.class.getSimpleName()).append(" [");
        boolean first = true;
    
        if (_value != null) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("_value=");
            builder.append(_value);
        }
        return builder.append(']').toString();
    }
}

