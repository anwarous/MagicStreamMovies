package com.covoiturage.service;
import com.covoiturage.dto.request.MessageRequest;import com.covoiturage.dto.response.MessageResponse;import com.covoiturage.model.*;import com.covoiturage.repository.MessageRepository;import lombok.RequiredArgsConstructor;import org.springframework.data.domain.*;import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class MessageService {
  private final MessageRepository messageRepository; private final ConversationService conversationService;
  public Page<MessageResponse> list(Long conversationId, int page, int size){return messageRepository.findByConversationIdOrderBySentAtAsc(conversationId, PageRequest.of(page,size)).map(this::toResponse);} 
  public MessageResponse create(Long conversationId, User sender, MessageRequest req){Conversation c=conversationService.getEntity(conversationId); Message m=Message.builder().conversation(c).sender(sender).content(req.content()).build(); return toResponse(messageRepository.save(m));}
  private MessageResponse toResponse(Message m){return new MessageResponse(m.getId(),m.getConversation().getId(),m.getSender().getId(),m.getSender().getFirstName(),m.getContent(),m.getSentAt(),m.getIsRead());}
}
