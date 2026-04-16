package com.covoiturage.controller;
import com.covoiturage.dto.request.MessageRequest;import com.covoiturage.dto.response.MessageResponse;import com.covoiturage.service.*;import lombok.RequiredArgsConstructor;import org.springframework.data.domain.Page;import org.springframework.messaging.handler.annotation.*;import org.springframework.messaging.simp.SimpMessagingTemplate;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;
@RestController @RequiredArgsConstructor
public class MessageController {
  private final MessageService messageService; private final AuthService authService; private final SimpMessagingTemplate messaging;
  @GetMapping("/api/conversations/{id}/messages")
  public Page<MessageResponse> list(@PathVariable Long id,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){return messageService.list(id,page,size);} 
  @MessageMapping("/chat/{conversationId}")
  public void send(@DestinationVariable Long conversationId, MessageRequest request, Authentication auth){
    var saved=messageService.create(conversationId, authService.getByEmail(auth.getName()), request);
    messaging.convertAndSend("/topic/conversation/"+conversationId, saved);
  }
}
