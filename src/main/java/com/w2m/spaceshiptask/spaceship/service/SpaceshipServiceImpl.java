package com.w2m.spaceshiptask.spaceship.service;

import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.spaceship.repository.SpaceshipRepository;
import com.w2m.spaceshiptask.utils.exception.EmptyListReturnException;
import com.w2m.spaceshiptask.utils.exception.NotExpectedResultException;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.utils.exception.messages.ExceptionMessages;
import com.w2m.spaceshiptask.utils.form.SpaceshipForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Spaceship addNewSpaceship(SpaceshipForm SpaceshipForm) {
        return spaceshipRepo.save(buildSpaceship(SpaceshipForm));
    }

    @Override
    @Transactional
    public Spaceship updateSpaceship(Long id, SpaceshipForm SpaceshipForm) throws NotFoundException {
        var savedSpaceship = findById(id);

        savedSpaceship.setName(SpaceshipForm.getSpaceshipName() != null ? SpaceshipForm.getSpaceshipName() :
                                                                          savedSpaceship.getName());

        savedSpaceship.setImageUrl(SpaceshipForm.getImageUrl() != null ? SpaceshipForm.getImageUrl() :
                                                                         savedSpaceship.getImageUrl());

        return spaceshipRepo.save(savedSpaceship);
    }

    @Override
    public List<Spaceship> findByName(String name, Pageable pageable) throws EmptyListReturnException {
        var ships = spaceshipRepo.findByNameContainingIgnoreCase(name);
        if (ships.isEmpty()) {
            throw new EmptyListReturnException(ExceptionMessages.REQUEST_RETURN_EMPTY.getMessage());
        }
        return ships;
    }

    private Spaceship buildSpaceship(SpaceshipForm spaceshipForm) {
        var source = new Source(spaceshipForm.getPremiereYear(), spaceshipForm.getSourceName(), spaceshipForm.getType());
        return new Spaceship(spaceshipForm.getSpaceshipName(), source, spaceshipForm.getImageUrl());
    }

}
