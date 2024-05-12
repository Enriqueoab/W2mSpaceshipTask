package com.w2m.spaceshiptask.spaceship.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import com.w2m.spaceshiptask.source.Source;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.utils.form.SpaceshipForm;
import com.w2m.spaceshiptask.config.rabbit.CoreConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.w2m.spaceshiptask.spaceship.repository.SpaceshipRepository;
import com.w2m.spaceshiptask.utils.exception.EmptyListReturnException;
import com.w2m.spaceshiptask.utils.exception.NotExpectedResultException;
import com.w2m.spaceshiptask.utils.exception.messages.ExceptionMessages;

@Service
public class SpaceshipServiceImpl implements SpaceshipService {

    private final SpaceshipRepository spaceshipRepo;
    private final RabbitTemplate rabbitTemplate;

    public SpaceshipServiceImpl(RabbitTemplate rabbitTemplate, SpaceshipRepository spaceshipRepo) {
        this.rabbitTemplate = rabbitTemplate;
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
    public List<String> showAllSpaceshipsRequest() {
//        TODO: Create @Aspect to send a log saying request sent
        var urls = spaceshipRepo.getAllByImageUrlNotNull();

        rabbitTemplate.convertAndSend(CoreConstants.RABBIT_EXCHANGE_NAME,
                CoreConstants.RABBIT_SHOW_SPACESHIP_REQUEST_ROUTING_KEY, urls);

        return urls;
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
