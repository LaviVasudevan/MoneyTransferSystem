import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { RedeemResponse, RewardSummary } from './reward';

@Injectable({
  providedIn: 'root',
})
export class Rewardservice {

  URI = 'http://localhost:8080/api/v1/accounts';

  constructor(private http: HttpClient) {}

  getRewards(accountId: number): Observable<RewardSummary> {
    return this.http.get<RewardSummary>(`${this.URI}/${accountId}/rewards`);
  }

  redeemPoints(accountId: number, points: number): Observable<RedeemResponse> {
    return this.http.post<RedeemResponse>(`${this.URI}/${accountId}/rewards/redeem`, { points });
  }
}
