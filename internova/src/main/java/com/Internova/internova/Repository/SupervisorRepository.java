package com.Internova.internova.Repository;

import com.Internova.internova.Model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    Supervisor findByEmail(String email);
}