package com.yigitusq.habittracker.controller;

import com.yigitusq.habittracker.dto.HabitCompletionResponse;
import com.yigitusq.habittracker.model.Habit;
import com.yigitusq.habittracker.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController // Bu sınıfın bir REST API olduğunu belirtir.
@RequestMapping("/api/habits") // Bu sınıftaki tüm metodlar bu adresle başlar.
@RequiredArgsConstructor // Service katmanını otomatik enjekte eder.
public class HabitController {

    private final HabitService habitService;

    // Tüm alışkanlıkları listele: GET http://localhost:8080/api/habits
    @GetMapping
    public List<Habit> getAllHabits() {
        return habitService.getAllHabits();
    }

    // Yeni alışkanlık ekle: POST http://localhost:8080/api/habits
    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {
        return habitService.saveHabit(habit);
    }

    // GET http://localhost:8080/api/habits/5
    @GetMapping("/{id}")
    public Habit getHabitById(@PathVariable Long id) {
        // habitService içinde bu metodu yazman gerekecek
        return habitService.getHabitById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
    }

    @PutMapping("/{id}")
    public Habit updateHabit(@PathVariable Long id, @RequestBody Habit habitDetails) {
        return habitService.updateHabit(id, habitDetails);
    }

    // POST http://localhost:8080/api/habits/5/complete
    @PostMapping("/{id}/complete")
    public HabitCompletionResponse completeHabit(@PathVariable Long id) {
        return habitService.completeHabit(id);
    }

}