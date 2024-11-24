package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.AdvertisementImages;
import com.example.QuanLyPhongTro.services.AdvertisementImagesService;

import java.util.List;

@RestController
@RequestMapping("/advertisement-images")
public class AdvertisementImagesController {

    @Autowired
    private AdvertisementImagesService _advertisementImagesService;

    @GetMapping("")
    public List<AdvertisementImages> getAllImages() {
        return _advertisementImagesService.getAllImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementImages> getImageById(@PathVariable int id) {
        AdvertisementImages image = _advertisementImagesService.getImageById(id);
        if (image != null) {
            return ResponseEntity.ok(image);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public Page<AdvertisementImages> getAdvertisementImages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _advertisementImagesService.getAdvertisementImages(page, size);
    }

    @PostMapping("")
    public AdvertisementImages addImage(@RequestBody AdvertisementImages image) {
        return _advertisementImagesService.addImage(image);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementImages> updateImage(@PathVariable int id, @RequestBody AdvertisementImages imageDetails) {
        AdvertisementImages updatedImage = _advertisementImagesService.updateImage(id, imageDetails);
        if (updatedImage != null) {
            return ResponseEntity.ok(updatedImage);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable int id) {
        if (_advertisementImagesService.deleteImage(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}