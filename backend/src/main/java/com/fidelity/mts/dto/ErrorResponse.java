package com.fidelity.mts.dto;

public record ErrorResponse(
		String errorCode,
		String message) {
 
	public String errorCode() {
		return errorCode;
	}
 
	public String message() {
		return message;
	}
 
}