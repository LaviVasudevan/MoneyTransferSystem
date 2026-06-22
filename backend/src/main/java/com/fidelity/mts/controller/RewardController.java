package com.fidelity.mts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.mts.dto.RedeemRequest;
import com.fidelity.mts.dto.RedeemResponse;
import com.fidelity.mts.dto.RewardSummaryResponse;
import com.fidelity.mts.servcie.RewardService;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class RewardController {

	@Autowired
	RewardService rewardService;

	@GetMapping("{id}/rewards")
	public ResponseEntity<RewardSummaryResponse> getRewards(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(rewardService.getRewardSummary(id));
	}

	@PostMapping("{id}/rewards/redeem")
	public ResponseEntity<RedeemResponse> redeemRewards(@PathVariable Long id, @RequestBody RedeemRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(rewardService.redeemPoints(id, request.getPoints()));
	}
}
