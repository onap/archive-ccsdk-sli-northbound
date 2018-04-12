package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * container output {
 *     container common-header {
 *         leaf timestamp {
 *             type ZULU;
 *         }
 *         leaf api-ver {
 *             type string;
 *         }
 *         leaf originator-id {
 *             type string;
 *         }
 *         leaf request-id {
 *             type string;
 *         }
 *         leaf sub-request-id {
 *             type string;
 *         }
 *         container flags {
 *             leaf mode {
 *                 type enumeration;
 *             }
 *             leaf force {
 *                 type enumeration;
 *             }
 *             leaf ttl {
 *                 type uint16;
 *             }
 *         }
 *     }
 *     container status {
 *         leaf code {
 *             type uint16;
 *         }
 *         leaf message {
 *             type string;
 *         }
 *     }
 *     leaf locked {
 *         type enumeration;
 *     }
 *     uses status;
 *     uses common-header;
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/check-lock/output</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CheckLockOutputBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CheckLockOutputBuilder
 *
 */
public interface CheckLockOutput
    extends
    Status,
    CommonHeader,
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CheckLockOutput>
{


    public enum Locked {
        TRUE(0, "TRUE"),
        
        FALSE(1, "FALSE")
        ;
    
        private static final java.util.Map<java.lang.Integer, Locked> VALUE_MAP;
    
        static {
            final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, Locked> b = com.google.common.collect.ImmutableMap.builder();
            for (Locked enumItem : Locked.values()) {
                b.put(enumItem.value, enumItem);
            }
    
            VALUE_MAP = b.build();
        }
    
        private final java.lang.String name;
        private final int value;
    
        private Locked(int value, java.lang.String name) {
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
         * @return corresponding Locked item
         */
        public static Locked forValue(int valueArg) {
            return VALUE_MAP.get(valueArg);
        }
    }

    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "output").intern();

    /**
     * TRUE/FALSE - returns TRUE when the given VNF was locked, otherwise returns FALSE
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CheckLockOutput.Locked</code> <code>locked</code>, or <code>null</code> if not present
     */
    Locked getLocked();

}

