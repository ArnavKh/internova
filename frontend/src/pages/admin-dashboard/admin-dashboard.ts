import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

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

interface Applicant {
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
export class AdminDashboard {
  internships: Internship[] = [
    {
      id: 1,
      role: 'Software Development Intern',
      companyName: 'TechNova Solutions',
      location: 'Bangalore, India',
      duration: '3 Months',
      eligibility: 'B.Tech / B.E in CS',
      salary: '₹15,000/month',
      deadline: '2025-10-31',
      skillsRequired: 'Angular, JavaScript, TypeScript',
      description: 'Work on front-end development tasks and contribute to Angular projects.'
    },
    {
      id: 2,
      role: 'Marketing Intern',
      companyName: 'GrowthHive Media',
      location: 'Remote',
      duration: '2 Months',
      eligibility: 'MBA / Marketing',
      salary: '₹10,000/month',
      deadline: '2025-11-05',
      skillsRequired: 'SEO, Social Media, Content Writing',
      description: 'Assist the marketing team in campaigns and social media strategy.'
    }
  ];

  internshipApplicants: { [key: number]: Applicant[] } = {
    1: [
      { name: 'Alice Johnson', email: 'alice@example.com', resumeUrl: 'https://example.com/resume/alice.pdf' },
      { name: 'Bob Smith', email: 'bob@example.com', resumeUrl: 'https://example.com/resume/bob.pdf' },
    ],
    2: [
      { name: 'Charlie Brown', email: 'charlie@example.com', resumeUrl: 'https://example.com/resume/charlie.pdf' },
    ]
  };

  applicants: Applicant[] = [];
  selectedInternship: Internship | null = null;

  showAddForm = false;
  showApplicantsPopup = false;

  newInternship: Partial<Internship> = {};

  openAddInternship() {
    this.showAddForm = true;
  }

  addInternship() {
    const id = this.internships.length + 1;
    this.internships.push({ ...this.newInternship, id } as Internship);
    alert('Internship added successfully!');
    this.showAddForm = false;
    this.newInternship = {};
  }

  // View Applicants Popup
  viewInternship(id: number) {
    this.applicants = this.internshipApplicants[id] || [];
    this.selectedInternship = this.internships.find(i => i.id === id) || null;
    this.showApplicantsPopup = true;
  }

  closeApplicantsPopup() {
    this.showApplicantsPopup = false;
    this.selectedInternship = null;
    this.applicants = [];
  }

  selectedApplicants: Applicant[] = [];

  selectApplicant(applicant: Applicant) {
    if (!this.selectedApplicants.includes(applicant)) {
      this.selectedApplicants.push(applicant);
      alert(`${applicant.name} has been selected for ${this.selectedInternship?.role}`);
    } else {
      alert(`${applicant.name} is already selected.`);
    }
  }

  openResume(resumeUrl: string) {
    window.open(resumeUrl, '_blank');
  }
}
