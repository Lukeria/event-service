package com.eventmanager.eventservice.model;

import com.eventmanager.eventservice.model.enums.Gender;
import com.eventmanager.eventservice.model.enums.RVSPStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "guests")
@NoArgsConstructor
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "rvsp_status")
    @Enumerated(EnumType.STRING)
    private RVSPStatus rvspStatus;
    private String uuid;
    private String email;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
}
