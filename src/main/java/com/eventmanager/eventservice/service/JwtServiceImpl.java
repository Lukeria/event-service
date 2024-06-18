package com.eventmanager.eventservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eventmanager.eventservice.dao.AuthorityRepository;
import com.eventmanager.eventservice.dao.ParticipantRepository;
import com.eventmanager.eventservice.dao.UserCredentialsRepository;
import com.eventmanager.eventservice.model.Authority;
import com.eventmanager.eventservice.model.Participant;
import com.eventmanager.eventservice.model.UserCredentials;
import com.eventmanager.eventservice.model.UserPrincipal;
import com.eventmanager.eventservice.model.enums.AuthorityName;
import com.eventmanager.eventservice.resources.ApplicationProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service(value = "JwtServiceImpl2")
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final ApplicationProperties applicationProperties;
    private final AuthorityRepository authorityRepository;
    private final ParticipantRepository participantRepository;
    private String secretKey;

    @PostConstruct
    protected void init() {
        // To avoid having the raw secrete key available in JVM;
        secretKey = Base64.getEncoder().encodeToString(applicationProperties.getSecretKey().getBytes());
    }

    @Override
    public Authentication validateAuthToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        UserCredentials userCredentials = userCredentialsRepository.searchByLogin(decodedJWT.getIssuer())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found"));

        return new UsernamePasswordAuthenticationToken(new UserPrincipal(userCredentials),
                userCredentials,
                List.of(userCredentials.getRole()));
    }

    @Override
    public String generateAuthToken(UserPrincipal userDetails) {
        Date now = new Date();
        //Valid withing 8 hours
        Date validity = new Date(now.getTime() + 3_600_000 * 8);
        Optional<? extends GrantedAuthority> authorityOptional = userDetails.getAuthorities()
                .stream()
                .findFirst();

        Authority authority = new Authority(-1L, "anonymous");
        if (authorityOptional.isPresent()) {
            authority = (Authority) authorityOptional.get();
        }

        return JWT.create()
                .withIssuer(userDetails.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("roleId", authority.getId())
                .withClaim("role", authority.getName())
                .sign(Algorithm.HMAC256(secretKey));
    }

    @Override
    public Boolean validateConfirmationToken(String token) {

        UserPrincipal userPrincipal;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserPrincipal){
            userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized");
        }

        if(userPrincipal == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        Participant participant = participantRepository.findByEmailAndUser(decodedJWT.getIssuer(),
                userPrincipal.getUserCredentials())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "This confirmation is not dedicated to the authenticated user"));

        return participant != null;
    }

    @Override
    public String generateConfirmationToken(String email) {
        Date now = new Date();
        //Valid withing 8 hours
        Date validity = new Date(now.getTime() + 3_600_000 * 24);

        Authority participantAuthority = authorityRepository.findByName(AuthorityName.ROLE_USER)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role is not found"));

        return JWT.create()
                .withIssuer(email)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("roleId", participantAuthority.getId())
                .withClaim("role", participantAuthority.getName())
                .sign(Algorithm.HMAC256(secretKey));
    }
}
