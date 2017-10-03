package org.onap.ccsdk.sli.northbound.uebclient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openecomp.sdc.api.IDistributionClient;
import org.openecomp.sdc.api.notification.INotificationData;

import static org.mockito.Mockito.mock;

public class TestSdncUebCallback {
	SdncUebConfiguration config;

	@Before
	public void setUp() throws Exception {
		config = new SdncUebConfiguration("src/test/resources");
	}

	@Test
	public void test() {

		IDistributionClient iDistClient = mock(IDistributionClient.class);
		SdncUebCallback cb = new SdncUebCallback(iDistClient, config);

		INotificationData iData = mock(INotificationData.class);
		cb.activateCallback(iData);
	}

}
