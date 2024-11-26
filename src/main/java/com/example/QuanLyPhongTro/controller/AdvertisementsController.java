package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.dto.AdvertisementDTO;
import com.example.QuanLyPhongTro.dto.AdvertisementDetailDTO;
import com.example.QuanLyPhongTro.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @GetMapping("/get/{id}")
    public ResponseEntity<AdvertisementDetailDTO> getAdvertisementById(@PathVariable int id) {
        Advertisements advertisement = _advertisementsService.getAdvertisementById(id);
        if (advertisement != null) {
            AdvertisementDetailDTO advertisementDetailDTO = new AdvertisementDetailDTO(advertisement);
            return ResponseEntity.ok(advertisementDetailDTO);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy quảng cáo với phân trang
    @GetMapping ("/get") // Giữ nguyên để trả về danh sách quảng cáo với phân trang
    public ResponseEntity<PageDTO<AdvertisementDTO>> getAdvertisements(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(required = false) Integer areaMin,
            @RequestParam(required = false) Integer areaMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Gọi service với các tham số lọc
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AdvertisementDTO> advertisements = _advertisementsService.getAdvertisements(address, priceMin, priceMax, areaMin, areaMax, pageRequest);

        return ResponseEntity.ok(new PageDTO<AdvertisementDTO>(advertisements));
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