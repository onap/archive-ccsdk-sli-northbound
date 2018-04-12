package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;

public enum VmState {
    Active(0, "active"),
    
    Standby(1, "standby"),
    
    Inactive(2, "inactive"),
    
    Unknown(3, "unknown")
    ;

    private static final java.util.Map<java.lang.Integer, VmState> VALUE_MAP;

    static {
        final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, VmState> b = com.google.common.collect.ImmutableMap.builder();
        for (VmState enumItem : VmState.values()) {
            b.put(enumItem.value, enumItem);
        }

        VALUE_MAP = b.build();
    }

    private final java.lang.String name;
    private final int value;

    private VmState(int value, java.lang.String name) {
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
     * @return corresponding VmState item
     */
    public static VmState forValue(int valueArg) {
        return VALUE_MAP.get(valueArg);
    }
}
