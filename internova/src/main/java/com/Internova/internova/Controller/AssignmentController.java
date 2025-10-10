package com.Internova.internova.Controller;

import com.Internova.internova.Model.Assignment;
import com.Internova.internova.Service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
@CrossOrigin("*")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/assign")
    public ResponseEntity<Assignment> assignStudent(
            @RequestParam Long applicationId,
            @RequestParam Long supervisorId,
            @RequestParam Long internshipId) {

        Assignment assignment = new Assignment();

        assignment.getStudentApplication().setId(applicationId);
        assignment.getSupervisor().setId(supervisorId);
        assignment.getInternship().setId(internshipId);

        Assignment saved = assignmentService.assignStudent(assignment);
        return ResponseEntity.ok(saved);
    }

    // ✅ New endpoint
    @GetMapping("/supervisor/{supervisorId}")
    public ResponseEntity<List<Assignment>> getAssignmentsBySupervisor(@PathVariable Long supervisorId) {
        List<Assignment> assignments = assignmentService.getAssignmentsBySupervisor(supervisorId);
        return ResponseEntity.ok(assignments);
    }
}