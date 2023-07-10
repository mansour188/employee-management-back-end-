package com.example.employe.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class Work {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer workId;
    private String title;
    private LocalDateTime startTime;
    private Duration duration;
    private String description;
    @ManyToOne
    private Users employer;


}
