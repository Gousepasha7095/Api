package Springboot.springboot.Controller;

import Springboot.springboot.Dto.*;
import Springboot.springboot.Entity.User;
import Springboot.springboot.Entity.UserAccount;
import Springboot.springboot.Entity.UserLoginAudit;
import Springboot.springboot.Repository.UserRepo;
import Springboot.springboot.Response.UserResponse;
import Springboot.springboot.Response.UserTransactionResponse;
import Springboot.springboot.Service.AuthService;
import Springboot.springboot.Utilities.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
//@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    
//@Autowired
//private UserRepo userRepo;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils =jwtUtils;
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
//        return ResponseEntity.ok(authService.register(registerDto));
//    }
@PostMapping("/register")
public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
    String message = authService.register(registerDto);
    Map<String, String> response = new HashMap<>();
    response.put("message", message);
    return ResponseEntity.ok(response);
}

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
//        return ResponseEntity.ok(authService.login(loginDto));
//    }
@PostMapping("/login")
public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
    Map<String, String> response = authService.login(loginDto);

    if (response.containsKey("message")) {
        return ResponseEntity.badRequest().body(response);
    }

    return ResponseEntity.ok(response); // Return token if login is successful
}

//    @PostMapping("/getUserWithAudit")
//    public UserResponse getUserWithAudit(@RequestBody UserAuditDto dto) {
//      User user = userRepo.findByUserName(dto.getUserName());
//        if (user == null) {
//            throw new RuntimeException("User not found or username mismatch");
//        }
//        UserResponse response = new UserResponse();
//        response.setUserName(user.getUsername());
//
//        List<UserAuditDto> auditDtos = user.getLoginAudits().stream().map(tx -> {
//            UserAuditDto t = new UserAuditDto();
//            t.setId(tx.getId());
//            t.setLoginTime(tx.getLoginTime());
//            return t;
//        }).collect(Collectors.toList());
//
//        response.setUserAuditDtos(auditDtos);
//        return response;
//    }

}
