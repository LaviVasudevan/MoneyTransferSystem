package com.fidelity.mts.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.fidelity.mts.enums.AccountStatus;

public record AccountResponse(
		  Long id,
		  String holderName,
		  BigDecimal balance,
		  AccountStatus status,
		  Integer version,
		  Instant lastUpdated) {
		 
			public Long id() {
				return id;
			}
		 
			public String holderName() {
				return holderName;
			}
		 
			public BigDecimal balance() {
				return balance;
			}
		 
			public AccountStatus status() {
				return status;
			}
		 
			public Integer version() {
				return version;
			}
		 
			public Instant lastUpdated() {
				return lastUpdated;
			}
		}