package com.eventmanager.eventservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ChecklistProgressDtoResponse {

    private ChecklistDtoResponse checklist;
    private int valueNow;
    private int valueMax;
}
