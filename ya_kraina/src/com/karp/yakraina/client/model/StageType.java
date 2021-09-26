package com.karp.yakraina.client.model;

public enum StageType {

	DECISION("Decision"),
	OPTIONS("OptionsList"),
	YES_NO_OPTIONS("YesNoOptions"),
	INFORMATION("Information"),
	DISCARD("Discard"),
	FINAL("Final"),
	UNDEFINED("");
	
	private String typeName;

	StageType(String typeName) {
		this.typeName = typeName;
	}

	public static StageType of(String typeName) {

		for (StageType t : values())
			if (t.typeName.equalsIgnoreCase(typeName))
				return t;

		return UNDEFINED;
	}

}
