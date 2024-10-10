package com.example.QuanLyPhongTro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongTro.services.AdminsService;

@RestController
public class HelloController {

	@Autowired
	private AdminsService _adminsService;
	
    @GetMapping("/hello")
    public String sayHello() {
    	
        return _adminsService.getAdminById(1).getUsername();
    }
}
