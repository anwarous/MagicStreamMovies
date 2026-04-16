package com.covoiturage.controller;
import com.covoiturage.dto.response.ConversationResponse;import com.covoiturage.service.*;import lombok.RequiredArgsConstructor;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/conversations") @RequiredArgsConstructor
public class ConversationController {
  private final ConversationService conversationService; private final AuthService authService;
  @GetMapping public List<ConversationResponse> mine(Authentication auth){return conversationService.listMine(authService.getByEmail(auth.getName()));}
  @PostMapping public ConversationResponse start(Authentication auth, @RequestParam Long participantId, @RequestParam(required = false) Long postId){return conversationService.startOrGet(authService.getByEmail(auth.getName()), participantId, postId);} 
}
