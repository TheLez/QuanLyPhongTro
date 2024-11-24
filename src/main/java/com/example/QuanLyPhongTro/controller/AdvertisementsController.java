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

    @GetMapping("")
    public List<Advertisements> getAllAdvertisements() {
        return _advertisementsService.getAllAdvertisements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advertisements> getAdvertisementById(@PathVariable int id) {
        Advertisements advertisement = _advertisementsService.getAdvertisementById(id);
        if (advertisement != null) {
            return ResponseEntity.ok(advertisement);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public Page<Advertisements> getAdvertisements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _advertisementsService.getAdvertisements(page, size);
    }


    @PostMapping("")
    public Advertisements addAdvertisement(@RequestBody Advertisements advertisement) {
        return _advertisementsService.addAdvertisement(advertisement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Advertisements> updateAdvertisement(@PathVariable int id, @RequestBody Advertisements advertisementDetails) {
        Advertisements updatedAdvertisement = _advertisementsService.updateAdvertisement(id, advertisementDetails);
        if (updatedAdvertisement != null) {
            return ResponseEntity.ok(updatedAdvertisement);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable int id) {
        if (_advertisementsService.deleteAdvertisement(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}