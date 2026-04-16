package com.covoiturage.model;
import com.covoiturage.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="users") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String firstName; private String lastName;
  @Column(unique = true, nullable = false) private String email;
  @Column(nullable = false) private String passwordHash;
  private String phone;
  @Column(columnDefinition = "TEXT") private String bio;
  private String avatarUrl;
  private Boolean hasVehicle = false;
  @Enumerated(EnumType.STRING) private Role role = Role.USER;
  private LocalDateTime createdAt = LocalDateTime.now();
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) private Vehicle vehicle;
  public String getFullName(){return firstName + " " + lastName;}
}
