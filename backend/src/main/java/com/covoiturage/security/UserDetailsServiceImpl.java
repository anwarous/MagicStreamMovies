package com.covoiturage.security;
import com.covoiturage.repository.UserRepository;import lombok.RequiredArgsConstructor;import org.springframework.security.core.userdetails.*;import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;
  @Override public UserDetails loadUserByUsername(String username){
    var user=userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPasswordHash()).authorities("ROLE_"+user.getRole().name()).build();
  }
}
