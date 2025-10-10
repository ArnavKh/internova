import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
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
  deadline: string;
  skillsRequired: string;
  description: string;
}

@Component({
  selector: 'app-student-dashboard',
  templateUrl: './student-dashboard.html',
  styleUrls: ['./student-dashboard.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class StudentDashboard implements OnInit {
  internships: Internship[] = [];
  showApplyForm = false;
  selectedInternship: Internship | null = null;
  selectedFile: File | null = null;

  applicantName: string = '';
  applicantEmail: string = '';
  applicantSkills: string = '';

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.loadInternships();
  }

  loadInternships() {
    this.http.get<Internship[]>("http://localhost:8089/admin/internships/all")
      .subscribe({
        next: (data) => this.internships = data,
        error: (err) => {
          console.error(err);
          alert('Failed to load internships.');
        }
      });
  }

  openApplyForm(internship: Internship) {
    this.selectedInternship = internship;
    this.showApplyForm = true;
    this.applicantName = '';
    this.applicantEmail = '';
    this.applicantSkills = '';
    this.selectedFile = null;
  }

  closeApplyForm() {
    this.showApplyForm = false;
    this.selectedInternship = null;
    this.selectedFile = null;
    this.applicantName = '';
    this.applicantEmail = '';
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  submitApplication() {
    if (!this.selectedFile || !this.selectedInternship || !this.applicantName || !this.applicantEmail) {
      alert('Please fill all fields and upload your resume.');
      return;
    }

    const formData = new FormData();
    formData.append('name', this.applicantName);
    formData.append('email', this.applicantEmail);
    formData.append('internshipId', this.selectedInternship.id.toString());
    formData.append('resume', this.selectedFile);

    this.http.post("http://localhost:8089/applications/apply", formData)
      .subscribe({
        next: () => {
          alert(`Application submitted!\n\nName: ${this.applicantName}\nEmail: ${this.applicantEmail}\nInternship: ${this.selectedInternship?.role}`);
          this.closeApplyForm();
        },
        error: (err) => {
          console.error(err);
          alert('Failed to submit application.');
        }
      });
  }
}
