package com.eventmanager.eventservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Объект передачи данных (DTO) для передачи данных о приглашаемом мероприятии от клиента к серверу.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@NoArgsConstructor
@Getter
@Setter
public class ParticipantInvitationDtoRequest {

    /**
     * Email, на который будет отправлено приглашение для участия в мероприятии
     */
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email must no be blank")
    private String email;

    /**
     * Ссылка на страницу с подтверждением приглашения для участия в мероприятии
     */
    @NotNull(message = "Confirmation link mist not be null")
    private String participantConfirmationLink;
}
