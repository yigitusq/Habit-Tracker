package com.yigitusq.habittracker.service;

import com.yigitusq.habittracker.dto.HabitCompletionResponse;
import com.yigitusq.habittracker.model.Habit;
import com.yigitusq.habittracker.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Habit saveHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public Habit getHabitById(Long id) {
        return habitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Alışkanlık bulunamadı! ID: " + id
                ));
    }

    public void deleteHabit(Long id) {
        habitRepository.deleteById(id);
    }

    public Habit updateHabit(Long id, Habit habitDetails) {
        Habit existingHabit = getHabitById(id);

        existingHabit.setName(habitDetails.getName());
        existingHabit.setDescription(habitDetails.getDescription());

        return habitRepository.save(existingHabit);
    }

    // HabitService.java içine QuoteService'i enjekte et
    private final QuoteService quoteService;

    public HabitCompletionResponse completeHabit(Long id) {
        Habit habit = getHabitById(id);
        LocalDate today = LocalDate.now();

        // Bugün zaten yapıldıysa tekrar artırma
        if (habit.getLastCompletedDate() != null && habit.getLastCompletedDate().isEqual(today)) {
            return new HabitCompletionResponse(habit, "Bugünlük hedefine zaten ulaştın!");
        }

        habit.setStreak(habit.getStreak() + 1);
        habit.setLastCompletedDate(today);
        habit.setCompletedToday(true);
        habitRepository.save(habit);

        String quote = quoteService.getRandomQuote();
        return new HabitCompletionResponse(habit, quote);
    }
}