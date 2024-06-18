package com.eventmanager.eventservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ChecklistDtoResponse {

    private Long id;
    private String name;
    private String description;
    private List<TaskDtoResponse> taskList;
}
