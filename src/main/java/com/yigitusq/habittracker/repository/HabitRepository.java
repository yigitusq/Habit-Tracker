package com.yigitusq.habittracker.repository;

import com.yigitusq.habittracker.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> { //save(), findAll(), deleteById()

}