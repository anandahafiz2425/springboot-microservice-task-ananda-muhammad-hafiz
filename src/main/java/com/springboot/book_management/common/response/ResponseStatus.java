package com.springboot.book_management.common.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseStatus {
        SUCCESS("SUCCESS"), ERROR("ERROR");

	private String val;

	ResponseStatus(String val) {
		this.val = val;
	}

	@JsonValue
	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		return val;
	}
}
