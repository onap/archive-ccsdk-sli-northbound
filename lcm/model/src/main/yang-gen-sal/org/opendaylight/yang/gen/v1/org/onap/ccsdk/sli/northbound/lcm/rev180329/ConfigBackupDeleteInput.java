package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * container input {
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
 *     leaf action {
 *         type action;
 *     }
 *     container action-identifiers {
 *         leaf service-instance-id {
 *             type string;
 *         }
 *         leaf vnf-id {
 *             type string;
 *         }
 *         leaf vf-module-id {
 *             type string;
 *         }
 *         leaf vnfc-name {
 *             type string;
 *         }
 *         leaf vserver-id {
 *             type string;
 *         }
 *     }
 *     uses action-identifiers;
 *     uses common-header;
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/config-backup-delete/input</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInputBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInputBuilder
 *
 */
public interface ConfigBackupDeleteInput
    extends
    ActionIdentifiers,
    CommonHeader,
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ConfigBackupDeleteInput>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "input").intern();

    /**
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Action</code> <code>action</code>, or <code>null</code> if not present
     */
    Action getAction();

}

