package com.yigitusq.habittracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.time.DayOfWeek;

@Entity
@Data
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int streak;
    private boolean completedToday;
    private LocalDate lastCompletedDate;


    @Column(nullable = false, columnDefinition = "integer default 1")
    private int level = 1;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int xp = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "habit_badges", joinColumns = @JoinColumn(name = "habit_id"))
    @Column(name = "badge")
    private Set<String> badges = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "habit_logs", joinColumns = @JoinColumn(name = "habit_id"))
    @Column(name = "completed_date")
    private Set<LocalDate> history = new HashSet<>();

    // Alışkanlık sıklığı: DAILY (Her Gün) veya SPECIFIC_DAYS (Belirli Günler)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'DAILY'")
    private String frequency = "DAILY";

    // Eğer SPECIFIC_DAYS seçildiyse hangi günler yapılacak? (Örn: MONDAY, WEDNESDAY)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "habit_target_days", joinColumns = @JoinColumn(name = "habit_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private Set<DayOfWeek> targetDays = new HashSet<>();
}