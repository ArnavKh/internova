package com.Internova.internova.Service;

import com.Internova.internova.Model.Assignment;
import com.Internova.internova.Repository.ApplicationRepository;
import com.Internova.internova.Repository.AssignmentRepository;
import com.Internova.internova.Repository.InternshipRepository;
import com.Internova.internova.Repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    public Assignment assignStudent(Assignment assignment) {
        // existing assign logic here...
        var app = applicationRepository.findById(assignment.getStudentApplication().getId())
                .orElseThrow(() -> new RuntimeException("Application not found"));
        var supervisor = supervisorRepository.findById(assignment.getSupervisor().getId())
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));
        var internship = internshipRepository.findById(assignment.getInternship().getId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        app.setSupervisor(supervisor);
        app.setInternship(internship);
        applicationRepository.save(app);

        assignment.setStudentApplication(app);
        assignment.setSupervisor(supervisor);
        assignment.setInternship(internship);

        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // ✅ New method
    public List<Assignment> getAssignmentsBySupervisor(Long supervisorId) {
        return assignmentRepository.findBySupervisorId(supervisorId);
    }
}