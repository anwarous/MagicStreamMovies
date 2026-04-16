package com.covoiturage.model;
import com.covoiturage.model.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;import java.time.*;
@Entity @Table(name="posts") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Enumerated(EnumType.STRING) private PostType type;
  private String origin; private String destination; private LocalDate departureDate; private LocalTime departureTime;
  private Integer seatsAvailable; private BigDecimal price;
  @Column(columnDefinition = "TEXT") private String description;
  @Enumerated(EnumType.STRING) private PostStatus status = PostStatus.ACTIVE;
  @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "author_id") private User author;
  private LocalDateTime createdAt = LocalDateTime.now();
}
