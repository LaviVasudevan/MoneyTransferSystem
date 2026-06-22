package com.fidelity.mts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class RewardRedemption {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private Long accountId;

	@Column(nullable = false)
	private Integer pointsRedeemed;

	@Column(nullable = false)
	private BigDecimal amountCredited;

	@Column(nullable = false, updatable = false)
	private Instant redeemedOn;

	public RewardRedemption() {
		super();
	}

	public RewardRedemption(Long accountId, Integer pointsRedeemed, BigDecimal amountCredited) {
		super();
		this.accountId = accountId;
		this.pointsRedeemed = pointsRedeemed;
		this.amountCredited = amountCredited;
		this.redeemedOn = Instant.now();
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

	public Integer getPointsRedeemed() {
		return pointsRedeemed;
	}

	public void setPointsRedeemed(Integer pointsRedeemed) {
		this.pointsRedeemed = pointsRedeemed;
	}

	public BigDecimal getAmountCredited() {
		return amountCredited;
	}

	public void setAmountCredited(BigDecimal amountCredited) {
		this.amountCredited = amountCredited;
	}

	public Instant getRedeemedOn() {
		return redeemedOn;
	}

	public void setRedeemedOn(Instant redeemedOn) {
		this.redeemedOn = redeemedOn;
	}
}
