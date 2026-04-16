package com.covoiturage.service;
import com.covoiturage.dto.request.*;import com.covoiturage.dto.response.*;import com.covoiturage.exception.UnauthorizedException;import com.covoiturage.model.User;import com.covoiturage.model.enums.Role;import com.covoiturage.repository.UserRepository;import com.covoiturage.security.JwtUtil;import lombok.RequiredArgsConstructor;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository; private final PasswordEncoder encoder; private final JwtUtil jwtUtil;
  public JwtResponse register(RegisterRequest req){
    User user=User.builder().firstName(req.firstName()).lastName(req.lastName()).email(req.email()).passwordHash(encoder.encode(req.password())).phone(req.phone()).role(Role.USER).hasVehicle(false).build();
    user=userRepository.save(user);
    return new JwtResponse(jwtUtil.generateToken(user.getEmail()), toResponse(user));
  }
  public JwtResponse login(LoginRequest req){
    User user=userRepository.findByEmail(req.email()).orElseThrow(()->new UnauthorizedException("Invalid credentials"));
    if(!encoder.matches(req.password(), user.getPasswordHash())) throw new UnauthorizedException("Invalid credentials");
    return new JwtResponse(jwtUtil.generateToken(user.getEmail()), toResponse(user));
  }
  public User getByEmail(String email){return userRepository.findByEmail(email).orElseThrow(()->new UnauthorizedException("User missing"));}
  public UserResponse toResponse(User user){return new UserResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getPhone(),user.getBio(),user.getAvatarUrl(),user.getHasVehicle());}
}
