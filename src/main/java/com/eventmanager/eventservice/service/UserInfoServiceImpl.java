package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.AuthorityRepository;
import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Authority;
import com.eventmanager.eventservice.model.UserCredentials;
import com.eventmanager.eventservice.model.enums.AuthorityName;
import com.eventmanager.eventservice.service.strategy.UserInfoStrategy;
import com.eventmanager.eventservice.service.strategy.UserInfoStrategyContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final AuthorityRepository authorityRepository;
    private final UserInfoStrategyContext strategyContext;

    @Override
    public UserInfoDtoResponse createUser(UserInfoDtoRequest request) {

        Authority authority = authorityRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role is not found"));

        UserInfoStrategy userInfoStrategy = strategyContext.getStrategy(request.getRoleName());
        return userInfoStrategy.createUser(request, authority);
    }

    @Override
    public List<UserInfoDtoResponse> getOrganizersList() {
        UserInfoStrategy userInfoStrategy = strategyContext.getStrategy(AuthorityName.ROLE_ORGANIZER);
        return userInfoStrategy.getList();
    }

    @Override
    public UserInfoDtoResponse getByUserCredentials(UserCredentials userCredentials) {
        if (userCredentials.getRole() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        UserInfoStrategy userInfoStrategy = strategyContext.getStrategy(userCredentials.getRole().getName());
        return userInfoStrategy.getByUser(userCredentials);
    }

    @Override
    public List<String> getEmailList(List<UserCredentials> userCredentialsList) {

        List<String> emailList = new ArrayList<>();

        for (UserInfoStrategy strategy : strategyContext.getStrategyList()) {
            emailList.addAll(strategy.getEmailList(userCredentialsList));
        }

        return emailList;
    }
}
