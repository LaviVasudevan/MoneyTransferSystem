package com.fidelity.mts.dto;

import java.math.BigDecimal;

public record RedeemResponse(
		Long accountId,
		Integer pointsRedeemed,
		BigDecimal amountCredited,
		BigDecimal newBalance,
		Integer remainingPoints
		) {
}
