package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Advertisements;
import com.example.QuanLyPhongTro.repositories.AdvertisementsRepository;

import java.util.List;

@Service
public class AdvertisementsService {
    @Autowired
    private AdvertisementsRepository _advertisementsRepository;

    public List<Advertisements> getAllAdvertisements() {
        return _advertisementsRepository.findAll();
    }

    public Advertisements getAdvertisementById(int id) {
        return _advertisementsRepository.findById(id).orElse(null);
    }

    public Advertisements addAdvertisement(Advertisements advertisement) {
        return _advertisementsRepository.save(advertisement);
    }

    public Advertisements updateAdvertisement(int id, Advertisements advertisementDetails) {
        Advertisements advertisement = getAdvertisementById(id);
        if (advertisement != null) {
            advertisement.setDescription(advertisementDetails.getDescription());
            advertisement.setStatus(advertisementDetails.getStatus());
            advertisement.setAddress(advertisementDetails.getAddress());
            advertisement.setCost(advertisementDetails.getCost());
            advertisement.setCreatedAt(advertisementDetails.getCreatedAt());
            advertisement.setMaxOccupants(advertisementDetails.getMaxOccupants());
            advertisement.setTitle(advertisementDetails.getTitle());
            advertisement.setArea(advertisementDetails.getArea());
            advertisement.setLatitude(advertisementDetails.getLatitude());
            advertisement.setLongitude(advertisementDetails.getLongitude());
            advertisement.setUser(advertisementDetails.getUser());
            return _advertisementsRepository.save(advertisement);
        }
        return null;
    }

    public boolean deleteAdvertisement(int id) {
        if (_advertisementsRepository.existsById(id)) {
            _advertisementsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}