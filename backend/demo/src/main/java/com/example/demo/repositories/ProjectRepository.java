package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);
}
