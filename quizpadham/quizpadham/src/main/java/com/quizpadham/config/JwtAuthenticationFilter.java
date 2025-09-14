package com.quizpadham.config;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String header = request.getHeader("Authorization");
        String token = null;
        String username=null;

        if(header != null && header.startsWith("Bearer")){
            token=header.substring(7); //remove Bearer
            try{
                username= jwtUtils.getUsernameFromJwt(token);
            }
            catch (Exception e){
                System.out.println("Cannot get username from token: " +e.getMessage());
            }
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            if(jwtUtils.validateJwtToken(token)){
                //extract roles from token
                String role= jwtUtils.getRoleFromJwt(token);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,null, List.of(new SimpleGrantedAuthority(role))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
