package com.Internova.internova.Service;

import com.Internova.internova.Model.Internship;
import com.Internova.internova.Repository.InternshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    private final InternshipRepository internshipRepository;

    private InternshipService(InternshipRepository internshipRepository) {
        this.internshipRepository = internshipRepository;
    }

    public Internship addInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    public Optional<Internship> getInternshipById(Long id) {
        return internshipRepository.findById(id);
    }

    public Internship updateInternship(Long id, Internship internshipDetails) {
        return internshipRepository.findById(id).map(internship -> {
            internship.setCompanyName(internship.getCompanyName());
            internship.setAbout(internship.getAbout());
            internship.setRole(internship.getRole());
            internship.setDescription(internship.getDescription());
            internship.setDuration(internship.getDuration());
            internship.setSkillsRequired(internship.getSkillsRequired());
            internship.setEligibility(internship.getEligibility());
            internship.setLocation(internship.getLocation());
            internship.setSalary(internship.getSalary());
            return internshipRepository.save(internship);
        }).orElseThrow(() -> new RuntimeException("Internship Not Found with ID: " + id));
    }

    public void deleteInternship(Long id) {
        internshipRepository.deleteById(id);

    }
}
