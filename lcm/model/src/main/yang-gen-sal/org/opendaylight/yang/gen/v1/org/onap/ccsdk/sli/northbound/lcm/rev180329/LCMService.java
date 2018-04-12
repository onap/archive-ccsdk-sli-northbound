package org.opendaylight.yang.gen.v1.org.onap.ccsdk.sli.northbound.lcm.rev180329;
import org.opendaylight.yangtools.yang.binding.RpcService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import java.util.concurrent.Future;

/**
 * Interface for implementing the following YANG RPCs defined in module <b>LCM</b>
 * <pre>
 * rpc check-lock {
 *     "An operation to check VNF lock status";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf locked {
 *             type enumeration;
 *         }
 *     }
 * }
 * rpc reboot {
 *     "An operation to reboot a specified virtual machine (VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc upgrade-backup {
 *     "An operation to do full backup of the VNF data prior to an upgrade.";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc rollback {
 *     "An operation to rollback to particular snapshot of a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *         leaf identity-url {
 *             type string;
 *         }
 *         leaf snapshot-id {
 *             type string;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc sync {
 *     "An operation to sync the configurations of a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc query {
 *     "An operation to query the status of a targe VNF.
 *                          Returns information on each VM, including state (active or standby)
 *                          and status (healthy or unhealthy)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         list query-results {
 *             key     leaf vserver-id {
 *                 type string;
 *             }
 *             leaf vm-state {
 *                 type vm-state;
 *             }
 *             leaf vm-status {
 *                 type vm-status;
 *             }
 *         }
 *     }
 * }
 * rpc config-export {
 *     "An operation to Export configurations of a virtual network function
 *                     (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc stop-application {
 *     "An operation to Stop Application traffic to a virtual network function";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc software-upload {
 *     "An operation to upload a new version of vSCP image to vSCP for updating it";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc resume-traffic {
 *     "An operation to resume traffic gracefully on the VF.
 *                          It resumes traffic gracefully without stopping the application";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc configure {
 *     "An operation to configure the configurations of a virtual network
 *                     function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc action-status {
 *     "An operation to get the current state of the previously submitted LCM request";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc upgrade-pre-check {
 *     "An operation to check that the VNF has the correct software version needed for a software upgrade.";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc live-upgrade {
 *     "An operation to perform upgrade of vSCP";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc config-modify {
 *     "Use the ModifyConfig command when a full configuration cycle is either not required
 *                          or is considered too costly. The ModifyConfig LCM action affects only a subset of the
 *                          total configuration data of a VNF. The set of configuration parameters to be affected
 *                          is a subset of the total configuration data of the target VNF type. The payload Stop
 *                          Application must contain the configuration parameters to be modified and their values.
 *                          A successful modify returns a success response. A failed modify returns a failure
 *                          response and the specific failure messages in the response payload Stop Application";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc restart {
 *     "An operation to restart a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc health-check {
 *     "An operation to perform health check of vSCP prior its upgrading";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc lock {
 *     "An operation to perform VNF lock operation";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc terminate {
 *     "An operation to terminate the configurations of a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc attach-volume {
 *     "An operation to attach a cinder volume to a VM";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc migrate {
 *     "An operation to migrate a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc quiesce-traffic {
 *     "An operation to stop traffic gracefully on the VF.
 *                          It stops traffic gracefully without stopping the application";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc config-restore {
 *     "An operation to restore the configurations of a virtual network
 *                     function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc upgrade-backout {
 *     "An operation does a backout after an UpgradeSoftware is completed (either successfully or unsuccessfully).";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc evacuate {
 *     "An operation to evacuate a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc unlock {
 *     "An operation to perform VNF unlock operation";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc config-backup-delete {
 *     "An operation to Delete backup configurations of a virtual network
 *                     function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc upgrade-software {
 *     "An operation to upgrade the target VNF to a new version and expected that the VNF is in a quiesced status .";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc stop {
 *     "An operation to stop the configurations of a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc detach-volume {
 *     "An operation to detach a cinder volume from a VM";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc config-scale-out {
 *     "An operation to Modify the configuration or other action to support
 *                 a ConfigScaleOut of a VNF.";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc upgrade-post-check {
 *     "An operation to check the VNF upgrade has been successful completed and all processes are running properly.";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc test {
 *     "An operation to test the configurations of a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc start-application {
 *     "An operation to perform VNF Start Application operation";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc config-backup {
 *     "An operation to Backup configurations of a virtual network function
 *                     (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc rebuild {
 *     "An operation to rebuild a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc audit {
 *     "An operation to audit the configurations of a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 * }
 * rpc start {
 *     "An operation to start a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *     }
 * }
 * rpc snapshot {
 *     "An operation to create a snapshot of a virtual network function (or VM)";
 *     input {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         leaf action {
 *             type action;
 *         }
 *         container action-identifiers {
 *             leaf service-instance-id {
 *                 type string;
 *             }
 *             leaf vnf-id {
 *                 type string;
 *             }
 *             leaf vf-module-id {
 *                 type string;
 *             }
 *             leaf vnfc-name {
 *                 type string;
 *             }
 *             leaf vserver-id {
 *                 type string;
 *             }
 *         }
 *         leaf payload {
 *             type payload;
 *         }
 *         leaf identity-url {
 *             type string;
 *         }
 *     }
 *     
 *     output {
 *         container common-header {
 *             leaf timestamp {
 *                 type ZULU;
 *             }
 *             leaf api-ver {
 *                 type string;
 *             }
 *             leaf originator-id {
 *                 type string;
 *             }
 *             leaf request-id {
 *                 type string;
 *             }
 *             leaf sub-request-id {
 *                 type string;
 *             }
 *             container flags {
 *                 leaf mode {
 *                     type enumeration;
 *                 }
 *                 leaf force {
 *                     type enumeration;
 *                 }
 *                 leaf ttl {
 *                     type uint16;
 *                 }
 *             }
 *         }
 *         container status {
 *             leaf code {
 *                 type uint16;
 *             }
 *             leaf message {
 *                 type string;
 *             }
 *         }
 *         leaf snapshot-id {
 *             type string;
 *         }
 *     }
 * }
 * </pre>
 *
 */
