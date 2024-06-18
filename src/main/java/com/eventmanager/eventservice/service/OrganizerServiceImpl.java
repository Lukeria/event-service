package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.OrganizerRepository;
import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Authority;
import com.eventmanager.eventservice.model.Organizer;
import com.eventmanager.eventservice.model.UserCredentials;
import com.eventmanager.eventservice.service.mapper.OrganizerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.CharBuffer;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final OrganizerMapper organizerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Organizer getModelById(Long id) {
        return organizerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizer is not found"));
    }

    @Override
    public List<UserInfoDtoResponse> getList() {
        return organizerRepository.findAll().stream()
                .map(organizerMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserInfoDtoResponse getByUser(UserCredentials userCredentials) {
        Organizer organizer = organizerRepository.findByUser(userCredentials)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizer is not found"));

        return organizerMapper.mapToDto(organizer);
    }

    @Override
    public UserInfoDtoResponse createUser(UserInfoDtoRequest request, Authority authority) {
        Organizer organizer = organizerMapper.mapToModel(request);
        UserCredentials userCredentials = UserCredentials.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(CharBuffer.wrap(request.getPassword())))
                .role(authority)
                .build();

        organizer.setUser(userCredentials);
        organizerRepository.save(organizer);

        return organizerMapper.mapToDto(organizer);
    }
}
