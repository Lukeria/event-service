package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для доступа к данным о мероприятиях в базе данных.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Находит мероприятие по его UUID.
     *
     * @param uuid UUID мероприятия.
     * @return Optional объект типа Event, содержащий информацию о мероприятии.
     */
    Optional<Event> findByUuid(String uuid);

    /**
     * Находит мероприятие по его UUID для переданного пользователя.
     *
     * @param uuid            UUID мероприятия, которое нужно найти.
     * @param userCredentials учетные данные пользователя.
     * @return {@link Optional}, содержащий найденное мероприятие, если оно существует,
     * или {@link Optional#empty()}, если такое мероприятие не найдено.
     */
    Optional<Event> findByUuidAndUserCredentialsListContains(String uuid, UserCredentials userCredentials);

    /**
     * Находит все мероприятия для переданного пользователя.
     *
     * @param userCredentials учетные данные пользователя.
     * @return список мероприятий, ассоциированных с предоставленными учетными данными пользователя
     */
    List<Event> findAllByUserCredentialsListContains(UserCredentials userCredentials);

    /**
     * Находит мероприятие по его UUID для переданного пользователя.
     *
     * @param id            id мероприятия, которое нужно найти.
     * @param userCredentials учетные данные пользователя.
     * @return {@link Optional}, содержащий найденное мероприятие, если оно существует,
     * или {@link Optional#empty()}, если такое мероприятие не найдено.
     */
    Optional<Event> findByIdAndUserCredentialsListContains(Long id, UserCredentials userCredentials);
}