public interface LCMService
    extends
    RpcService
{




    /**
     * An operation to check VNF lock status
     *
     */
    Future<RpcResult<CheckLockOutput>> checkLock(CheckLockInput input);
    
    /**
     * An operation to reboot a specified virtual machine (VM)
     *
     */
    Future<RpcResult<RebootOutput>> reboot(RebootInput input);
    
    /**
     * An operation to do full backup of the VNF data prior to an upgrade.
     *
     */
    Future<RpcResult<UpgradeBackupOutput>> upgradeBackup(UpgradeBackupInput input);
    
    /**
     * An operation to rollback to particular snapshot of a virtual network function 
     * (or VM)
     *
     */
    Future<RpcResult<RollbackOutput>> rollback(RollbackInput input);
    
    /**
     * An operation to sync the configurations of a virtual network function (or VM)
     *
     */
    Future<RpcResult<SyncOutput>> sync(SyncInput input);
    
    /**
     * An operation to query the status of a targe VNF. Returns information on each VM,
     * including state (active or standby) and status (healthy or unhealthy)
     *
     */
    Future<RpcResult<QueryOutput>> query(QueryInput input);
    
    /**
     * An operation to Export configurations of a virtual network function (or VM)
     *
     */
    Future<RpcResult<ConfigExportOutput>> configExport(ConfigExportInput input);
    
    /**
     * An operation to Stop Application traffic to a virtual network function
     *
     */
    Future<RpcResult<StopApplicationOutput>> stopApplication(StopApplicationInput input);
    
    /**
     * An operation to upload a new version of vSCP image to vSCP for updating it
     *
     */
    Future<RpcResult<SoftwareUploadOutput>> softwareUpload(SoftwareUploadInput input);
    
    /**
     * An operation to resume traffic gracefully on the VF. It resumes traffic 
     * gracefully without stopping the application
     *
     */
    Future<RpcResult<ResumeTrafficOutput>> resumeTraffic(ResumeTrafficInput input);
    
    /**
     * An operation to configure the configurations of a virtual network function (or 
     * VM)
     *
     */
    Future<RpcResult<ConfigureOutput>> configure(ConfigureInput input);
    
    /**
     * An operation to get the current state of the previously submitted LCM request
     *
     */
    Future<RpcResult<ActionStatusOutput>> actionStatus(ActionStatusInput input);
    
    /**
     * An operation to check that the VNF has the correct software version needed for a
     * software upgrade.
     *
     */
    Future<RpcResult<UpgradePreCheckOutput>> upgradePreCheck(UpgradePreCheckInput input);
    
    /**
     * An operation to perform upgrade of vSCP
     *
     */
    Future<RpcResult<LiveUpgradeOutput>> liveUpgrade(LiveUpgradeInput input);
    
    /**
     * Use the ModifyConfig command when a full configuration cycle is either not 
     * required or is considered too costly. The ModifyConfig LCM action affects only a
     * subset of the total configuration data of a VNF. The set of configuration 
     * parameters to be affected is a subset of the total configuration data of the 
     * target VNF type. The payload Stop Application must contain the configuration 
     * parameters to be modified and their values. A successful modify returns a 
     * success response. A failed modify returns a failure response and the specific 
     * failure messages in the response payload Stop Application
     *
     */
    Future<RpcResult<ConfigModifyOutput>> configModify(ConfigModifyInput input);
    
    /**
     * An operation to restart a virtual network function (or VM)
     *
     */
    Future<RpcResult<RestartOutput>> restart(RestartInput input);
    
    /**
     * An operation to perform health check of vSCP prior its upgrading
     *
     */
    Future<RpcResult<HealthCheckOutput>> healthCheck(HealthCheckInput input);
    
    /**
     * An operation to perform VNF lock operation
     *
     */
    Future<RpcResult<LockOutput>> lock(LockInput input);
    
    /**
     * An operation to terminate the configurations of a virtual network function (or 
     * VM)
     *
     */
    Future<RpcResult<TerminateOutput>> terminate(TerminateInput input);
    
    /**
     * An operation to attach a cinder volume to a VM
     *
     */
    Future<RpcResult<AttachVolumeOutput>> attachVolume(AttachVolumeInput input);
    
    /**
     * An operation to migrate a virtual network function (or VM)
     *
     */
    Future<RpcResult<MigrateOutput>> migrate(MigrateInput input);
    
    /**
     * An operation to stop traffic gracefully on the VF. It stops traffic gracefully 
     * without stopping the application
     *
     */
    Future<RpcResult<QuiesceTrafficOutput>> quiesceTraffic(QuiesceTrafficInput input);
    
    /**
     * An operation to restore the configurations of a virtual network function (or VM)
     *
     */
    Future<RpcResult<ConfigRestoreOutput>> configRestore(ConfigRestoreInput input);
    
    /**
     * An operation does a backout after an UpgradeSoftware is completed (either 
     * successfully or unsuccessfully).
     *
     */
    Future<RpcResult<UpgradeBackoutOutput>> upgradeBackout(UpgradeBackoutInput input);
    
    /**
     * An operation to evacuate a virtual network function (or VM)
     *
     */
    Future<RpcResult<EvacuateOutput>> evacuate(EvacuateInput input);
    
    /**
     * An operation to perform VNF unlock operation
     *
     */
    Future<RpcResult<UnlockOutput>> unlock(UnlockInput input);
    
    /**
     * An operation to Delete backup configurations of a virtual network function (or 
     * VM)
     *
     */
    Future<RpcResult<ConfigBackupDeleteOutput>> configBackupDelete(ConfigBackupDeleteInput input);
    
    /**
     * An operation to upgrade the target VNF to a new version and expected that the 
     * VNF is in a quiesced status .
     *
     */
    Future<RpcResult<UpgradeSoftwareOutput>> upgradeSoftware(UpgradeSoftwareInput input);
    
    /**
     * An operation to stop the configurations of a virtual network function (or VM)
     *
     */
    Future<RpcResult<StopOutput>> stop(StopInput input);
    
    /**
     * An operation to detach a cinder volume from a VM
     *
     */
    Future<RpcResult<DetachVolumeOutput>> detachVolume(DetachVolumeInput input);
    
    /**
     * An operation to Modify the configuration or other action to support a 
     * ConfigScaleOut of a VNF.
     *
     */
    Future<RpcResult<ConfigScaleOutOutput>> configScaleOut(ConfigScaleOutInput input);
    
    /**
     * An operation to check the VNF upgrade has been successful completed and all 
     * processes are running properly.
     *
     */
    Future<RpcResult<UpgradePostCheckOutput>> upgradePostCheck(UpgradePostCheckInput input);
    
    /**
     * An operation to test the configurations of a virtual network function (or VM)
     *
     */
    Future<RpcResult<TestOutput>> test(TestInput input);
    
    /**
     * An operation to perform VNF Start Application operation
     *
     */
    Future<RpcResult<StartApplicationOutput>> startApplication(StartApplicationInput input);
    
    /**
     * An operation to Backup configurations of a virtual network function (or VM)
     *
     */
    Future<RpcResult<ConfigBackupOutput>> configBackup(ConfigBackupInput input);
    
    /**
     * An operation to rebuild a virtual network function (or VM)
     *
     */
    Future<RpcResult<RebuildOutput>> rebuild(RebuildInput input);
    
    /**
     * An operation to audit the configurations of a virtual network function (or VM)
     *
     */
    Future<RpcResult<AuditOutput>> audit(AuditInput input);
    
    /**
     * An operation to start a virtual network function (or VM)
     *
     */
    Future<RpcResult<StartOutput>> start(StartInput input);
    
    /**
     * An operation to create a snapshot of a virtual network function (or VM)
     *
     */
    Future<RpcResult<SnapshotOutput>> snapshot(SnapshotInput input);

}

