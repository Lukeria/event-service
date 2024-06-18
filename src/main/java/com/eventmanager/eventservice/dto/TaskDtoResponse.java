package com.eventmanager.eventservice.dto;

import com.eventmanager.eventservice.model.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TaskDtoResponse {

    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private ZonedDateTime deadline;
    private Long checklistId;
}
