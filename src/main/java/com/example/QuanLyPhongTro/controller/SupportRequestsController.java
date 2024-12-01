package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    // Lấy tất cả các yêu cầu hỗ trợ
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<SupportRequests> getAllSupportRequests() {
        return _supportRequestsService.getAllSupportRequests();
    }

    // Lấy yêu cầu hỗ trợ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SupportRequests> getSupportRequestById(@PathVariable int id) {
        SupportRequests supportRequest = _supportRequestsService.getSupportRequestById(id);
        if (supportRequest != null) {
            return ResponseEntity.ok(supportRequest);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy danh sách yêu cầu hỗ trợ với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách yêu cầu hỗ trợ với phân trang
    public Page<SupportRequests> getSupportRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _supportRequestsService.getSupportRequests(page, size);
    }

    // Thêm yêu cầu hỗ trợ mới
    @PostMapping("")
    public SupportRequests addSupportRequest(@RequestBody SupportRequests supportRequest) {
        return _supportRequestsService.addSupportRequest(supportRequest);
    }

    // Cập nhật yêu cầu hỗ trợ theo ID
    @PutMapping("/{id}")
    public ResponseEntity<SupportRequests> updateSupportRequest(@PathVariable int id, @RequestBody SupportRequests supportRequestDetails) {
        SupportRequests updatedSupportRequest = _supportRequestsService.updateSupportRequest(id, supportRequestDetails);
        if (updatedSupportRequest != null) {
            return ResponseEntity.ok(updatedSupportRequest);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa yêu cầu hỗ trợ theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupportRequest(@PathVariable int id) {
        if (_supportRequestsService.deleteSupportRequest(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    //Phuc test
    // Lấy danh sách request chưa được trả lời (cho admin)
    @GetMapping("/pending")
    public List<SupportRequests> getPendingRequests() {
        return _supportRequestsService.getPendingRequests();
    }

    // Admin trả lời request
    @PostMapping("/{id}/reply")
    public SupportRequests replyToRequest(@PathVariable int id, @RequestBody String replyContent) {
        return _supportRequestsService.replyToRequest(id, replyContent);
    }

    // User lấy tất cả các request của họ
    @GetMapping("/user/{userId}")
    public List<SupportRequests> getUserRequests(@PathVariable int userId) {
        return _supportRequestsService.getAllSupportRequests() // Hoặc thêm filter ở đây
                .stream()
                .filter(request -> request.getUser().getId() == userId)
                .toList();
    }
}