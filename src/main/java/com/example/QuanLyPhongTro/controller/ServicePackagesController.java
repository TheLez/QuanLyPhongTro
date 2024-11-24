package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.ServicePackages;
import com.example.QuanLyPhongTro.services.ServicePackagesService;

import java.util.List;

@RestController
@RequestMapping("/service-packages")
public class ServicePackagesController {

    @Autowired
    private ServicePackagesService _servicePackagesService;

    @GetMapping("")
    public List<ServicePackages> getAllServicePackages() {
        return _servicePackagesService.getAllServicePackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicePackages> getServicePackageById(@PathVariable int id) {
        ServicePackages servicePackage = _servicePackagesService.getServicePackageById(id);
        if (servicePackage != null) {
            return ResponseEntity.ok(servicePackage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public Page<ServicePackages> getServicePackages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _servicePackagesService.getServicePackages(page, size);
    }

    @PostMapping("")
    public ServicePackages addServicePackage(@RequestBody ServicePackages servicePackage) {
        return _servicePackagesService.addServicePackage(servicePackage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicePackages> updateServicePackage(@PathVariable int id, @RequestBody ServicePackages servicePackageDetails) {
        ServicePackages updatedServicePackage = _servicePackagesService.updateServicePackage(id, servicePackageDetails);
        if (updatedServicePackage != null) {
            return ResponseEntity.ok(updatedServicePackage);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicePackage(@PathVariable int id) {
        if (_servicePackagesService.deleteServicePackage(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}