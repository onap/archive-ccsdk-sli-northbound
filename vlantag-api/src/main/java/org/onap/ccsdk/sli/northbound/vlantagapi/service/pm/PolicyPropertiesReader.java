package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.onap.ccsdk.sli.adaptors.util.str.StrUtil;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.exception.VlantagApiException;
import org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model.PolicyProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyPropertiesReader {
    private static final Logger log = LoggerFactory.getLogger(PolicyPropertiesReader.class);

    private static final String SDNC_ROOT_DIR = "SDNC_CONFIG_DIR";
    private static final String POLICYMGR_PROPFILE = "policymanager.properties";
    private static final String POLICYMGR_PROPFILE_PATH = "/opt/sdnc/data/properties";

    private static final String POLICYMGR_URL_PN = "intf.pdp.policyengine.url";
    private static final String POLICYMGR_USERNAME_PN = "intf.pdp.policyengine.url";
    private static final String POLICYMGR_PASS_PN = "intf.pdp.policyengine.url";
    private static final String POLICYMGR_AUTHORIZATION_PN = "intf.pdp.policyengine.http.headers.authorization";
    private static final String POLICYMGR_CLIENTAUTH_PN = "intf.pdp.policyengine.http.headers.clientauth";
    private static final String POLICYMGR_ENVIRONMENT_PN = "intf.pdp.policyengine.http.headers.environment";

    public PolicyProperty getPolicyProperties() throws VlantagApiException {
        Properties props = new Properties();
        PolicyProperty policyProps;
        String propPath = System.getenv(SDNC_ROOT_DIR);

        if (propPath == null)
            propPath = POLICYMGR_PROPFILE_PATH;

        String propFileStr = propPath + "/" + POLICYMGR_PROPFILE;

        File propFile = new File(propFileStr);

        try {
            if (!propFile.exists()) {

                InputStream in = PolicyPropertiesReader.class.getResourceAsStream("/" + POLICYMGR_PROPFILE);
                if (in == null)
                    throw new VlantagApiException("Missing configuration properties file : " + propFileStr);
                else
                    props.load(in);
            } else
                props.load(new FileInputStream(propFileStr));

            policyProps = new PolicyProperty();
            if (props.getProperty(POLICYMGR_URL_PN) != null)
                policyProps.setUrl(props.getProperty(POLICYMGR_URL_PN));

            if (props.getProperty(POLICYMGR_USERNAME_PN) != null)
                policyProps.setUsername(props.getProperty(POLICYMGR_USERNAME_PN));

            if (props.getProperty(POLICYMGR_PASS_PN) != null)
                policyProps.setPassword(props.getProperty(POLICYMGR_PASS_PN));

            if (props.getProperty(POLICYMGR_CLIENTAUTH_PN) != null)
                policyProps.setClientAuth(props.getProperty(POLICYMGR_CLIENTAUTH_PN));

            if (props.getProperty(POLICYMGR_AUTHORIZATION_PN) != null)
                policyProps.setAuthorization(props.getProperty(POLICYMGR_AUTHORIZATION_PN));

            if (props.getProperty(POLICYMGR_ENVIRONMENT_PN) != null)
                policyProps.setEnvironment(props.getProperty(POLICYMGR_ENVIRONMENT_PN));

            StrUtil.info(log, policyProps);
        } catch (Exception e) {
            throw new VlantagApiException("Could not load properties at URL " + propFileStr, e);
        }

        return policyProps;
    }

}
