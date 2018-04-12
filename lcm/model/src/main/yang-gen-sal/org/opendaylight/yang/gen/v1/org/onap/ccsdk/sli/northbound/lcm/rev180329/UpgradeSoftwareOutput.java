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
 *     uses status;
 *     uses common-header;
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/upgrade-software/output</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.UpgradeSoftwareOutputBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.UpgradeSoftwareOutputBuilder
 *
 */
public interface UpgradeSoftwareOutput
    extends
    Status,
    CommonHeader,
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.UpgradeSoftwareOutput>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "output").intern();


}

