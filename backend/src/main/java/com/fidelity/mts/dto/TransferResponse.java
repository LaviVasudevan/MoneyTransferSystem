package com.fidelity.mts.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fidelity.mts.enums.TransactionStatus;

public record TransferResponse(
		UUID transactionId,
		TransactionStatus status,
		String message,
		Long debitedFrom,
		Long creditedTo,
		BigDecimal amount
		) {
 
	public UUID transactionId() {
		return transactionId;
	}
 
	public TransactionStatus status() {
		return status;
	}
 
	public String message() {
		return message;
	}
 
	public Long debitedFrom() {
		return debitedFrom;
	}
 
	public Long creditedTo() {
		return creditedTo;
	}
 
	public BigDecimal amount() {
		return amount;
	}

 
}
 