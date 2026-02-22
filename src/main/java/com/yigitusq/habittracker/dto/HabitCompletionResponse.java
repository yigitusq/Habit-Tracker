package com.yigitusq.habittracker.dto;

import com.yigitusq.habittracker.model.Habit;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class HabitCompletionResponse {
    private Habit habit;
    private String motivationMessage;
    private boolean leveledUp;
    private List<String> newBadges;
}