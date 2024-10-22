package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Tenants;
import com.example.QuanLyPhongTro.services.TenantsService;

import java.util.List;

@RestController
@RequestMapping("/tenants")
public class TenantsController {

    @Autowired
    private TenantsService _tenantsService;

    @GetMapping("")
    public List<Tenants> getAllTenants() {
        return _tenantsService.getAllTenants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenants> getTenantById(@PathVariable int id) {
        Tenants tenant = _tenantsService.getTenantById(id);
        if (tenant != null) {
            return ResponseEntity.ok(tenant);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public Tenants addTenant(@RequestBody Tenants tenant) {
        return _tenantsService.addTenant(tenant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tenants> updateTenant(@PathVariable int id, @RequestBody Tenants tenantDetails) {
        Tenants updatedTenant = _tenantsService.updateTenant(id, tenantDetails);
        if (updatedTenant != null) {
            return ResponseEntity.ok(updatedTenant);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable int id) {
        if (_tenantsService.deleteTenant(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}