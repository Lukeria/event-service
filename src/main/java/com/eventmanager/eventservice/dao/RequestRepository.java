package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Request;
import com.eventmanager.eventservice.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с запросами на мероприятия.
 * Расширяет функциональность JpaRepository для работы с сущностью Request.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
public interface RequestRepository extends JpaRepository<Request, Long> {

    /**
     * Находит все запросы, связанные с указанным пользователем.
     *
     * @param userCredentials Учетные данные пользователя, по которым осуществляется поиск запросов.
     * @return Список запросов, связанных с указанным пользователем.
     */
    List<Request> findAllByUserCredentialsListContains(UserCredentials userCredentials);

    /**
     * Находит запрос по его идентификатору и учетным данным пользователя.
     *
     * @param id Идентификатор запроса.
     * @param userCredentials Учетные данные пользователя, по которым осуществляется поиск запроса.
     * @return Опционально возвращает запрос, если он найден, иначе возвращает пустой Optional.
     */
    Optional<Request> findByIdAndUserCredentialsListContains(Long id, UserCredentials userCredentials);
}
