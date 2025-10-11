import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  intern = { email: '', password: '' };
  admin = { email: '', password: '' };
  supervisor = { email: '', password: '' };
  activeTab: 'intern' | 'admin' | 'supervisor' = 'intern';

  constructor(private http: HttpClient, private router: Router) { }

  setTab(tab: 'intern' | 'admin' | 'supervisor') {
    this.activeTab = tab;
  }

  internLogin() {
    const email = this.intern.email;
    const password = this.intern.password;

    const url = `http://localhost:8089/students/login/${email}/${password}`;

    this.http.post(url, {})
      .subscribe(() => this.router.navigate(['/intern']));
  }


  adminLogin() {
    this.http.post('http://localhost:8089/admin/login', this.admin)
      .subscribe(() => this.router.navigate(['/admin']));
  }

  supervisorLogin() {
    this.http.post('http://localhost:8089/supervisor/login', this.supervisor)
      .subscribe({
        next: () => this.router.navigate(['/admin']),
        error: () => alert('Invalid credentials')
      });
  }


  goToRegister() {
    this.router.navigate(['/register']);
  }
}