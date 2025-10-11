package com.Internova.internova.Controller;

import com.Internova.internova.Model.Assignment;
import com.Internova.internova.Model.Application;
import com.Internova.internova.Model.Internship;
import com.Internova.internova.Model.Student;
import com.Internova.internova.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired private InternshipRepository internshipRepository;
    @Autowired private ApplicationRepository applicationRepository;
    @Autowired private SupervisorRepository supervisorRepository;
    @Autowired private AssignmentRepository assignmentRepository;
    @Autowired private StudentRepository studentRepository;

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> getAdminDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalInternships", internshipRepository.count());
        data.put("totalApplications", applicationRepository.count());
        data.put("totalSupervisors", supervisorRepository.count());
        data.put("totalAssignments", assignmentRepository.count());
        data.put("totalStudents", studentRepository.count());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/supervisor/{supervisorId}")
    public ResponseEntity<Map<String, Object>> getSupervisorDashboard(@PathVariable Long supervisorId) {
        Map<String, Object> data = new HashMap<>();

        List<Assignment> assigned = assignmentRepository.findBySupervisorId(supervisorId);
        data.put("assignedCount", assigned.size());

        // basic summary of assigned students
        List<Map<String, Object>> students = assigned.stream().map(a -> {
            Map<String, Object> m = new HashMap<>();
            Application app = a.getStudentApplication();
            m.put("applicationId", app != null ? app.getId() : null);
            m.put("studentName", app != null ? app.getStudentName() : null);
            m.put("email", app != null ? app.getEmail() : null);
            Internship intern = a.getInternship();
            m.put("internshipId", intern != null ? intern.getId() : null);
            m.put("internshipTitle", intern != null ? intern.getRole() : null);
            m.put("assignmentStatus", a.getStatus());
            return m;
        }).collect(Collectors.toList());

        data.put("assignedStudents", students);

        long pendingEvaluations = assigned.stream()
                .filter(a -> a.getStudentApplication() != null && "Accepted".equalsIgnoreCase(a.getStudentApplication().getStatus()))
                .count();
        data.put("pendingEvaluations", pendingEvaluations);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentDashboard(@PathVariable Long studentId) {
        Map<String, Object> data = new HashMap<>();

        // find student (optional)
        Optional<Student> studentOpt = studentRepository.findById(studentId);

        // find applications by email if student exists, else empty list
        List<Application> applications = Collections.emptyList();
        if (studentOpt.isPresent()) {
            String email = studentOpt.get().getEmail();
            applications = applicationRepository.findByEmail(email);
        }

        data.put("student", studentOpt.orElse(null));
        data.put("applications", applications);

        long applied = applications.size();
        long accepted = applications.stream().filter(a -> "Accepted".equalsIgnoreCase(a.getStatus())).count();
        long rejected = applications.stream().filter(a -> "Rejected".equalsIgnoreCase(a.getStatus())).count();

        data.put("totalApplied", applied);
        data.put("acceptedCount", accepted);
        data.put("rejectedCount", rejected);

        return ResponseEntity.ok(data);
    }
}