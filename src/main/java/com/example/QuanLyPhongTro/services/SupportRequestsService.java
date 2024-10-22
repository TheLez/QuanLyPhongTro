package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.SupportRequests;
import com.example.QuanLyPhongTro.repositories.SupportRequestsRepository;

import java.util.List;

@Service
public class SupportRequestsService {
    @Autowired
    private SupportRequestsRepository _supportRequestsRepository;

    public List<SupportRequests> getAllSupportRequests() {
        return _supportRequestsRepository.findAll();
    }

    public SupportRequests getSupportRequestById(int id) {
        return _supportRequestsRepository.findById(id).orElse(null);
    }

    public SupportRequests addSupportRequest(SupportRequests supportRequest) {
        return _supportRequestsRepository.save(supportRequest);
    }

    public SupportRequests updateSupportRequest(int id, SupportRequests supportRequestDetails) {
        SupportRequests supportRequest = getSupportRequestById(id);
        if (supportRequest != null) {
            supportRequest.setCreatedAt(supportRequestDetails.getCreatedAt());
            supportRequest.setStatus(supportRequestDetails.getStatus());
            supportRequest.setContent(supportRequestDetails.getContent());
            supportRequest.setUser(supportRequestDetails.getUser());
            return _supportRequestsRepository.save(supportRequest);
        }
        return null;
    }

    public boolean deleteSupportRequest(int id) {
        if (_supportRequestsRepository.existsById(id)) {
            _supportRequestsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}