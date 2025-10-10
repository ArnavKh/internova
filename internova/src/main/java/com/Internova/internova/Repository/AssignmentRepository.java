package com.Internova.internova.Repository;

import com.Internova.internova.Model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findBySupervisorId(Long supervisorId);
}