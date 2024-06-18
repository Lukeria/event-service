package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.UserCredentialsRepository;
import com.eventmanager.eventservice.dto.AuthDtoRequest;
import com.eventmanager.eventservice.dto.AuthDtoResponse;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.UserCredentials;
import com.eventmanager.eventservice.model.UserPrincipal;
import com.eventmanager.eventservice.service.api.UserInfoAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService, AuthenticationService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoAPIService userInfoAPIService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> userCredentialsOptional = userCredentialsRepository.searchByLogin(username);

        return new UserPrincipal(userCredentialsOptional.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found")));
    }

    @Override
    public AuthDtoResponse authenticate(AuthDtoRequest request) {
        UserPrincipal userPrincipal = (UserPrincipal) loadUserByUsername(request.getLogin());

//      To overwrite the password data in memory as soon as it is no longer needed,
//      reducing the window of time during which the password might be exposed to an attack
        if (!passwordEncoder.matches(CharBuffer.wrap(request.getPassword()), userPrincipal.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }

        String jwtToken = jwtService.generateAuthToken(userPrincipal);

        AuthDtoResponse authDtoResponse = new AuthDtoResponse();
        UserInfoDtoResponse userInfoDtoResponse = userInfoAPIService
                .getByUserCredentials(userPrincipal.getUserCredentials());
        authDtoResponse.setToken(jwtToken);
        authDtoResponse.setUserInfo(userInfoDtoResponse);

        return authDtoResponse;
    }

    @Override
    public UserCredentials getAuthenticatedUser() {
        UserPrincipal userPrincipal;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserPrincipal) {
            userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized");
        }

        if (userPrincipal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized");
        }

        return userPrincipal.getUserCredentials();
    }
}
