package Springboot.springboot.Service;

import Springboot.springboot.Dto.LoginDto;
import Springboot.springboot.Dto.RegisterDto;
import Springboot.springboot.Entity.User;
import Springboot.springboot.Entity.UserLoginAudit;
import Springboot.springboot.Repository.UserLoginAuditRepository;
import Springboot.springboot.Repository.UserRepo;
import Springboot.springboot.Utilities.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    private UserLoginAuditRepository userLoginAuditRepository;

    @Autowired
    private UserRepo userRepos;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public String register(RegisterDto registerDto) {
        if (userRepo.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepo.save(user);

        return "User registered successfully!";
    }

//    public String login(LoginDto loginDto) {
//        Optional<User> user = userRepo.findByUsername(loginDto.getUsername());
//
//        if (user.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
//            throw new RuntimeException("Invalid username or password!");
//        }
//
//        return jwtUtils.generateToken(user.get().getUsername());
//    }

    public Map<String, String> login(LoginDto loginDto) {
        Optional<User> userOptional = userRepo.findByUsername(loginDto.getUsername());


        if (userOptional.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), userOptional.get().getPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid username or password!");
            return errorResponse;
        }

        User user = userOptional.get();
        UserLoginAudit loginAudit = new UserLoginAudit();
        loginAudit.setUser(user);
        loginAudit.setLoginTime(LocalDateTime.now());
        userLoginAuditRepository.save(loginAudit);
        String token = jwtUtils.generateToken(user.getUsername()); // Generate token without role

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("token", token);
        return successResponse;
    }

    public Optional<User> findByUserName(String userName){
        return userRepos.findByUsername(userName);
    }
}
