package com.eventmanager.eventservice.service.strategy;

import com.eventmanager.eventservice.dao.ParticipantRepository;
import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Authority;
import com.eventmanager.eventservice.model.Participant;
import com.eventmanager.eventservice.model.UserCredentials;
import com.eventmanager.eventservice.service.mapper.ParticipantMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.nio.CharBuffer;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipantServiceImpl implements ParticipantService{

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Participant getModelByUser(UserCredentials userCredentials) {
        return participantRepository.findByUser(userCredentials)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant is not found"));
    }

    @Override
    public UserInfoDtoResponse getByUser(UserCredentials userCredentials) {
        Participant participant = getModelByUser(userCredentials);
        return participantMapper.mapToDto(participant);
    }

    @Override
    public UserInfoDtoResponse createUser(UserInfoDtoRequest request, Authority authority) {
        Participant participant = participantMapper.mapToModel(request);
        UserCredentials userCredentials = UserCredentials.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(CharBuffer.wrap(request.getPassword())))
                .role(authority)
                .build();

        participant.setUser(userCredentials);
        participantRepository.save(participant);

        return participantMapper.mapToDto(participant);
    }

    @Override
    public List<UserInfoDtoResponse> getList() {
        return participantRepository.findAll().stream()
                .map(participantMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getEmailList(List<UserCredentials> userCredentialsList) {
        return participantRepository.findByUserIn(userCredentialsList)
                .stream()
                .map(Participant::getEmail)
                .filter(StringUtils::hasLength)
                .collect(Collectors.toList());
    }
}
