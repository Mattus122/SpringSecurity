package com.example.AuthenticationProject.filter;
import com.example.AuthenticationProject.service.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request
            , @Nonnull HttpServletResponse response
            ,@Nonnull FilterChain filterChain) throws ServletException, IOException {
        //verify wether request has authorization, and it has Bearer in it
        final String authHeader = request.getHeader("Authorization");
         final String jwt;
         final String email;
        if (authHeader == null|| !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        //Extract Jwt from Authorization
        jwt = authHeader.substring(7);
        email = jwtService.extractUserName(jwt);
        if(email != null &&SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("validation happening");
            //verify wether the user is present in database
            // verify wether the token is valid
            // if valid set to security context holder
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );
            logger.info(userDetails.getAuthorities());
            //letting the rest of the application know who is the current user:
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request,response);




        }





    }
    private static final List<String> EXCLUDE_URL = Arrays.asList("/application/v1/auth/*", "/application/v1/auth/swagger-ui/index.html#");
    @Override
    protected boolean shouldNotFilter(@Nonnull HttpServletRequest request) throws ServletException {
//        return request.getServletPath().contains("/application/v1/auth/**/swagger-ui/index.html#") ;
        return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }
//watch video lecture of crack it and see how security has been implemented then verify how you have implemented.
    //Just Make the Api work of nivas that is important .
    //fill the tickets to completion .
    //take break in between do the ticket work this will improve the knowledge.

}
