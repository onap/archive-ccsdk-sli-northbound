package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header.Flags;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ZULU;

/**
 * A common header for all APP-C requests
 *
 * <p>This class represents the following YANG schema fragment defined in module <b>LCM</b>
 * <pre>
 * container common-header {
 *     leaf timestamp {
 *         type ZULU;
 *     }
 *     leaf api-ver {
 *         type string;
 *     }
 *     leaf originator-id {
 *         type string;
 *     }
 *     leaf request-id {
 *         type string;
 *     }
 *     leaf sub-request-id {
 *         type string;
 *     }
 *     container flags {
 *         leaf mode {
 *             type enumeration;
 *         }
 *         leaf force {
 *             type enumeration;
 *         }
 *         leaf ttl {
 *             type uint16;
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>LCM/common-header/common-header</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeaderBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeaderBuilder
 *
 */
public interface CommonHeader
    extends
    ChildOf<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.CommonHeader>,
    Augmentable<org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.CommonHeader>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:onap:ccsdk:sli:northbound:lcm",
        "2018-03-29", "common-header").intern();

    /**
     * timestamp is in ISO 8601 timestamp format ZULU offset
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.ZULU</code> <code>timestamp</code>, or <code>null</code> if not present
     */
    ZULU getTimestamp();
    
    /**
     * api-ver is the API version identifier. A given release of APPC should support 
     * all previous versions of APPC API (correlate with general requirements)
     *
     *
     *
     * @return <code>java.lang.String</code> <code>apiVer</code>, or <code>null</code> if not present
     */
    java.lang.String getApiVer();
    
    /**
     * originator-id an identifier of the calling system which can be used addressing 
     * purposes, i.e. returning asynchronous response to the proper destination over 
     * DMaaP (especially in case of multiple consumers of APP-C APIs)
     *
     *
     *
     * @return <code>java.lang.String</code> <code>originatorId</code>, or <code>null</code> if not present
     */
    java.lang.String getOriginatorId();
    
    /**
     * UUID for the request ID. An OSS/BSS identifier for the request that caused the 
     * current action. Multiple API calls may be made with the same request-id The 
     * request-id shall be recorded throughout the operations on a single request
     *
     *
     *
     * @return <code>java.lang.String</code> <code>requestId</code>, or <code>null</code> if not present
     */
    java.lang.String getRequestId();
    
    /**
     * Uniquely identifies a specific LCM action. It is persistent over the life-cycle 
     * of a single request
     *
     *
     *
     * @return <code>java.lang.String</code> <code>subRequestId</code>, or <code>null</code> if not present
     */
    java.lang.String getSubRequestId();
    
    /**
     * Flags are generic flags that apply to any and all commands, all are optional
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329.common.header.common.header.Flags</code> <code>flags</code>, or <code>null</code> if not present
     */
    Flags getFlags();

}

