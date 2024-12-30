package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Id(Long userId);
    List<Task> findByCompletedTrue();
    List<Task> findByCompletedFalse();
    Optional<Task> findById(Long id);
    List<Task> findByTitle(String title);
    void deleteById(Long id);
}
