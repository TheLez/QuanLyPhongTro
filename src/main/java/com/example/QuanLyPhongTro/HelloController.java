package com.example.QuanLyPhongTro;

import com.example.QuanLyPhongTro.models.Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.services.AdminsService;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private AdminsService _adminsService;

    @GetMapping("/getall")
    public List<Admins> getAllAdmins() {
        return _adminsService.getAllAdmins();
    }

    @GetMapping("/find/{id}")
    public String sayHello() {
        return _adminsService.getAdminById(1).getUsername();
    }
}
