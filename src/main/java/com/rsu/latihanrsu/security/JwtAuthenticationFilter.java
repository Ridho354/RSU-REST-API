package com.rsu.latihanrsu.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rsu.latihanrsu.entity.UserAccount;
import com.rsu.latihanrsu.service.JwtService;
import com.rsu.latihanrsu.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String AUTH_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerTokenFromAuthHeader = request.getHeader(AUTH_HEADER);
            // di cek tokennya apakah signaturenya sesuai dan masih belum expired
            if (bearerTokenFromAuthHeader != null && jwtService.verifyJwtToken(bearerTokenFromAuthHeader)) {
                // decode dari tokennya supaya kita dapat userAccountId
                String userId = jwtService.getUserIdByToken(bearerTokenFromAuthHeader);
                UserAccount userAccount = userService.getByUserId(userId);

                if (userAccount != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAccount.getUsername(), null, userAccount.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
