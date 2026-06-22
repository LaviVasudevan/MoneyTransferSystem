package com.fidelity.mts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public class RewardHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private Long accountId;

	@Column(nullable = false)
	private UUID transactionId;

	@Column(nullable = false)
	private Integer pointsEarned;

	@Column(nullable = false, updatable = false)
	private Instant createdOn;

	public RewardHistory() {
		super();
	}

	public RewardHistory(Long accountId, UUID transactionId, Integer pointsEarned) {
		super();
		this.accountId = accountId;
		this.transactionId = transactionId;
		this.pointsEarned = pointsEarned;
		this.createdOn = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(Integer pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}
}
