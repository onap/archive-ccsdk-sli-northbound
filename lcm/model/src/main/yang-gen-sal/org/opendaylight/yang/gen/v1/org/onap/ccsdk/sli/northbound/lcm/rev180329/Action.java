package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;

public enum Action {
    Restart(0, "Restart"),
    
    Rebuild(1, "Rebuild"),
    
    Migrate(2, "Migrate"),
    
    Evacuate(3, "Evacuate"),
    
    Snapshot(4, "Snapshot"),
    
    Rollback(5, "Rollback"),
    
    Sync(6, "Sync"),
    
    Audit(7, "Audit"),
    
    Stop(8, "Stop"),
    
    Start(9, "Start"),
    
    Terminate(10, "Terminate"),
    
    SoftwareUpload(11, "SoftwareUpload"),
    
    HealthCheck(12, "HealthCheck"),
    
    LiveUpgrade(13, "LiveUpgrade"),
    
    Lock(14, "Lock"),
    
    Unlock(15, "Unlock"),
    
    Test(16, "Test"),
    
    CheckLock(17, "CheckLock"),
    
    Configure(18, "Configure"),
    
    ConfigModify(19, "ConfigModify"),
    
    ConfigScaleOut(20, "ConfigScaleOut"),
    
    ConfigRestore(21, "ConfigRestore"),
    
    ConfigBackup(22, "ConfigBackup"),
    
    ConfigBackupDelete(23, "ConfigBackupDelete"),
    
    ConfigExport(24, "ConfigExport"),
    
    StopApplication(25, "StopApplication"),
    
    StartApplication(26, "StartApplication"),
    
    QuiesceTraffic(27, "QuiesceTraffic"),
    
    ResumeTraffic(28, "ResumeTraffic"),
    
    UpgradePreCheck(29, "UpgradePreCheck"),
    
    UpgradeSoftware(30, "UpgradeSoftware"),
    
    UpgradePostCheck(31, "UpgradePostCheck"),
    
    UpgradeBackup(32, "UpgradeBackup"),
    
    UpgradeBackout(33, "UpgradeBackout"),
    
    ActionStatus(34, "ActionStatus"),
    
    Query(35, "Query"),
    
    Reboot(36, "Reboot"),
    
    AttachVolume(37, "AttachVolume"),
    
    DetachVolume(38, "DetachVolume")
    ;

    private static final java.util.Map<java.lang.Integer, Action> VALUE_MAP;

    static {
        final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, Action> b = com.google.common.collect.ImmutableMap.builder();
        for (Action enumItem : Action.values()) {
            b.put(enumItem.value, enumItem);
        }

        VALUE_MAP = b.build();
    }

    private final java.lang.String name;
    private final int value;

    private Action(int value, java.lang.String name) {
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
     * @return corresponding Action item
     */
    public static Action forValue(int valueArg) {
        return VALUE_MAP.get(valueArg);
    }
}
