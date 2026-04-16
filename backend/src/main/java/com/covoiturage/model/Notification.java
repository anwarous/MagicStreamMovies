package com.covoiturage.model;
import com.covoiturage.model.enums.NotifType;
import jakarta.persistence.*;import lombok.*;import java.time.LocalDateTime;
@Entity @Table(name="notifications") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="recipient_id") private User recipient;
  @Enumerated(EnumType.STRING) private NotifType type;
  @Column(columnDefinition = "TEXT") private String payload;
  private Boolean isRead = false;
  private LocalDateTime createdAt = LocalDateTime.now();
}
