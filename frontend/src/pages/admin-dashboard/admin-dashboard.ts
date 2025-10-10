import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface Internship {
  id: number;
  role: string;
  companyName: string;
  location: string;
  duration: string;
  eligibility: string;
  salary: string;
  skillsRequired: string;
  description: string;
  about: string;
}

interface Applicant {
  id: number;
  name: string;
  email: string;
  resumeUrl: string;
}

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.html',
  styleUrls: ['./admin-dashboard.css']
})
export class AdminDashboard implements OnInit {
  internships: Internship[] = [];
  applicants: Applicant[] = [];
  selectedInternship: Internship | null = null;

  showAddForm = false;
  showApplicantsPopup = false;

  newInternship: Partial<Internship> = {};
  selectedApplicants: Applicant[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadInternships();
  }

  // Load all internships from backend
  loadInternships() {
    this.http.get<Internship[]>("http://localhost:8089/admin/internships/all")
      .subscribe(data => this.internships = data);
  }

  // Show Add Internship form
  openAddInternship() {
    this.showAddForm = true;
  }

  // Add internship via API
  addInternship() {
    this.http.post<Internship>("http://localhost:8089/admin/internships/add", this.newInternship)
      .subscribe({
        next: (data) => {
          this.internships.push(data);
          this.showAddForm = false;
          this.newInternship = {};
          alert('Internship added successfully!');
        },
        error: (err) => {
          console.error(err);
          alert('Failed to add internship.');
        }
      });
  }

  // View applicants for selected internship
  viewInternship(id: number) {
    this.http.get<Applicant[]>("${this.baseUrl}/${id}/applicants")
      .subscribe({
        next: (data) => {
          this.applicants = data;
          this.selectedInternship = this.internships.find(i => i.id === id) || null;
          this.showApplicantsPopup = true;
        },
        error: (err) => {
          console.error(err);
          alert('Failed to load applicants.');
        }
      });
  }

  // Close applicants popup
  closeApplicantsPopup() {
    this.showApplicantsPopup = false;
    this.selectedInternship = null;
    this.applicants = [];
  }

  // Select applicant via API
  selectApplicant(applicant: Applicant) {
    if (!this.selectedInternship) return;

    this.http.post("`${this.baseUrl}/${this.selectedInternship.id}/select`", applicant)
      .subscribe({
        next: () => {
          if (!this.selectedApplicants.includes(applicant)) {
            this.selectedApplicants.push(applicant);
            alert(`${applicant.name} has been selected for ${this.selectedInternship?.role}`);
          } else {
            alert(`${applicant.name} is already selected.`);
          }
        },
        error: (err) => {
          console.error(err);
          alert(`Failed to select ${applicant.name}.`);
        }
      });
  }

  // Open applicant resume in new tab
  openResume(resumeUrl: string) {
    window.open(resumeUrl, '_blank');
  }
}