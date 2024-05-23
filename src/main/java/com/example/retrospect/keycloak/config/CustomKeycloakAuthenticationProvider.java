package com.example.retrospect.keycloak.config;

import java.util.*;

import com.example.retrospect.user.entity.UserEntity;
import com.example.retrospect.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class CustomKeycloakAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomKeycloakAuthenticationProvider.class);

    private final ApplicationContext appCtx;
    private final Jwt2AuthenticationConverter jwt2AuthenticationConverter;
    private final JwtDecoder jwtDecoder;

    public CustomKeycloakAuthenticationProvider(ApplicationContext appCtx,
                                                Jwt2AuthenticationConverter jwt2AuthenticationConverter, JwtDecoder jwtDecoder) {
        this.appCtx = appCtx;
        this.jwt2AuthenticationConverter = jwt2AuthenticationConverter;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
        Jwt jwt = getJwt(bearer);
        JwtAuthenticationToken token = this.jwt2AuthenticationConverter.convert(jwt);
        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }

    private Jwt getJwt(BearerTokenAuthenticationToken bearer) {
        try {
            return this.jwtDecoder.decode(bearer.getToken());
        } catch (BadJwtException failed) {
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        } catch (JwtException failed) {
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public String getToken(Object userPrincipal) {
        String accessToken = null;
        if (userPrincipal instanceof Jwt) {
            LOGGER.debug("Inside Keycloak Security Context principal");
            Jwt customUserData = (Jwt) userPrincipal;
            if (customUserData != null)
                accessToken = customUserData.getTokenValue();
        }
        return accessToken;
    }
}
