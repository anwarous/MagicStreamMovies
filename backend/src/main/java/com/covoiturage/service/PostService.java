package com.covoiturage.service;
import com.covoiturage.dto.request.PostRequest;import com.covoiturage.dto.response.PostResponse;import com.covoiturage.exception.*;import com.covoiturage.model.*;import com.covoiturage.model.enums.PostStatus;import com.covoiturage.repository.PostRepository;import lombok.RequiredArgsConstructor;import org.springframework.stereotype.Service;import java.util.stream.Collectors;import java.util.*;
@Service @RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  public List<PostResponse> all(){return postRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());}
  public PostResponse one(Long id){return toResponse(postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post with id %d not found".formatted(id))));}
  public PostResponse create(User me, PostRequest req){Post p=Post.builder().type(req.type()).origin(req.origin()).destination(req.destination()).departureDate(req.departureDate()).departureTime(req.departureTime()).seatsAvailable(req.seatsAvailable()).price(req.price()).description(req.description()).author(me).build(); return toResponse(postRepository.save(p));}
  public PostResponse update(User me, Long id, PostRequest req){Post p=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post with id %d not found".formatted(id))); own(me,p); p.setOrigin(req.origin());p.setDestination(req.destination());p.setDepartureDate(req.departureDate());p.setDepartureTime(req.departureTime());p.setSeatsAvailable(req.seatsAvailable());p.setPrice(req.price());p.setDescription(req.description());p.setType(req.type()); return toResponse(postRepository.save(p));}
  public void delete(User me, Long id){Post p=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post with id %d not found".formatted(id))); own(me,p); postRepository.delete(p);} 
  public PostResponse setStatus(User me, Long id, PostStatus status){Post p=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post with id %d not found".formatted(id))); own(me,p); p.setStatus(status); return toResponse(postRepository.save(p));}
  private void own(User me, Post p){if(!p.getAuthor().getId().equals(me.getId())) throw new UnauthorizedException("Not owner of post");}
  private PostResponse toResponse(Post p){return new PostResponse(p.getId(),p.getType(),p.getOrigin(),p.getDestination(),p.getDepartureDate(),p.getDepartureTime(),p.getSeatsAvailable(),p.getPrice(),p.getDescription(),p.getStatus(),p.getAuthor().getId(),p.getAuthor().getFirstName());}
}
