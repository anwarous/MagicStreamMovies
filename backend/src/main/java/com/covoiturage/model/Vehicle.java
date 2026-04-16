package com.covoiturage.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="vehicles") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vehicle {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String brand; private String model; private String color;
  @Column(unique = true) private String licensePlate; private Integer seats;
  @OneToOne @JoinColumn(name = "user_id", nullable = false, unique = true) private User user;
}
