package com.w2m.spaceshiptask.spaceship.service;

import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.utils.exception.EmptyListReturnException;
import com.w2m.spaceshiptask.utils.exception.NotExpectedResultException;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.utils.form.SpaceshipForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpaceshipService {

    Spaceship findById(Long id) throws NotFoundException;

    HttpStatus removeSpaceship(Long id) throws NotFoundException, NotExpectedResultException;

    Page<Spaceship> getAll(Pageable pageable);

    Spaceship addNewSpaceship(SpaceshipForm SpaceshipForm);

    Spaceship updateSpaceship(Long id, SpaceshipForm SpaceshipForm) throws NotFoundException;

    List<Spaceship> findByName(String name, Pageable pageable) throws EmptyListReturnException;

    List<String> showAllSpaceshipsRequest();
}
