package com.covoiturage.model;
import jakarta.persistence.*;import lombok.*;import java.time.LocalDateTime;import java.util.*;
@Entity @Table(name="conversations") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Conversation {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @ManyToMany
  @JoinTable(name = "conversation_participants", joinColumns = @JoinColumn(name = "conversation_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> participants = new ArrayList<>();
  @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "post_id") private Post post;
  private LocalDateTime createdAt = LocalDateTime.now();
}
