package com.Internova.internova.Controller;

import com.Internova.internova.Model.Admin;
import com.Internova.internova.Model.Internship;
import com.Internova.internova.Service.InternshipService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/internships")
public class InternshipController {
    private final InternshipService internshipService;

    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @PostMapping("/add")
    public ResponseEntity<Internship> addInternship(@RequestBody Internship internship) {
        return ResponseEntity.ok(internshipService.addInternship(internship));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Internship>> getAllInternships() {
        return ResponseEntity.ok(internshipService.getAllInternships());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internship> getInternshipById(@PathVariable Long id) {
        return internshipService.getInternshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Internship> updateInternship(@PathVariable Long id, @RequestBody Internship internship) {
        return ResponseEntity.ok(internshipService.updateInternship(id, internship));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInternship(@PathVariable Long id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.noContent().build();
    }
}
