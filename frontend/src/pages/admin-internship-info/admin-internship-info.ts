import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'admin-internship-info',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h1>{{ internship?.title }}</h1>
    <p>{{ internship?.description }}</p>
  `
})
export class AdminInternshipInfo {
  internship: any;

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.http.get(`http://localhost:8080/api/internships/${id}`)
      .subscribe(data => this.internship = data);
  }
}
