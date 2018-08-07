package org.onap.ccsdk.sli.northbound.vlantagapi.service.exception;

public class VlantagApiException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public VlantagApiException() {
        super();
    }

    public VlantagApiException(String message) {
        super(message);
    }

    public VlantagApiException(String message, Throwable t) {
        super(message, t);
    }

}
