package com.fidelity.mts.servcie;

import com.fidelity.mts.dto.RedeemResponse;
import com.fidelity.mts.dto.RewardSummaryResponse;
import com.fidelity.mts.entity.TransactionLog;

public interface RewardService {

	/**
	 * Evaluates the given transaction for reward eligibility and, if eligible,
	 * grants reward points to the sender and records the grant in reward history.
	 *
	 * Eligibility:
	 *  - Transaction status is SUCCESS
	 *  - Amount is greater than Rs 100
	 *  - Sender and receiver accounts are different
	 *
	 * Points: 1 point for every Rs 100 transferred (floor division).
	 *
	 * @return the number of reward points granted (0 if not eligible).
	 */
	int evaluateAndGrantReward(TransactionLog transactionLog);

	RewardSummaryResponse getRewardSummary(Long accountId);

	/**
	 * Redeems the given number of points for the account, crediting the
	 * equivalent rupee value (100 points = Rs 5) to the account's balance.
	 * In-app only: redeemed value can only ever be credited as balance,
	 * never withdrawn directly.
	 *
	 * @throws com.fidelity.mts.exception.InsufficientRewardPointsException
	 *         if the account does not have enough available (unredeemed) points.
	 */
	RedeemResponse redeemPoints(Long accountId, Integer points);
}
