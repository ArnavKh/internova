package com.Internova.internova.Service;

import com.Internova.internova.Model.Admin;
import com.Internova.internova.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    public AdminRepository adminRepository;

    //Create or update admin
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    //Get all admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    //Get Admin by ID
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    //Delete admin by id
    public void deleteByAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    //Update Admin Details
    public Admin updateAdmin(Long id, Admin adminDetails) {
        return adminRepository.findById(id).map(admin -> {
            admin.setName(adminDetails.getName());
            admin.setEmail(adminDetails.getEmail());
            return adminRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
    }

    //Login
    public Admin login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null; // login failed
    }
}
