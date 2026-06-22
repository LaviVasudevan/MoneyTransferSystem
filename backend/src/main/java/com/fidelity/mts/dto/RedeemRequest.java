package com.fidelity.mts.dto;

public class RedeemRequest {

	private Integer points;

	public RedeemRequest() {
		super();
	}

	public RedeemRequest(Integer points) {
		super();
		this.points = points;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
}
