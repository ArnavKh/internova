import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private BASE_URL = 'http://localhost:8089/';

  constructor(private http: HttpClient) {}

  loginIntern(credentials: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/intern/login`, credentials);
  }

  loginAdmin(credentials: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/admin/login`, credentials);
  }
}
