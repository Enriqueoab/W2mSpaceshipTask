package com.w2m.spaceshiptask.spaceship.service;

import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.spaceship.repository.SpaceshipRepository;
import com.w2m.spaceshiptask.utils.exception.NotExpectedResultException;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.utils.exception.messages.ExceptionMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpaceshipServiceImpl implements SpaceshipService {

    private final SpaceshipRepository spaceshipRepo;

    public SpaceshipServiceImpl(SpaceshipRepository spaceshipRepo) {
        this.spaceshipRepo = spaceshipRepo;
    }


    @Override
    public Spaceship findById(Long id) throws NotFoundException {
        return spaceshipRepo.findById(id).orElseThrow(() -> new NotFoundException(ExceptionMessages.SPACESHIP_NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public HttpStatus removeSpaceship(Long id) throws NotFoundException, NotExpectedResultException {
        var spaceship = findById(id);
        spaceshipRepo.delete(spaceship);
        if (spaceshipRepo.existsById(id)){
            throw new NotExpectedResultException(ExceptionMessages.REMOVE_SPACESHIP_ERROR.getMessage());
        }
        return HttpStatus.OK;
    }

    @Override
    public Page<Spaceship> getAll(Pageable pageable) {
        return spaceshipRepo.findAll(pageable);
    }

    @Override
    @Transactional
    public Spaceship addNewSpaceship(Spaceship spaceship) {
        return spaceshipRepo.save(spaceship);
    }

    @Override
    @Transactional
    public Spaceship updateSpaceship(Long id, Spaceship spaceship) throws NotFoundException {
        var savedSpaceship = findById(id);

        savedSpaceship.setName(spaceship.getName());
        savedSpaceship.setImageUrl(spaceship.getImageUrl());
        return spaceshipRepo.save(spaceship);
    }

    @Override
    public Page<Spaceship> findByName(String name, Pageable pageable) {
        return null;
    }

}
