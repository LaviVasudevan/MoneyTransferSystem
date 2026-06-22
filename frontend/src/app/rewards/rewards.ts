import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Rewardservice } from '../rewardservice';
import { RewardHistory, RewardRedemption } from '../reward';

@Component({
  selector: 'app-rewards',
  standalone: false,
  templateUrl: './rewards.html',
  styleUrl: './rewards.css',
})
export class Rewards implements OnInit {

  currentAccountId: number = 0;

  totalPointsEarned: number = 0;
  totalPointsRedeemed: number = 0;
  availablePoints: number = 0;

  history: RewardHistory[] = [];
  redemptions: RewardRedemption[] = [];

  redeemPointsInput: number | null = null;
  redeemMessage: string | null = null;
  redeemError: string | null = null;
  redeeming: boolean = false;

  constructor(
    private rewardservice: Rewardservice,
    private route: ActivatedRoute,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((param) => {
      const id = Number(param.get('id'));
      this.currentAccountId = id;
      this.loadRewards();
    });
  }

  loadRewards(): void {
    this.rewardservice.getRewards(this.currentAccountId).subscribe({
      next: (summary) => {
        this.totalPointsEarned = summary.totalPointsEarned;
        this.totalPointsRedeemed = summary.totalPointsRedeemed;
        this.availablePoints = summary.availablePoints;

        this.history = summary.history.sort((a, b) => {
          return new Date(b.createdOn).getTime() - new Date(a.createdOn).getTime();
        });

        this.redemptions = summary.redemptions.sort((a, b) => {
          return new Date(b.redeemedOn).getTime() - new Date(a.redeemedOn).getTime();
        });

        this.cd.detectChanges();
      }
    });
  }

  // Rs value the user would get for the points currently entered in the input.
  get redeemValuePreview(): number {
    if (!this.redeemPointsInput || this.redeemPointsInput <= 0) {
      return 0;
    }
    return (this.redeemPointsInput * 5) / 100;
  }

  redeem(): void {
    this.redeemMessage = null;
    this.redeemError = null;

    if (!this.redeemPointsInput || this.redeemPointsInput <= 0) {
      this.redeemError = 'Enter a valid number of points to redeem.';
      return;
    }

    if (this.redeemPointsInput > this.availablePoints) {
      this.redeemError = `You only have ${this.availablePoints} points available.`;
      return;
    }

    this.redeeming = true;

    this.rewardservice.redeemPoints(this.currentAccountId, this.redeemPointsInput).subscribe({
      next: (resp) => {
        this.redeemMessage = `Redeemed ${resp.pointsRedeemed} points for ₹${resp.amountCredited.toFixed(2)}. New balance: ₹${resp.newBalance.toFixed(2)}.`;
        this.redeemPointsInput = null;
        this.redeeming = false;
        this.loadRewards();
      },
      error: (err) => {
        this.redeemError = err?.error?.message || 'Could not redeem points. Please try again.';
        this.redeeming = false;
        this.cd.detectChanges();
      }
    });
  }

  redeemAll(): void {
    this.redeemPointsInput = this.availablePoints;
    this.redeem();
  }

  formatDate(dateString: string | Date): string {
    const date = new Date(dateString);
    return date.toLocaleString('en-IN', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  goBack(): void {
    this.router.navigate(['/home']);
  }
}
