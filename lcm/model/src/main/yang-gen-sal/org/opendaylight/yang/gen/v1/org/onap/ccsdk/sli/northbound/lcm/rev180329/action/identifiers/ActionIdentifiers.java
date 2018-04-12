package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * A block containing the action arguments. These are used to specify the object 
 * upon which APP-C LCM command is to operate
 *
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * container action-identifiers {
 *     leaf service-instance-id {
 *         type string;
 *     }
 *     leaf vnf-id {
 *         type string;
 *     }
 *     leaf vf-module-id {
 *         type string;
 *     }
 *     leaf vnfc-name {
 *         type string;
 *     }
 *     leaf vserver-id {
 *         type string;
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/action-identifiers/action-identifiers</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiersBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiersBuilder
 *
 */
public interface ActionIdentifiers
    extends
    ChildOf<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ActionIdentifiers>,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.action.identifiers.ActionIdentifiers>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "action-identifiers").intern();

    /**
     * identifies a specific service the command refers to. When multiple APP-C 
     * instances are used and applied to a subset of services, this will become 
     * significant . The field is mandatory when the vnf-id is empty
     *
     *
     *
     * @return <code>java.lang.String</code> <code>serviceInstanceId</code>, or <code>null</code> if not present
     */
    java.lang.String getServiceInstanceId();
    
    /**
     * identifies the VNF to which this action is to be applied(vnf-id uniquely 
     * identifies the service-instance referred to). Note that some actions are applied
     * to multiple VNFs in the same service. When this is the case, vnf-id may be left 
     * out, but service-instance-id must appear. The field is mandatory when 
     * service-instance-id is empty
     *
     *
     *
     * @return <code>java.lang.String</code> <code>vnfId</code>, or <code>null</code> if not present
     */
    java.lang.String getVnfId();
    
    /**
     * identifies the VF module to which this action is to be applied.
     *
     *
     *
     * @return <code>java.lang.String</code> <code>vfModuleId</code>, or <code>null</code> if not present
     */
    java.lang.String getVfModuleId();
    
    /**
     * identifies the VNFC to which this action is to be applied. Some actions apply 
     * only to a component within a VNF (e.g. RESTART is sometimes applied to on VM 
     * only). In such a case, the name of the VNFC is used to search for the component 
     * within the VNF
     *
     *
     *
     * @return <code>java.lang.String</code> <code>vnfcName</code>, or <code>null</code> if not present
     */
    java.lang.String getVnfcName();
    
    /**
     * identifies a specific VM within the given service/vnf to which this action is to
     * be applied
     *
     *
     *
     * @return <code>java.lang.String</code> <code>vserverId</code>, or <code>null</code> if not present
     */
    java.lang.String getVserverId();

}

