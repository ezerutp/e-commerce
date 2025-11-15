import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface LoginResponse {
  token: string;
  username: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private loginUrl = 'http://localhost:8080/auth';

  login(credentials: { username: string; password: string }): Observable<string> {
    return this.http.post(this.loginUrl, credentials, { responseType: 'text' });
  }
}
