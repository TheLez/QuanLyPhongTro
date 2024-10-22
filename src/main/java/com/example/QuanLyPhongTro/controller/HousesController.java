package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Houses;
import com.example.QuanLyPhongTro.services.HousesService;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HousesController {

    @Autowired
    private HousesService _housesService;

    @GetMapping("")
    public List<Houses> getAllHouses() {
        return _housesService.getAllHouses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Houses> getHouseById(@PathVariable int id) {
        Houses house = _housesService.getHouseById(id);
        if (house != null) {
            return ResponseEntity.ok(house);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public Houses addHouse(@RequestBody Houses house) {
        return _housesService.addHouse(house);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Houses> updateHouse(@PathVariable int id, @RequestBody Houses houseDetails) {
        Houses updatedHouse = _housesService.updateHouse(id, houseDetails);
        if (updatedHouse != null) {
            return ResponseEntity.ok(updatedHouse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable int id) {
        if (_housesService.deleteHouse(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}