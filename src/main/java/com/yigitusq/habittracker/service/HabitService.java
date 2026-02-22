package com.yigitusq.habittracker.service;

import com.yigitusq.habittracker.dto.HabitCompletionResponse;
import com.yigitusq.habittracker.model.Habit;
import com.yigitusq.habittracker.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.DayOfWeek;


import java.time.LocalDate;
import java.util.HashSet;
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

    private final QuoteService quoteService;
    public HabitCompletionResponse completeHabit(Long id) {
        Habit habit = getHabitById(id);
        LocalDate today = LocalDate.now();

        if (habit.getLastCompletedDate() != null && habit.getLastCompletedDate().isEqual(today)) {
            return new HabitCompletionResponse(habit, "Bugünlük hedefine zaten ulaştın!", false, null);
        }

        boolean streakMaintained = false;

        if (habit.getLastCompletedDate() == null) {
        }
        else if ("DAILY".equals(habit.getFrequency()) || habit.getFrequency() == null) {
            if (habit.getLastCompletedDate().isEqual(today.minusDays(1))) {
                streakMaintained = true;
            }
        }
        else if ("SPECIFIC_DAYS".equals(habit.getFrequency()) && habit.getTargetDays() != null && !habit.getTargetDays().isEmpty()) {
            LocalDate previousTargetDate = today.minusDays(1);

            while (!habit.getTargetDays().contains(previousTargetDate.getDayOfWeek())) {
                previousTargetDate = previousTargetDate.minusDays(1);
            }

            if (!habit.getLastCompletedDate().isBefore(previousTargetDate)) {
                streakMaintained = true;
            }
        }

        if (streakMaintained) {
            habit.setStreak(habit.getStreak() + 1);
        } else {
            habit.setStreak(1); // Zincir kırıldıysa veya ilk günse 1 yap
        }

        habit.setLastCompletedDate(today);
        habit.setCompletedToday(true);

        if (habit.getHistory() == null) habit.setHistory(new HashSet<>());
        habit.getHistory().add(today);

        boolean leveledUp = false;
        habit.setXp(habit.getXp() + 20);

        int xpNeededForNextLevel = habit.getLevel() * 100;
        if (habit.getXp() >= xpNeededForNextLevel) {
            habit.setXp(habit.getXp() - xpNeededForNextLevel);
            habit.setLevel(habit.getLevel() + 1);
            leveledUp = true;
        }

        java.util.List<String> newBadges = new java.util.ArrayList<>();
        if (habit.getBadges() == null) habit.setBadges(new HashSet<>());

        if (habit.getStreak() == 3 && !habit.getBadges().contains("3 Günlük Isınma 🔥")) {
            habit.getBadges().add("3 Günlük Isınma 🔥");
            newBadges.add("3 Günlük Isınma 🔥");
        }
        if (habit.getStreak() == 7 && !habit.getBadges().contains("1 Haftalık Seri 🥉")) {
            habit.getBadges().add("1 Haftalık Seri 🥉");
            newBadges.add("1 Haftalık Seri 🥉");
        }
        if (habit.getStreak() == 21 && !habit.getBadges().contains("Alışkanlık Avcısı 🏆")) {
            habit.getBadges().add("Alışkanlık Avcısı 🏆");
            newBadges.add("Alışkanlık Avcısı 🏆");
        }

        habitRepository.save(habit);

        String quote = quoteService.getRandomQuote();
        return new HabitCompletionResponse(habit, quote, leveledUp, newBadges);
    }
}