package com.eventmanager.eventservice.model;

import com.eventmanager.eventservice.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая запрос на мероприятие.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@Entity
@Table(name = "requests")
@NoArgsConstructor
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_time")
    private LocalTime eventTime;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(name = "event_place")
    private String eventPlace;

    @ManyToOne
    @JoinColumn(name = "event_type_id", referencedColumnName = "id")
    private EventType type;

    @ManyToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "participant_id", referencedColumnName = "id")
    private Participant participant;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "requests_user_credentials",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserCredentials> userCredentialsList = new ArrayList<>();
}
