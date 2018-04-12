package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;
import java.util.List;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.query.output.QueryResults;

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
 *     list query-results {
 *         key     leaf vserver-id {
 *             type string;
 *         }
 *         leaf vm-state {
 *             type vm-state;
 *         }
 *         leaf vm-status {
 *             type vm-status;
 *         }
 *     }
 *     uses status;
 *     uses common-header;
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/query/output</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutputBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutputBuilder
 *
 */
public interface QueryOutput
    extends
    Status,
    CommonHeader,
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "output").intern();

    /**
     * @return <code>java.util.List</code> <code>queryResults</code>, or <code>null</code> if not present
     */
    List<QueryResults> getQueryResults();

}

