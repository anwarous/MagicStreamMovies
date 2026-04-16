package com.covoiturage.security;
import jakarta.servlet.*;import jakarta.servlet.http.*;import lombok.RequiredArgsConstructor;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;import org.springframework.stereotype.Component;import org.springframework.web.filter.OncePerRequestFilter;import java.io.IOException;
@Component @RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil; private final UserDetailsService userDetailsService;
  @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String auth=request.getHeader("Authorization");
    if(auth!=null&&auth.startsWith("Bearer ")){
      String token=auth.substring(7);
      if(jwtUtil.isValid(token)){
        String email=jwtUtil.extractSubject(token);
        var userDetails=userDetailsService.loadUserByUsername(email);
        var authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request,response);
  }
}
