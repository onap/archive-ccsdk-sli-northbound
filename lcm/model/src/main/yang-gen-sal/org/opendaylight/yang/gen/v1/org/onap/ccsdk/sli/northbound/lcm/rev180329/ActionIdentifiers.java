package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;

/**
 * A block containing the action arguments. These are used to specify the object 
 * upon which APP-C LCM command is to operate
 *
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * grouping action-identifiers {
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
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/action-identifiers</i>
 *
 */
public interface ActionIdentifiers
    extends
    DataObject
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "action-identifiers").intern();

    /**
     * A block containing the action arguments. These are used to specify the object 
     * upon which APP-C LCM command is to operate
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers</code> <code>actionIdentifiers</code>, or <code>null</code> if not present
     */
    org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers getActionIdentifiers();

}

