package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.RoomServices;
import com.example.QuanLyPhongTro.services.RoomServicesService;

import java.util.List;

@RestController
@RequestMapping("/room-services")
public class RoomServicesController {

    @Autowired
    private RoomServicesService _roomServicesService;

    @GetMapping("")
    public List<RoomServices> getAllRoomServices() {
        return _roomServicesService.getAllRoomServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomServices> getRoomServiceById(@PathVariable int id) {
        RoomServices roomService = _roomServicesService.getRoomServiceById(id);
        if (roomService != null) {
            return ResponseEntity.ok(roomService);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public Page<RoomServices> getRoomServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _roomServicesService.getRoomServices(page, size);
    }

    @PostMapping("")
    public RoomServices addRoomService(@RequestBody RoomServices roomService) {
        return _roomServicesService.addRoomService(roomService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomServices> updateRoomService(@PathVariable int id, @RequestBody RoomServices roomServiceDetails) {
        RoomServices updatedRoomService = _roomServicesService.updateRoomService(id, roomServiceDetails);
        if (updatedRoomService != null) {
            return ResponseEntity.ok(updatedRoomService);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomService(@PathVariable int id) {
        if (_roomServicesService.deleteRoomService(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}