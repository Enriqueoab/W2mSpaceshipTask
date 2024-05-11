package com.w2m.spaceshiptask.spaceship.repository;

import com.w2m.spaceshiptask.spaceship.Spaceship;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@NonNullApi
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

    List<Spaceship> findByNameContainingIgnoreCase(String name);

    @Cacheable("spaceships")
    Page<Spaceship> findAll(Pageable pageable);

    @Query(value="SELECT s.imageUrl FROM Spaceship s")
    List<String> getAllByImageUrlNotNull();
}
