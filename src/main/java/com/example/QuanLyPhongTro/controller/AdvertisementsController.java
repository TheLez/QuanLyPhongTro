package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Advertisements;
import com.example.QuanLyPhongTro.services.AdvertisementsService;

import java.util.List;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementsController {

    @Autowired
    private AdvertisementsService _advertisementsService;

    // Lấy tất cả quảng cáo
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<Advertisements> getAllAdvertisements() {
        return _advertisementsService.getAllAdvertisements();
    }

    // Lấy quảng cáo theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Advertisements> getAdvertisementById(@PathVariable int id) {
        Advertisements advertisement = _advertisementsService.getAdvertisementById(id);
        if (advertisement != null) {
            return ResponseEntity.ok(advertisement);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy quảng cáo với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách quảng cáo với phân trang
    public Page<Advertisements> getAdvertisements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _advertisementsService.getAdvertisements(page, size);
    }

    // Thêm quảng cáo mới
    @PostMapping("")
    public Advertisements addAdvertisement(@RequestBody Advertisements advertisement) {
        return _advertisementsService.addAdvertisement(advertisement);
    }

    // Cập nhật quảng cáo theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Advertisements> updateAdvertisement(@PathVariable int id, @RequestBody Advertisements advertisementDetails) {
        Advertisements updatedAdvertisement = _advertisementsService.updateAdvertisement(id, advertisementDetails);
        if (updatedAdvertisement != null) {
            return ResponseEntity.ok(updatedAdvertisement);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa quảng cáo theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable int id) {
        if (_advertisementsService.deleteAdvertisement(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}