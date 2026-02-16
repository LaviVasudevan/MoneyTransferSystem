import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from '../auth';

@Component({
  selector: 'app-accountinfo',
  standalone: false,
  templateUrl: './accountinfo.html',
  styleUrl: './accountinfo.css',
})
export class Accountinfo {

  accountId: number | null = null; 

  constructor(private router: Router, private authService: Auth) {}

  ngOnInit(): void {
  const storedUser = sessionStorage.getItem('auth_user');
  console.log(storedUser);
  if (storedUser) {
    this.accountId = Number(storedUser);
  }

}

logout(): void {
    this.authService.logout();
    this.router.navigate(['']);
}
}
