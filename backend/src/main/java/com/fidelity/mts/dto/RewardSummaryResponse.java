package com.fidelity.mts.dto;

import java.util.List;

import com.fidelity.mts.entity.RewardHistory;
import com.fidelity.mts.entity.RewardRedemption;

public record RewardSummaryResponse(
		Long accountId,
		Integer totalPointsEarned,
		Integer totalPointsRedeemed,
		Integer availablePoints,
		List<RewardHistory> history,
		List<RewardRedemption> redemptions
		) {
}
