package org.melato.gpx;

public class KeyValue {
	private String key;
	private String value;

	public static final KeyValue[] EMPTY_ARRAY = new KeyValue[0];

	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
