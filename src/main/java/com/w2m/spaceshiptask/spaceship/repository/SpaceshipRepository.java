package com.w2m.spaceshiptask.spaceship.repository;

import com.w2m.spaceshiptask.spaceship.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

    List<Spaceship> findByNameContainingIgnoreCase(String name);
}
