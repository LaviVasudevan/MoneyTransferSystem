package com.fidelity.mts.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fidelity.mts.entity.RewardRedemption;

@Repository
public interface RewardRedemptionRepo extends JpaRepository<RewardRedemption, UUID> {

	List<RewardRedemption> findByAccountId(Long accountId);
}
