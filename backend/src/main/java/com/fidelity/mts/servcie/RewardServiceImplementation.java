package com.fidelity.mts.servcie;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.mts.dto.RedeemResponse;
import com.fidelity.mts.dto.RewardSummaryResponse;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.RewardHistory;
import com.fidelity.mts.entity.RewardRedemption;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.enums.TransactionStatus;
import com.fidelity.mts.exception.InsufficientRewardPointsException;
import com.fidelity.mts.repo.AccountRepo;
import com.fidelity.mts.repo.RewardHistoryRepo;
import com.fidelity.mts.repo.RewardRedemptionRepo;

@Service
public class RewardServiceImplementation implements RewardService {

	private static final BigDecimal MIN_ELIGIBLE_AMOUNT = BigDecimal.valueOf(100);
	private static final BigDecimal POINT_UNIT = BigDecimal.valueOf(100);

	// Redemption rate: 100 points = Rs 5
	private static final BigDecimal REDEMPTION_POINT_UNIT = BigDecimal.valueOf(100);
	private static final BigDecimal REDEMPTION_RUPEE_VALUE = BigDecimal.valueOf(5);

	@Autowired
	RewardHistoryRepo rewardHistoryRepo;

	@Autowired
	RewardRedemptionRepo rewardRedemptionRepo;

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	AccountService accountService;

	@Override
	public int evaluateAndGrantReward(TransactionLog transactionLog) {

		if (!isEligible(transactionLog)) {
			return 0;
		}

		int points = transactionLog.getAmount()
				.divideToIntegralValue(POINT_UNIT)
				.intValue();

		if (points <= 0) {
			return 0;
		}

		RewardHistory rewardHistory = new RewardHistory(
				transactionLog.getFromAccountId(),
				transactionLog.getId(),
				points);

		rewardHistoryRepo.save(rewardHistory);

		return points;
	}

	private boolean isEligible(TransactionLog transactionLog) {

		if (transactionLog.getStatus() != TransactionStatus.SUCCESS) {
			return false;
		}

		if (transactionLog.getAmount() == null
				|| transactionLog.getAmount().compareTo(MIN_ELIGIBLE_AMOUNT) <= 0) {
			return false;
		}

		if (transactionLog.getFromAccountId() != null
				&& transactionLog.getFromAccountId().equals(transactionLog.getToAccountId())) {
			return false;
		}

		return true;
	}

	@Override
	public RewardSummaryResponse getRewardSummary(Long accountId) {

		List<RewardHistory> history = rewardHistoryRepo.findByAccountId(accountId);
		List<RewardRedemption> redemptions = rewardRedemptionRepo.findByAccountId(accountId);

		int totalEarned = history.stream()
				.mapToInt(RewardHistory::getPointsEarned)
				.sum();

		int totalRedeemed = redemptions.stream()
				.mapToInt(RewardRedemption::getPointsRedeemed)
				.sum();

		int available = totalEarned - totalRedeemed;

		return new RewardSummaryResponse(accountId, totalEarned, totalRedeemed, available, history, redemptions);
	}

	@Override
	public RedeemResponse redeemPoints(Long accountId, Integer points) {

		if (points == null || points <= 0) {
			throw new IllegalArgumentException("Points to redeem must be a positive number.");
		}

		RewardSummaryResponse summary = getRewardSummary(accountId);

		if (points > summary.availablePoints()) {
			throw new InsufficientRewardPointsException();
		}

		BigDecimal amountToCredit = BigDecimal.valueOf(points)
				.multiply(REDEMPTION_RUPEE_VALUE)
				.divide(REDEMPTION_POINT_UNIT);

		Account account = accountService.findById(accountId);

		accountService.credit(account, amountToCredit);
		accountRepo.save(account);

		RewardRedemption redemption = new RewardRedemption(accountId, points, amountToCredit);
		rewardRedemptionRepo.save(redemption);

		int remainingPoints = summary.availablePoints() - points;

		return new RedeemResponse(accountId, points, amountToCredit, account.getBalance(), remainingPoints);
	}
}
