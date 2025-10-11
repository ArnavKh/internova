package com.Internova.internova.Repository;

import com.Internova.internova.Model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByInternshipId(Long internshipId);
    List<Application> findByEmail(String email);
}