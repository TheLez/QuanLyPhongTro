package com.example.QuanLyPhongTro.repositories;

import com.example.QuanLyPhongTro.models.SupportRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRequestsRepository extends JpaRepository<SupportRequests, Integer> {
    List<SupportRequests> findByStatus(Integer status); // Lấy danh sách request theo trạng thái
    List<SupportRequests> findByUserId(Integer userId);
}