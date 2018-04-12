package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;

/**
 * The specific response codes are to be aligned with SDC reference doc (main 
 * removed to avoid duplication and digression from main table). See SDC and ECOMP 
 * Distribution Consumer Interface Agreement
 *
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * grouping status {
 *     container status {
 *         leaf code {
 *             type uint16;
 *         }
 *         leaf message {
 *             type string;
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/status</i>
 *
 */
public interface Status
    extends
    DataObject
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "status").intern();

    /**
     * The specific response codes are to be aligned with SDC reference doc (main table
     * removed to avoid duplication and digression from main table). See SDC and ECOMP 
     * Distribution Consumer Interface Agreement
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.Status</code> <code>status</code>, or <code>null</code> if not present
     */
    org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.Status getStatus();

}

