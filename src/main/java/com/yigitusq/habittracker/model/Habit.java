package com.yigitusq.habittracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.function.Predicate;

@Entity
@Data //getHabitName(), setHabitName()
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int streak;
    private boolean completedToday;

    private LocalDate lastCompletedDate;
}
