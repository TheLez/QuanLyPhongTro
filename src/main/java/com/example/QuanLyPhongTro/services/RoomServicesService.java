package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.RoomServices;
import com.example.QuanLyPhongTro.repositories.RoomServicesRepository;

import java.util.List;

@Service
public class RoomServicesService {
    @Autowired
    private RoomServicesRepository _roomServicesRepository;

    public List<RoomServices> getAllRoomServices() {
        return _roomServicesRepository.findAll();
    }

    public RoomServices getRoomServiceById(int id) {
        return _roomServicesRepository.findById(id).orElse(null);
    }

    public RoomServices addRoomService(RoomServices roomService) {
        return _roomServicesRepository.save(roomService);
    }

    public RoomServices updateRoomService(int id, RoomServices roomServiceDetails) {
        RoomServices roomService = getRoomServiceById(id);
        if (roomService != null) {
            roomService.setName(roomServiceDetails.getName());
            roomService.setCost(roomServiceDetails.getCost());
            roomService.setCreatedAt(roomServiceDetails.getCreatedAt());
            roomService.setUnit(roomServiceDetails.getUnit());
            roomService.setRoom(roomServiceDetails.getRoom());
            return _roomServicesRepository.save(roomService);
        }
        return null;
    }

    public boolean deleteRoomService(int id) {
        if (_roomServicesRepository.existsById(id)) {
            _roomServicesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}