package com.covoiturage.service;
import com.covoiturage.dto.response.ConversationResponse;import com.covoiturage.exception.ResourceNotFoundException;import com.covoiturage.model.*;import com.covoiturage.repository.*;import lombok.RequiredArgsConstructor;import org.springframework.stereotype.Service;import java.util.*;import java.util.stream.Collectors;
@Service @RequiredArgsConstructor
public class ConversationService {
  private final ConversationRepository conversationRepository; private final UserRepository userRepository; private final PostRepository postRepository;
  public List<ConversationResponse> listMine(User me){return conversationRepository.findAll().stream().filter(c->c.getParticipants().stream().anyMatch(u->u.getId().equals(me.getId()))).map(this::toResponse).collect(Collectors.toList());}
  public ConversationResponse startOrGet(User me, Long participantId, Long postId){
    var conv=conversationRepository.findByParticipants(me.getId(),participantId).orElseGet(()->{
      User p=userRepository.findById(participantId).orElseThrow(()->new ResourceNotFoundException("Participant not found"));
      Conversation c=new Conversation(); c.setParticipants(new ArrayList<>(List.of(me,p)));
      if(postId!=null){Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));c.setPost(post);} 
      return conversationRepository.save(c);
    });
    return toResponse(conv);
  }
  public Conversation getEntity(Long id){return conversationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Conversation not found"));}
  public ConversationResponse toResponse(Conversation c){return new ConversationResponse(c.getId(), c.getParticipants().stream().map(User::getId).toList(), c.getPost()==null?null:c.getPost().getId(), c.getCreatedAt());}
}
