package nl.novi.cannoliworld.filter;


import nl.novi.cannoliworld.service.CustomUserDetailService;
import nl.novi.cannoliworld.utils.JwtUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtUtil;
    private CustomUserDetailService customUserDetailService;

    public JwtRequestFilter() {
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
       final String authorizationHeader = request.getHeader("Authorization");

       String username = null;
       String jwt = null;

       if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
           jwt = authorizationHeader.substring(7);
           username = jwtUtil.extractUsername(jwt);
       }

       if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);

           if (jwtUtil.validateToken(jwt, userDetails)) {

               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                       userDetails, null, userDetails.getAuthorities()

               );
               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
           }
       }
       filterChain.doFilter(request, response);

    }

}


