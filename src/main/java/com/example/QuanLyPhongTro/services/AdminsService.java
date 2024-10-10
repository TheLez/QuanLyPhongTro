package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Admins;
import com.example.QuanLyPhongTro.repositories.AdminsRepository;

@Service
public class AdminsService {
	@Autowired
	private AdminsRepository _adminsRepository;
	public Admins getAdminById(int id) {
		return _adminsRepository.findById(id).get();
	}
}
