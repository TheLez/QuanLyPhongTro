package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.SupportRequests;
import com.example.QuanLyPhongTro.services.SupportRequestsService;

import java.util.List;

@RestController
@RequestMapping("/support-requests")
public class SupportRequestsController {

    @Autowired
    private SupportRequestsService _supportRequestsService;

    @GetMapping("")
    public List<SupportRequests> getAllSupportRequests() {
        return _supportRequestsService.getAllSupportRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportRequests> getSupportRequestById(@PathVariable int id) {
        SupportRequests supportRequest = _supportRequestsService.getSupportRequestById(id);
        if (supportRequest != null) {
            return ResponseEntity.ok(supportRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public SupportRequests addSupportRequest(@RequestBody SupportRequests supportRequest) {
        return _supportRequestsService.addSupportRequest(supportRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportRequests> updateSupportRequest(@PathVariable int id, @RequestBody SupportRequests supportRequestDetails) {
        SupportRequests updatedSupportRequest = _supportRequestsService.updateSupportRequest(id, supportRequestDetails);
        if (updatedSupportRequest != null) {
            return ResponseEntity.ok(updatedSupportRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupportRequest(@PathVariable int id) {
        if (_supportRequestsService.deleteSupportRequest(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}