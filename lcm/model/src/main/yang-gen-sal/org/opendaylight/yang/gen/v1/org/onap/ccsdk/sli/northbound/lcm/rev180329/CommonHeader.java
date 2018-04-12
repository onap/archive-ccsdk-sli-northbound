package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;

/**
 * A common header for all APP-C requests
 *
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * grouping common-header {
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
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/common-header</i>
 *
 */
public interface CommonHeader
    extends
    DataObject
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "common-header").intern();

    /**
     * A common header for all APP-C requests
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader</code> <code>commonHeader</code>, or <code>null</code> if not present
     */
    org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader getCommonHeader();

}

