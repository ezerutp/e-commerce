import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginRequest } from '../../features/auth/models/login-request.js';
import { LoginResponse } from '../../features/auth/models/login-response.js';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private api = 'http://localhost:8080/auth';
  private userNameSubject = new BehaviorSubject<string>('');
  public userName$ = this.userNameSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.api, request)
      .pipe(
        tap(res => {
          localStorage.setItem('token', res.token);
        })
      );
  }

  getToken() {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.userNameSubject.next('');
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) {
      return false;
    }
    
    return !this.isTokenExpired(token);
  }

  setUserName(name: string): void {
    this.userNameSubject.next(name);
  }

  private isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const exp = payload.exp;
      
      if (!exp) {
        return false;
      }
      
      return Date.now() >= exp * 1000;
    } catch (e) {
      return true;
    }
  }
}