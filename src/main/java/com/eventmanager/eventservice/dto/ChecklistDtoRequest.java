package com.eventmanager.eventservice.dto;

import com.eventmanager.eventservice.model.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ChecklistDtoRequest {

    private Long id;
    private String name;
    private String description;
}
