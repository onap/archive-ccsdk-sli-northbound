package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.query.output;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.QueryOutput;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.VmState;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.VmStatus;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * list query-results {
 *     key     leaf vserver-id {
 *         type string;
 *     }
 *     leaf vm-state {
 *         type vm-state;
 *     }
 *     leaf vm-status {
 *         type vm-status;
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/query/output/query-results</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.query.output.QueryResultsBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.query.output.QueryResultsBuilder
 *
 *
 */
public interface QueryResults
    extends
    ChildOf<QueryOutput>,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.query.output.QueryResults>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "query-results").intern();

    /**
     * Identifier of a VM
     *
     *
     *
     * @return <code>java.lang.String</code> <code>vserverId</code>, or <code>null</code> if not present
     */
    java.lang.String getVserverId();
    
    /**
     * The state of the VM
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.VmState</code> <code>vmState</code>, or <code>null</code> if not present
     */
    VmState getVmState();
    
    /**
     * the status of the VM
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.VmStatus</code> <code>vmStatus</code>, or <code>null</code> if not present
     */
    VmStatus getVmStatus();

}

