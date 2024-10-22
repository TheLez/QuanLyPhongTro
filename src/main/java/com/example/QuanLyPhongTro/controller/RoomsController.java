package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Rooms;
import com.example.QuanLyPhongTro.services.RoomsService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    @Autowired
    private RoomsService _roomsService;

    @GetMapping("")
    public List<Rooms> getAllRooms() {
        return _roomsService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rooms> getRoomById(@PathVariable int id) {
        Rooms room = _roomsService.getRoomById(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public Rooms addRoom(@RequestBody Rooms room) {
        return _roomsService.addRoom(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rooms> updateRoom(@PathVariable int id, @RequestBody Rooms roomDetails) {
        Rooms updatedRoom = _roomsService.updateRoom(id, roomDetails);
        if (updatedRoom != null) {
            return ResponseEntity.ok(updatedRoom);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        if (_roomsService.deleteRoom(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}