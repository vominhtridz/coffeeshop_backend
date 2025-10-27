package com.coffeeshop.coffeeshop_backend.controller;

import com.coffeeshop.coffeeshop_backend.model.ERole;
import com.coffeeshop.coffeeshop_backend.model.Role;
import com.coffeeshop.coffeeshop_backend.model.User;
import com.coffeeshop.coffeeshop_backend.payload.request.LoginRequest;
import com.coffeeshop.coffeeshop_backend.payload.request.SignupRequest;
import com.coffeeshop.coffeeshop_backend.payload.response.JwtResponse;
import com.coffeeshop.coffeeshop_backend.payload.response.MessageResponse;
import com.coffeeshop.coffeeshop_backend.Repository.RoleRepository;
import com.coffeeshop.coffeeshop_backend.Repository.UserRepository;
import com.coffeeshop.coffeeshop_backend.security.jwt.JwtUtils;
import com.coffeeshop.coffeeshop_backend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*") // Cho phép gọi từ mọi nguồn
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    /**
     * API Đăng nhập
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Xác thực username/password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Set thông tin xác thực vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Tạo JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Lấy thông tin user
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Trả về thông tin user và JWT
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    /**
     * API Đăng ký
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Tạo user mới
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        // Set quyền (role)
        Set<Role> roles = new HashSet<>();
        
        // **QUAN TRỌNG: Gán quyền default là ROLE_USER**
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role 'USER' is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}