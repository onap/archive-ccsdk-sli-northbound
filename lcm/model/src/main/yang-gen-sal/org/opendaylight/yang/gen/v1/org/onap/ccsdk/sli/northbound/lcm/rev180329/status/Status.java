package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * The specific response codes are to be aligned with SDC reference doc (main 
 * removed to avoid duplication and digression from main table). See SDC and ECOMP 
 * Distribution Consumer Interface Agreement
 *
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * container status {
 *     leaf code {
 *         type uint16;
 *     }
 *     leaf message {
 *         type string;
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/status/status</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.StatusBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.StatusBuilder
 *
 */
public interface Status
    extends
    ChildOf<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.Status>,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.status.Status>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "status").intern();

    /**
     * Response code
     *
     *
     *
     * @return <code>java.lang.Integer</code> <code>code</code>, or <code>null</code> if not present
     */
    java.lang.Integer getCode();
    
    /**
     * Response message
     *
     *
     *
     * @return <code>java.lang.String</code> <code>message</code>, or <code>null</code> if not present
     */
    java.lang.String getMessage();

}

