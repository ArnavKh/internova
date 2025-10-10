package com.Internova.internova.Model;

import jakarta.persistence.*;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status = "In Progress"; // In Progress | Completed

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Application studentApplication;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    private Internship internship;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Application getStudentApplication() {
        return studentApplication;
    }

    public void setStudentApplication(Application studentApplication) {
        this.studentApplication = studentApplication;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Internship getInternship() {
        return internship;
    }

    public void setInternship(Internship internship) {
        this.internship = internship;
    }
}