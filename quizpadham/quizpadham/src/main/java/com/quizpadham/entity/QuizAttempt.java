package com.quizpadham.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="quiz_attempts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private int score;
    private int total;
    private String difficulty;

    private LocalDateTime createdAt = LocalDateTime.now();
}
