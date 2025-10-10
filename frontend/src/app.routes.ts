import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { AdminDashboard } from './pages/admin-dashboard/admin-dashboard';
import { StudentDashboard } from './pages/student-dashboard/student-dashboard';
import { AdminInternshipInfo } from './pages/admin-internship-info/admin-internship-info';
import { RegisterComponent } from './pages/register/register';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin', component: AdminDashboard },
  { path: 'admin/internship/:id', component: AdminInternshipInfo },
  { path: 'intern', component: StudentDashboard },
  { path: '**', redirectTo: '' }
];
