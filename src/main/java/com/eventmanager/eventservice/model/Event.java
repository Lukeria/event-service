package com.eventmanager.eventservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель сущности "Мероприятие".
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String uuid;
    private String place;

    @ManyToOne
    @JoinColumn(name = "event_type_id", referencedColumnName = "id")
    private EventType type;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "budget_id", referencedColumnName = "id")
    private Budget budget;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "events_user_credentials",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserCredentials> userCredentialsList = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Checklist> checklistList;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guestList;

    @OneToOne (mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Invitation invitation;
}
