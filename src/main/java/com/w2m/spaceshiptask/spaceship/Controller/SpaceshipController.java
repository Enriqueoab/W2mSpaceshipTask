package com.w2m.spaceshiptask.spaceship.Controller;

import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.spaceship.service.SpaceshipService;
import com.w2m.spaceshiptask.utils.exception.NotExpectedResultException;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

@Controller
public class SpaceshipController {

    private final SpaceshipService spaceshipService;

    public SpaceshipController(SpaceshipService spaceshipService) {
        this.spaceshipService = spaceshipService;
    }

    public Page<Spaceship> getAll(Pageable pageable) {
        return spaceshipService.getAll(pageable);
    }

    public Spaceship findById(Long id) throws NotFoundException {
        return spaceshipService.findById(id);
    }

    public HttpStatus removeSpaceship(Long id) throws NotExpectedResultException, NotFoundException {
        return spaceshipService.removeSpaceship(id);
    }

    public Page<Spaceship> findByName(String name, Pageable pageable) {
        return spaceshipService.findByName(name, pageable);
    }

    public Spaceship addNewSpaceship(Spaceship spaceship) {
        return spaceshipService.addNewSpaceship(spaceship);
    }

    public Spaceship updateSpaceship(Long id, Spaceship spaceship) throws NotFoundException {
        return spaceshipService.updateSpaceship(id, spaceship);
    }

}
