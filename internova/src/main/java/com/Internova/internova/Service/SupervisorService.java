package com.Internova.internova.Service;

import com.Internova.internova.Model.Supervisor;
import com.Internova.internova.Repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepository;

    public Supervisor addSupervisor(Supervisor supervisor) {
        return supervisorRepository.save(supervisor);
    }

    public List<Supervisor> getAllSupervisors() {
        return supervisorRepository.findAll();
    }

    public Supervisor getSupervisorById(Long id) {
        return supervisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor not found with id: " + id));
    }

    public Supervisor updateSupervisor(Long id, Supervisor updatedSupervisor) {
        return supervisorRepository.findById(id).map(supervisor -> {
            supervisor.setName(updatedSupervisor.getName());
            supervisor.setEmail(updatedSupervisor.getEmail());
            supervisor.setPassword(updatedSupervisor.getPassword());
            supervisor.setOrganization(updatedSupervisor.getOrganization());
            supervisor.setRoleType(updatedSupervisor.getRoleType());
            supervisor.setDepartment(updatedSupervisor.getDepartment());
            return supervisorRepository.save(supervisor);
        }).orElseThrow(() -> new RuntimeException("Supervisor not found with id: " + id));
    }

    public void deleteSupervisor(Long id) {
        supervisorRepository.deleteById(id);
    }

    public Supervisor login(String email, String password) {
        Supervisor supervisor = supervisorRepository.findByEmail(email);
        if (supervisor != null && supervisor.getPassword().equals(password)) {
            return supervisor;
        }
        throw new RuntimeException("Invalid email or password");
    }
}
