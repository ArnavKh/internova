import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class RegisterComponent {
  activeTab: 'intern' | 'admin' | 'supervisor' = 'intern';

  intern = { name: '', email: '', password: '' };
  admin = { name: '', email: '', password: '' };
  supervisor = { name: '', email: '', password: '' };

  constructor(private http: HttpClient, private router: Router) {}

  setTab(tab: 'intern' | 'admin' | 'supervisor') {
    this.activeTab = tab;
  }

  registerIntern() {
    this.http.post('http://localhost:8089/students/register', this.intern)
      .subscribe(() => {
        alert('Intern registered successfully!');
        this.router.navigate(['/login']);
      });
  }

  registerAdmin() {
    this.http.post('http://localhost:8080/api/admin/register', this.admin)
      .subscribe(() => {
        alert('Admin registered successfully!');
        this.router.navigate(['/login']);
      });
  }

  registerSupervisor() {
    this.http.post('http://localhost:8080/api/supervisor/register', this.supervisor)
      .subscribe(() => {
        alert('Supervisor registered successfully!');
        this.router.navigate(['/login']);
      });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
