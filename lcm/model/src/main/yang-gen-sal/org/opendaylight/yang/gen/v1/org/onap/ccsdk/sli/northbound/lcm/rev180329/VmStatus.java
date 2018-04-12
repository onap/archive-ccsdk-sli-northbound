package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;

public enum VmStatus {
    Healthy(0, "healthy"),
    
    Unhealthy(1, "unhealthy"),
    
    Unknown(2, "unknown")
    ;

    private static final java.util.Map<java.lang.Integer, VmStatus> VALUE_MAP;

    static {
        final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, VmStatus> b = com.google.common.collect.ImmutableMap.builder();
        for (VmStatus enumItem : VmStatus.values()) {
            b.put(enumItem.value, enumItem);
        }

        VALUE_MAP = b.build();
    }

    private final java.lang.String name;
    private final int value;

    private VmStatus(int value, java.lang.String name) {
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
     * @return corresponding VmStatus item
     */
    public static VmStatus forValue(int valueArg) {
        return VALUE_MAP.get(valueArg);
    }
}
