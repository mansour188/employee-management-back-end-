package com.example.employe.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workdto {
    private Integer userId;
    private Integer workId;
    private String title;
    private LocalDateTime startTime;
    private Integer duration;
    private String description;

}
