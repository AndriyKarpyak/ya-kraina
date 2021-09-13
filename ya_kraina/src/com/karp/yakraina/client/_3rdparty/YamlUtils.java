package com.karp.yakraina.client._3rdparty;

import com.google.gwt.core.client.JavaScriptObject;

public class YamlUtils {

	private YamlUtils() {
	}

	static void throwIllegalArgumentException(final String message, final String data) {
		throw new IllegalArgumentException(message + "\n" + data);
	}

	/**
	 * Converts a value to YAML string.
	 */
	public static native String stringify(JavaScriptObject object) /*-{
		return $wnd.YAML.stringify(object);
	}-*/;
	  
	/**
	 * Evaluates a YAML expression safely. The pay load must evaluate to an Object or
	 * an Array (not a primitive or a String).
	 * 
	 * @param <T> The type of JavaScriptObject that should be returned
	 * @param yaml The source YAML text
	 * @return The evaluated object
	 * 
	 * @throws IllegalArgumentException if the input is not valid YAML
	 */
	public static native <T extends JavaScriptObject> T safeEval(String input) /*-{
		try {
		    return $wnd.YAML.parse(input);
		} catch (e) {
			return @YamlUtils::throwIllegalArgumentException(*)("Error parsing YAML: " + e, input);
		}
	}-*/;
}
