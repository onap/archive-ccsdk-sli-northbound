package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * Flags are generic flags that apply to any and all commands, all are optional
 *
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * container flags {
 *     leaf mode {
 *         type enumeration;
 *     }
 *     leaf force {
 *         type enumeration;
 *     }
 *     leaf ttl {
 *         type uint16;
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/common-header/common-header/flags</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header.FlagsBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header.FlagsBuilder
 *
 */
public interface Flags
    extends
    ChildOf<CommonHeader>,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header.Flags>
{


    public enum Mode {
        EXCLUSIVE(0, "EXCLUSIVE"),
        
        NORMAL(1, "NORMAL")
        ;
    
        private static final java.util.Map<java.lang.Integer, Mode> VALUE_MAP;
    
        static {
            final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, Mode> b = com.google.common.collect.ImmutableMap.builder();
            for (Mode enumItem : Mode.values()) {
                b.put(enumItem.value, enumItem);
            }
    
            VALUE_MAP = b.build();
        }
    
        private final java.lang.String name;
        private final int value;
    
        private Mode(int value, java.lang.String name) {
            this.value = value;
            this.name = name;
        }
    
        /**
         * Returns the name of the enumeration item as it is specified in the input yang.
         *
         * @return the name of the enumeration item as it is specified in the input yang
         */
        public java.lang.String getName() {
            return name;
        }
    
        /**
         * @return integer value
         */
        public int getIntValue() {
            return value;
        }
    
        /**
         * @param valueArg integer value
         * @return corresponding Mode item
         */
        public static Mode forValue(int valueArg) {
            return VALUE_MAP.get(valueArg);
        }
    }
    
    public enum Force {
        TRUE(0, "TRUE"),
        
        FALSE(1, "FALSE")
        ;
    
        private static final java.util.Map<java.lang.Integer, Force> VALUE_MAP;
    
        static {
            final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, Force> b = com.google.common.collect.ImmutableMap.builder();
            for (Force enumItem : Force.values()) {
                b.put(enumItem.value, enumItem);
            }
    
            VALUE_MAP = b.build();
        }
    
        private final java.lang.String name;
        private final int value;
    
        private Force(int value, java.lang.String name) {
            this.value = value;
            this.name = name;
        }
    
        /**
         * Returns the name of the enumeration item as it is specified in the input yang.
         *
         * @return the name of the enumeration item as it is specified in the input yang
         */
        public java.lang.String getName() {
            return name;
        }
    
        /**
         * @return integer value
         */
        public int getIntValue() {
            return value;
        }
    
        /**
         * @param valueArg integer value
         * @return corresponding Force item
         */
        public static Force forValue(int valueArg) {
            return VALUE_MAP.get(valueArg);
        }
    }

    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "flags").intern();

    /**
     * EXCLUSIVE (accept no queued requests on this VNF while processing) or NORMAL 
     * (queue other requests until complete)
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header.Flags.Mode</code> <code>mode</code>, or <code>null</code> if not present
     */
    Mode getMode();
    
    /**
     * TRUE/FALSE - Execute action even if target is in unstable (i.e. locked, 
     * transiting, etc.) state
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header.Flags.Force</code> <code>force</code>, or <code>null</code> if not present
     */
    Force getForce();
    
    /**
     * &amp;lt;0....N&amp;gt; - The timeout value (expressed in seconds) for action 
     * execution, between action being received by APPC and action initiation
     *
     *
     *
     * @return <code>java.lang.Integer</code> <code>ttl</code>, or <code>null</code> if not present
     */
    java.lang.Integer getTtl();

}

