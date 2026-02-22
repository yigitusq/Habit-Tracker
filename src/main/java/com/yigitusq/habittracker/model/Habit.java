package com.yigitusq.habittracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private int streak; // Zincir sayısı
    private boolean completedToday; // Bugün yapıldı mı?

    private LocalDate lastCompletedDate; // Son yapılma tarihi

    @ElementCollection
    @CollectionTable(name = "habit_logs", joinColumns = @JoinColumn(name = "habit_id"))
    @Column(name = "completed_date")
    private Set<LocalDate> history = new HashSet<>();
}