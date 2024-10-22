package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.models.Admins;
import com.example.QuanLyPhongTro.services.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminsController {
    @Autowired
    private AdminsService _adminsService;

    @GetMapping("")
    public List<Admins> getAllAdmins() {
        return _adminsService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public Admins findById() {
        return _adminsService.getAdminById(1);
    }

    @PostMapping("")
    public Admins addAdmin(@RequestBody Admins admin) {
        return _adminsService.addAdmin(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admins> updateAdmin(@PathVariable int id, @RequestBody Admins adminDetails) {
        Admins updatedAdmin = _adminsService.updateAdmin(id, adminDetails);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int id) {
        if (_adminsService.deleteAdmin(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
