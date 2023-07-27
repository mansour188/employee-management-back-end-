package com.example.employe.management.controllers;

import com.example.employe.management.config.JwtTokenUtil;
import com.example.employe.management.dto.AuthRequest;
import com.example.employe.management.dto.AuthResponse;
import com.example.employe.management.model.Users;
import com.example.employe.management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;
    private  final EmployeeService employeeService;
    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(), request.getPassword()
                            )
                    );

            User user = (User) authenticate.getPrincipal();

            String token=jwtTokenUtil.generateToken(user.getUsername());
            AuthResponse authResponse=new AuthResponse();
            authResponse.setToken(token);
            Users usr=employeeService.getUserByemail(request.getEmail());
            authResponse.setIdUser(usr.getUserId());

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(authResponse);

        }catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
