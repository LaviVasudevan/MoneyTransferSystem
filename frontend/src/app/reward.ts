export interface RewardHistory {
    id: string;
    accountId: number;
    transactionId: string;
    pointsEarned: number;
    createdOn: Date;
}

export interface RewardRedemption {
    id: string;
    accountId: number;
    pointsRedeemed: number;
    amountCredited: number;
    redeemedOn: Date;
}

export interface RewardSummary {
    accountId: number;
    totalPointsEarned: number;
    totalPointsRedeemed: number;
    availablePoints: number;
    history: RewardHistory[];
    redemptions: RewardRedemption[];
}

export interface RedeemResponse {
    accountId: number;
    pointsRedeemed: number;
    amountCredited: number;
    newBalance: number;
    remainingPoints: number;
}
