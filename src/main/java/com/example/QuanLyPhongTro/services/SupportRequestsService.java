package com.example.QuanLyPhongTro.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.SupportRequests;
import com.example.QuanLyPhongTro.repositories.SupportRequestsRepository;

import java.util.List;
import java.util.Map;

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

    public Page<SupportRequests> getSupportRequests(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return _supportRequestsRepository.findAll(pageable);
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
    // Lấy tất cả request chưa reply
    public List<SupportRequests> getPendingRequests() {
        return _supportRequestsRepository.findByStatus(0); // 0: Pending
    }

    // Admin trả lời request
    public SupportRequests replyToRequest(int id, String replyContent) {
        SupportRequests request = _supportRequestsRepository.findById(id).orElse(null);
        if (request != null && request.getStatus() == 0) { // Chỉ reply nếu chưa được trả lời
            // Đảm bảo replyContent là một chuỗi đơn giản
            if (replyContent.startsWith("{") && replyContent.endsWith("}")) {
                // Nếu replyContent là JSON, chỉ lấy phần giá trị bên trong
                replyContent = extractStringFromJson(replyContent);
            }
            request.setAdminReply(replyContent);
            request.setStatus(1); // Cập nhật trạng thái thành Replied
            return _supportRequestsRepository.save(request);
        }
        return null; // Không tìm thấy hoặc request đã được trả lời
    }

    // Phương thức để trích xuất chuỗi từ JSON
    private String extractStringFromJson(String json) {
        try {
            // Sử dụng thư viện Jackson hoặc Gson để parse JSON
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(json, Map.class);
            return map.get("reply"); // Lấy giá trị của key "reply"
        } catch (Exception e) {
            // Nếu không parse được, trả về chuỗi gốc
            return json;
        }
    }
}