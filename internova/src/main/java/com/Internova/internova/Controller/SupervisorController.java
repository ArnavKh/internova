package com.Internova.internova.Controller;

import com.Internova.internova.Model.Supervisor;
import com.Internova.internova.Service.SupervisorService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/supervisor")
@CrossOrigin("*")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @PostMapping("/add")
    public ResponseEntity<Supervisor> addSupervisor(@RequestBody Supervisor supervisor) {
        return ResponseEntity.ok(supervisorService.addSupervisor(supervisor));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Supervisor>> getAllSupervisors() {
        return ResponseEntity.ok(supervisorService.getAllSupervisors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supervisor> getSupervisorById(@PathVariable Long id) {
        return ResponseEntity.ok(supervisorService.getSupervisorById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Supervisor> updateSupervisor(@PathVariable Long id, @RequestBody Supervisor updatedSupervisor) {
        return ResponseEntity.ok(supervisorService.updateSupervisor(id, updatedSupervisor));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupervisor(@PathVariable Long id) {
        supervisorService.deleteSupervisor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Supervisor> login(@RequestBody Supervisor supervisor) {
        Supervisor loggedIn = supervisorService.login(supervisor.getEmail(), supervisor.getPassword());
        return ResponseEntity.ok(loggedIn);
    }
}