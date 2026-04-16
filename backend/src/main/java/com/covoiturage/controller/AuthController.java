package com.covoiturage.controller;
import com.covoiturage.dto.request.*;import com.covoiturage.dto.response.*;import com.covoiturage.service.AuthService;import lombok.RequiredArgsConstructor;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  @PostMapping("/register") public JwtResponse register(@RequestBody RegisterRequest req){return authService.register(req);} 
  @PostMapping("/login") public JwtResponse login(@RequestBody LoginRequest req){return authService.login(req);} 
  @GetMapping("/me") public UserResponse me(Authentication auth){return authService.toResponse(authService.getByEmail(auth.getName()));}
  @PostMapping("/refresh") public JwtResponse refresh(Authentication auth){var user=authService.getByEmail(auth.getName());return new JwtResponse(authService.login(new LoginRequest(user.getEmail(),"")).token(), authService.toResponse(user));}
}
