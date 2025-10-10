package com.Internova.internova.Controller;

import com.Internova.internova.Model.Admin;
import com.Internova.internova.Service.AdminService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.Internova.internova.Service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.saveAdmin(admin);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Update Admin
    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin adminDetails) {
        try {
            Admin updatedAdmin = adminService.updateAdmin(id, adminDetails);
            return ResponseEntity.ok(updatedAdmin);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete Admin
    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.deleteByAdmin(id);
        return "Admin Deleted by id: " + id;
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestBody Admin admin) {
        Admin loggedInAdmin = adminService.login(admin.getEmail(), admin.getPassword());
        if (loggedInAdmin != null) {
            return ResponseEntity.ok(loggedInAdmin);
        } else {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }
    }

}
