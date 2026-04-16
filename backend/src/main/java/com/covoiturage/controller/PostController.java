package com.covoiturage.controller;
import com.covoiturage.dto.request.PostRequest;import com.covoiturage.dto.response.PostResponse;import com.covoiturage.model.enums.PostStatus;import com.covoiturage.service.*;import lombok.RequiredArgsConstructor;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/posts") @RequiredArgsConstructor
public class PostController {
  private final PostService postService; private final AuthService authService;
  @GetMapping public List<PostResponse> all(){return postService.all();}
  @GetMapping("/{id}") public PostResponse one(@PathVariable Long id){return postService.one(id);} 
  @PostMapping public PostResponse create(@RequestBody PostRequest req, Authentication auth){return postService.create(authService.getByEmail(auth.getName()), req);} 
  @PutMapping("/{id}") public PostResponse update(@PathVariable Long id,@RequestBody PostRequest req, Authentication auth){return postService.update(authService.getByEmail(auth.getName()), id, req);} 
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id, Authentication auth){postService.delete(authService.getByEmail(auth.getName()), id);} 
  @PatchMapping("/{id}/status") public PostResponse status(@PathVariable Long id, @RequestParam PostStatus status, Authentication auth){return postService.setStatus(authService.getByEmail(auth.getName()), id, status);} 
}
