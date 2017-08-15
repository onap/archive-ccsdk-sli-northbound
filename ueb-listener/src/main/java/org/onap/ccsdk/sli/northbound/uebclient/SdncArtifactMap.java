/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 * 			reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.ccsdk.sli.northbound.uebclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdncArtifactMap {

	private static final Logger LOG = LoggerFactory
			.getLogger(SdncArtifactMap.class);

	public class SdncArtifactType {
		private String tag;
		private String artifactType;
		private String rpc;
		private int pass;

		public String getTag() {
			return tag;
		}
		public String getArtifactType() {
			return artifactType;
		}
		public String getRpc() {
			return rpc;
		}

		public int getPass() {
			return pass;
		}

        public String getRpcUrl(String base) {
			return(base+rpc);
        }

		private SdncArtifactType(String tag, String rpc, String pass) {
			this.tag = tag;
			this.rpc = rpc;
			try {
				this.pass = Integer.parseInt(pass);
			} catch (Exception e) {
				LOG.error("Invalid pass value for artifact map entry ("+tag+","+rpc+","+pass+")");
			}
		}
	}



	private Map<String, SdncArtifactType> mapItems = new HashMap<String, SdncArtifactType>();

	private int NumPasses = 1;

	public int getNumPasses() {
		return NumPasses;
	}

	public void load(String fileName) {

		File mapFile = new File(fileName);

		if (mapFile.exists() && mapFile.canRead()) {

			BufferedReader rdr = null;
			try {

				rdr = new BufferedReader(new FileReader(mapFile));

				for (String ln ; (ln = rdr.readLine()) != null ; ) {
					String[] lnFields = ln.split(",");
					if (lnFields.length == 3) {
						SdncArtifactType entry = new SdncArtifactType(lnFields[0], lnFields[1], lnFields[2]);
						mapItems.put(entry.getTag(), entry);
						if (entry.getPass() + 1 > NumPasses ) {
							NumPasses = entry.getPass() + 1;
						}
					}
				}


			} catch (Exception e) {
				LOG.error("Caught exception reading artifact map", e);
				return;
			} finally {
				if (rdr != null) {
					try {
						rdr.close();
					} catch (IOException e) {

					}
				}
			}
		}
	}

	public SdncArtifactType getMapping(String tag) {
		if (mapItems.containsKey(tag)) {
			return(mapItems.get(tag));
		} else {
			return(null);
		}
	}

	public static SdncArtifactMap getInstance() {
		return new SdncArtifactMap();
	}

}
