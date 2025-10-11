package com.Internova.internova.Controller;

import com.Internova.internova.Model.Application;
import com.Internova.internova.Model.Internship;
import com.Internova.internova.Repository.ApplicationRepository;
import com.Internova.internova.Repository.InternshipRepository;
import com.Internova.internova.Service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/applications")
@CrossOrigin("*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private NotificationService notificationService;

    // ✅ Apply for Internship (Upload Resume + Send Notification + Email)
    @PostMapping("/apply")
    public ResponseEntity<Application> applyForInternship(
            @RequestParam("studentName") String studentName,
            @RequestParam("email") String email,
            @RequestParam("coverLetter") String coverLetter,
            @RequestParam("internshipId") Long internshipId,
            @RequestParam(value = "resume", required = false) MultipartFile resumeFile) {

        try {
            String resumePath = null;

            // ✅ Save uploaded resume to "uploads/resumes/"
            if (resumeFile != null && !resumeFile.isEmpty()) {
                String baseDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "resumes" + File.separator;
                File dir = new File(baseDir);
                if (!dir.exists()) dir.mkdirs();

                resumePath = baseDir + resumeFile.getOriginalFilename();
                resumeFile.transferTo(new File(resumePath));
            }

            // ✅ Fetch internship from database
            Internship internship = internshipRepository.findById(internshipId)
                    .orElseThrow(() -> new RuntimeException("Internship not found"));

            // ✅ Create and save application
            Application application = new Application();
            application.setStudentName(studentName);
            application.setEmail(email);
            application.setCoverLetter(coverLetter);
            application.setResumePath(resumePath);
            application.setStatus("Applied");
            application.setInternship(internship);

            Application savedApp = applicationService.saveApplication(application);

            // ✅ Create notification for supervisor/admin
            notificationService.createNotification(
                    studentName + " has applied for the internship: " + internship.getRole(),
                    "SUPERVISOR",
                    internship.getId() // assuming internship-supervisor mapping is based on ID
            );

            return ResponseEntity.ok(savedApp);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Get all applications for a specific internship (for supervisor/admin)
    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<?> getApplicationsByInternship(@PathVariable Long internshipId) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationsByInternship(internshipId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error retrieving applications.");
        }
    }
}