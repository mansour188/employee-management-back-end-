package com.example.employe.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Integer duration;
    private String description;

    @JsonIgnore

    @ToString.Exclude

    @ManyToOne
    private Users employer;


}
