package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
