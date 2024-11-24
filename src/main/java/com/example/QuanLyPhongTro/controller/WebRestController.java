package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.jwt.JwtTokenProvider;
import com.example.QuanLyPhongTro.models.CustomUserDetails;
import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.payload.LoginRequest;
import com.example.QuanLyPhongTro.payload.LoginResponse;
import com.example.QuanLyPhongTro.payload.RandomStuff;
import com.example.QuanLyPhongTro.payload.RegisterRequest;
import com.example.QuanLyPhongTro.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class WebRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // Kiểm tra xem tên người dùng đã tồn tại hay chưa
        if (userRepository.findByUsername(registerRequest.getUsername()) != null) {
            return "Tên người dùng đã tồn tại!";
        }

        // Tạo mới người dùng
        Users user = new Users();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Lưu người dùng vào database
        userRepository.save(user);
        return "Đăng ký thành công!";
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public RandomStuff randomStuff(){
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }

}

