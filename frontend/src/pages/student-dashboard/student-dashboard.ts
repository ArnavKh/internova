import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface Internship {
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
export class StudentDashboard {
  internships: Internship[] = [
    {
      role: 'Software Development Intern',
      companyName: 'TechNova Solutions',
      location: 'Bangalore, India',
      duration: '3 Months',
      eligibility: 'B.Tech / B.E in CS',
      salary: '₹15,000/month',
      deadline: '2025-10-31',
      skillsRequired: 'Angular, JavaScript',
      description: 'Work on front-end development tasks.'
    },
    {
      role: 'Marketing Intern',
      companyName: 'GrowthHive Media',
      location: 'Remote',
      duration: '2 Months',
      eligibility: 'MBA',
      salary: '₹10,000/month',
      deadline: '2025-11-05',
      skillsRequired: 'Social Media, SEO',
      description: 'Assist the marketing team.'
    }
  ];

  showApplyForm = false;
  selectedInternship: Internship | null = null;
  selectedFile: File | null = null;

  // ✅ New form fields
  applicantName: string = '';
  applicantEmail: string = '';

  openApplyForm(internship: Internship) {
    this.selectedInternship = internship;
    this.showApplyForm = true;

    // reset previous data
    this.applicantName = '';
    this.applicantEmail = '';
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
    if (this.selectedFile && this.selectedInternship && this.applicantName && this.applicantEmail) {
      // Here you can handle upload to server or API call
      alert(
        `Application submitted!\n\nName: ${this.applicantName}\nEmail: ${this.applicantEmail}\nInternship: ${this.selectedInternship.role} at ${this.selectedInternship.companyName}`
      );
      this.closeApplyForm();
    } else {
      alert('Please fill all fields and upload your resume.');
    }
  }
}
