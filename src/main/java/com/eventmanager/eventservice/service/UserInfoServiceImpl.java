package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.AuthorityRepository;
import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Authority;
import com.eventmanager.eventservice.model.UserCredentials;
import com.eventmanager.eventservice.model.enums.AuthorityName;
import com.eventmanager.eventservice.service.api.UserInfoAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoAPIService {

    private final OrganizerService organizerService;
    private final ParticipantService participantService;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserInfoDtoResponse createUser(UserInfoDtoRequest request) {

        Authority authority = authorityRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role is not found"));

        if (AuthorityName.ROLE_ORGANIZER.equals(request.getRoleName())) {
            return organizerService.createUser(request, authority);
        } else if (AuthorityName.ROLE_USER.equals(request.getRoleName())) {
            return participantService.createUser(request, authority);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user role");
    }

    @Override
    public List<UserInfoDtoResponse> getOrganizersList() {
        return organizerService.getList();
    }

    @Override
    public UserInfoDtoResponse getByUserCredentials(UserCredentials userCredentials) {
        if (userCredentials.getRole() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        if (AuthorityName.ROLE_ORGANIZER.equals(userCredentials.getRole().getName())) {
            return organizerService.getByUser(userCredentials);
        } else if (AuthorityName.ROLE_USER.equals(userCredentials.getRole().getName())) {
            return participantService.getByUser(userCredentials);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found");
    }
}
