package com.Internova.internova.Service;

import com.Internova.internova.Model.Application;
import com.Internova.internova.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsByInternship(Long internshipId) {
        return applicationRepository.findByInternshipId(internshipId);
    }
}