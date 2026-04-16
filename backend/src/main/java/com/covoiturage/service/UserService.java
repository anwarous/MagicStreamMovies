package com.covoiturage.service;
import com.covoiturage.dto.response.UserResponse;import com.covoiturage.exception.ResourceNotFoundException;import com.covoiturage.model.*;import com.covoiturage.repository.UserRepository;import lombok.RequiredArgsConstructor;import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  public UserResponse getUser(Long id){var u=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id %d not found".formatted(id)));return new UserResponse(u.getId(),u.getFirstName(),u.getLastName(),u.getEmail(),u.getPhone(),u.getBio(),u.getAvatarUrl(),u.getHasVehicle());}
  public UserResponse updateMe(User me, UserResponse data){me.setFirstName(data.firstName());me.setLastName(data.lastName());me.setPhone(data.phone());me.setBio(data.bio());me.setAvatarUrl(data.avatarUrl());return getUser(userRepository.save(me).getId());}
  public UserResponse updateVehicle(User me, Vehicle vehicle){vehicle.setUser(me);me.setVehicle(vehicle);me.setHasVehicle(true);return getUser(userRepository.save(me).getId());}
}
