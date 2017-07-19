/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 ONAP Intellectual Property. All rights
 * 						reserved.
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

package org.openecomp.sdnc.vnftools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openecomp.sdnc.sli.SvcLogicContext;
import org.openecomp.sdnc.sli.SvcLogicException;
import org.openecomp.sdnc.sli.SvcLogicJavaPlugin;
import org.openecomp.sdnc.sli.SliPluginUtils.SliPluginUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VnfTools implements SvcLogicJavaPlugin {
	// ========== FIELDS ==========

	private static final Logger LOG = LoggerFactory.getLogger(VnfTools.class);

	// ========== CONSTRUCTORS ==========

	public VnfTools(Properties props) {
		if (props != null) {
			LOG.debug("props is not null.");
		}
	}


	public void checkIfActivateReady( Map<String, String> parameters, SvcLogicContext ctx ) throws SvcLogicException {
		LOG.debug("Checking if enough data is available to send the NCS Activate request...");

		SliPluginUtils.checkParameters(parameters, new String[]{"return-key"}, LOG);
		final String returnKey = parameters.get("return-key");
		ctx.setAttribute(returnKey, "true");

	}

	/**
	 * DG node performs a java String.contains(String) and writes true or false
	 * to a key in context memory.
	 * @param parameters Hashmap in context memory must contain the following:
	 * <table border='1'>
	 * <thead>
	 * 	<th>Key</th>
	 * 	<th>Description</th>
	 * </thead>
	 * <tbody>
	 * 	<tr>
	 * 		<td>string_to_search</td>
	 * 		<td>String to perform java String.contains(String) on</td>
	 * 	</tr>
	 *  <tr>
	 * 		<td>string_to_find</td>
	 * 		<td>String to find in the string_to_search</td>
	 * 	</tr>
	 *  <tr>
	 * 		<td>result_ctx_string</td>
	 * 		<td>Context memory key to write the result ("true" or "false") to</td>
	 * 	</tr>
	 * </tbody>
	 * </table>
	 * @param ctx Reference to context memory
	 * @throws SvcLogicException
	 */
	public void stringContains( Map<String, String> parameters, SvcLogicContext ctx ) throws SvcLogicException {
		SliPluginUtils.checkParameters(parameters, new String[]{"string_to_search","string_to_find","result_ctx_string"}, LOG);
		ctx.setAttribute(parameters.get("result_ctx_string"), Boolean.toString(parameters.get("string_to_search").contains(parameters.get("string_to_find"))));
	}


	public void generateName( Map<String, String> parameters, SvcLogicContext ctx ) throws SvcLogicException {
		LOG.debug("generateName");

		SliPluginUtils.checkParameters(parameters, new String[]{"base","suffix","return-path"}, LOG);

		String base = parameters.get("base");
		ctx.setAttribute( parameters.get("return-path"), base.substring(0, base.length() - 4) + parameters.get("suffix") + base.substring(base.length() - 2) );
	}


	private boolean matches(String str1, String str2) {
		if (str1 == null) {
			if (str2 == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (str2 == null) {
				return false;
			} else {
				return str1.equals(str2);
			}
		}
	}

	private void setIfNotNull(String property, String value, SvcLogicContext ctx) {
		if (value != null) {
			LOG.debug("Setting " + property + " to " + value);
			ctx.setAttribute(property, value);
		}
	}

	/*
	 * Moves an array element from one index to another
	 */
	private void copyArrayEntry(String srcRoot, String destRoot, SvcLogicContext ctx) {
		LOG.debug("copyArrayEntry called: srcRoot=" + srcRoot + ", destRoot=" + destRoot);

		// Record all of the source keys
		List<String> keysToMove = new ArrayList<String>();
		for (String key : ctx.getAttributeKeySet()) {
			if (key.startsWith(srcRoot)) {
				keysToMove.add(key);
			}
		}

		// Now loop through and copy those keys to the destination, and then delete the source
		for (String key : keysToMove) {
			String suffix = key.substring(srcRoot.length());
			LOG.debug("Move " + key + " to " + destRoot + suffix);
			ctx.setAttribute(destRoot + suffix, ctx.getAttribute(key));
			ctx.setAttribute(key, null);
		}

	}

	public void printContext(Map<String, String> parameters, SvcLogicContext ctx) throws SvcLogicException {
		if (parameters == null) {
			throw new SvcLogicException("no parameters passed");
		}

		String fileName = parameters.get("filename");

		if ((fileName == null) || (fileName.length() == 0)) {
			throw new SvcLogicException("printContext requires 'filename' parameter");
		}

		PrintStream pstr = null;

		try {
			pstr = new PrintStream(new FileOutputStream(new File(fileName), true));
		} catch (Exception e) {
			throw new SvcLogicException("Cannot open file " + fileName, e);
		}
	        pstr.println("#######################################");
		for (String attr : ctx.getAttributeKeySet()) {
			pstr.println(attr + " = " + ctx.getAttribute(attr));
		}
		pstr.flush();
		pstr.close();
	}

	static int getArrayLength( SvcLogicContext ctx, String key ) {
		try {
			return Integer.parseInt(ctx.getAttribute(key));
		} catch( NumberFormatException e ) {}

		return 0;
	}

	static int getArrayLength( SvcLogicContext ctx, String key, String debug ) {
		try {
			return Integer.parseInt(ctx.getAttribute(key));
		} catch( NumberFormatException e ) {
			LOG.debug(debug);
		}

		return 0;
	}

	/**
	 * Returns true if string is null or empty.
	 * @param str
	 * @return
	 */
	private static boolean stringIsBlank( String str ) {
		return str == null || str.isEmpty();
	}

}
