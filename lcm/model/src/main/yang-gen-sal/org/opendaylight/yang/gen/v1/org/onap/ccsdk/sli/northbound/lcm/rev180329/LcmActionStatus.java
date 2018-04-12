package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;

public enum LcmActionStatus {
    INPROGRESS(0, "IN_PROGRESS"),
    
    SUCCESSFUL(1, "SUCCESSFUL"),
    
    FAILED(2, "FAILED"),
    
    NOTFOUND(3, "NOT_FOUND"),
    
    ABORTED(4, "ABORTED"),
    
    MULTIPLEREQUESTSFOUND(5, "MULTIPLE_REQUESTS_FOUND")
    ;

    private static final java.util.Map<java.lang.Integer, LcmActionStatus> VALUE_MAP;

    static {
        final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, LcmActionStatus> b = com.google.common.collect.ImmutableMap.builder();
        for (LcmActionStatus enumItem : LcmActionStatus.values()) {
            b.put(enumItem.value, enumItem);
        }

        VALUE_MAP = b.build();
    }

    private final java.lang.String name;
    private final int value;

    private LcmActionStatus(int value, java.lang.String name) {
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
     * @return corresponding LcmActionStatus item
     */
    public static LcmActionStatus forValue(int valueArg) {
        return VALUE_MAP.get(valueArg);
    }
}
