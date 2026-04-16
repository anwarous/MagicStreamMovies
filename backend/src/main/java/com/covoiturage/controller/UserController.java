package com.covoiturage.controller;
import com.covoiturage.dto.response.UserResponse;import com.covoiturage.model.Vehicle;import com.covoiturage.service.*;import lombok.RequiredArgsConstructor;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/users") @RequiredArgsConstructor
public class UserController {
  private final UserService userService; private final AuthService authService;
  @GetMapping("/{id}") public UserResponse byId(@PathVariable Long id){return userService.getUser(id);} 
  @PutMapping("/me") public UserResponse updateMe(@RequestBody UserResponse body, Authentication auth){return userService.updateMe(authService.getByEmail(auth.getName()), body);} 
  @PutMapping("/me/vehicle") public UserResponse updateVehicle(@RequestBody Vehicle body, Authentication auth){return userService.updateVehicle(authService.getByEmail(auth.getName()), body);} 
}
