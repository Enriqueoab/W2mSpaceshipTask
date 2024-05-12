package com.w2m.spaceshiptask.spaceship.service;

import java.util.List;
import org.mockito.Mock;
import java.util.Optional;
import java.util.ArrayList;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import com.w2m.spaceshiptask.source.Source;
import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import com.w2m.spaceshiptask.source.SourceType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.w2m.spaceshiptask.utils.form.SpaceshipForm;
import com.w2m.spaceshiptask.spaceship.SpaceshipImgDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.spaceship.repository.SpaceshipRepository;
import com.w2m.spaceshiptask.utils.exception.EmptyListReturnException;
import com.w2m.spaceshiptask.utils.exception.NotExpectedResultException;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SpaceshipServiceImplTest {
    @InjectMocks
    private SpaceshipServiceImpl spaceshipServiceImpl;

    @Mock
    private SpaceshipRepository spaceshipRepo;

    @Mock
    private RabbitTemplate rabbitTemplate;

    /**
     * Method under test: {@link SpaceshipServiceImpl#findById(Long)}
     */
    @Test
    void testFindById() throws NotFoundException {

        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        var spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Optional<Spaceship> ofResult = Optional.of(spaceship);
        Mockito.when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var actualFindByIdResult = (new SpaceshipServiceImpl(rabbitTemplate, spaceshipRepo)).findById(1L);

        Mockito.verify(spaceshipRepo).findById(Mockito.<Long>any());
        Assertions.assertSame(spaceship, actualFindByIdResult);
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship() throws NotExpectedResultException, NotFoundException {

        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        var spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Optional<Spaceship> ofResult = Optional.of(spaceship);

        Mockito.when(spaceshipRepo.existsById(Mockito.<Long>any())).thenReturn(true);
        Mockito.doNothing().when(spaceshipRepo).delete(Mockito.<Spaceship>any());
        Mockito.when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Assertions.assertThrows(NotExpectedResultException.class,
                () -> (new SpaceshipServiceImpl(rabbitTemplate, spaceshipRepo)).removeSpaceship(1L));
        Mockito.verify(spaceshipRepo).delete(Mockito.<Spaceship>any());
        Mockito.verify(spaceshipRepo).existsById(Mockito.<Long>any());
        Mockito.verify(spaceshipRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship2() throws NotExpectedResultException, NotFoundException {

        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        var spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Optional<Spaceship> ofResult = Optional.of(spaceship);

        Mockito.when(spaceshipRepo.existsById(Mockito.<Long>any())).thenReturn(false);
        Mockito.doNothing().when(spaceshipRepo).delete(Mockito.<Spaceship>any());
        Mockito.when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var actualRemoveSpaceshipResult = (new SpaceshipServiceImpl(rabbitTemplate, spaceshipRepo))
                .removeSpaceship(1L);

        Mockito.verify(spaceshipRepo).delete(Mockito.<Spaceship>any());
        Mockito.verify(spaceshipRepo).existsById(Mockito.<Long>any());
        Mockito.verify(spaceshipRepo).findById(Mockito.<Long>any());
        Assertions.assertEquals(HttpStatus.OK, actualRemoveSpaceshipResult);
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship3() throws NotExpectedResultException, NotFoundException {

        Optional<Spaceship> emptyResult = Optional.empty();
        Mockito.when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Assertions.assertThrows(NotFoundException.class,
                () -> (new SpaceshipServiceImpl(rabbitTemplate, spaceshipRepo)).removeSpaceship(1L));
        Mockito.verify(spaceshipRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#getAll(Pageable)}
     */
    @Test
    void testGetAll() {

        PageImpl<Spaceship> pageImpl = new PageImpl<>(new ArrayList<>());
        Mockito.when(spaceshipRepo.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        Page<Spaceship> actualAll = (new SpaceshipServiceImpl(rabbitTemplate, spaceshipRepo)).getAll(null);

        Mockito.verify(spaceshipRepo).findAll(Mockito.<Pageable>any());
        Assertions.assertTrue(actualAll.toList().isEmpty());
        Assertions.assertSame(pageImpl, actualAll);
    }



    /**
     * Method under test:
     * {@link SpaceshipServiceImpl#addNewSpaceship(SpaceshipForm)}
     */
    @Test
    void testAddNewSpaceship() {

        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        var spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Mockito.when(spaceshipRepo.save(Mockito.<Spaceship>any())).thenReturn(spaceship);

        var actualAddNewSpaceshipResult = spaceshipServiceImpl.addNewSpaceship(new SpaceshipForm());

        Mockito.verify(spaceshipRepo).save(Mockito.<Spaceship>any());
        Assertions.assertSame(spaceship, actualAddNewSpaceshipResult);
    }

    /**
     * Method under test:
     * {@link SpaceshipServiceImpl#updateSpaceship(Long, SpaceshipForm)}
     */
    @Test
    void testUpdateSpaceship() throws NotFoundException {

        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Optional<Spaceship> ofResult = Optional.of(spaceship);

        Source source2 = new Source();
        source2.setName("Name");
        source2.setPremiereYear(1);
        source2.setType(SourceType.SERIES);

        Spaceship spaceship2 = new Spaceship();
        spaceship2.setImageUrl("https://example.org/example");
        spaceship2.setName("Name");
        spaceship2.setSource(source2);

        Mockito.when(spaceshipRepo.save(Mockito.<Spaceship>any())).thenReturn(spaceship2);
        Mockito.when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        var spaceshipServiceImpl = new SpaceshipServiceImpl(rabbitTemplate, spaceshipRepo);

        var actualUpdateSpaceshipResult = spaceshipServiceImpl.updateSpaceship(1L, new SpaceshipForm());

        Mockito.verify(spaceshipRepo).findById(Mockito.<Long>any());
        Mockito.verify(spaceshipRepo).save(Mockito.<Spaceship>any());
        Assertions.assertSame(spaceship2, actualUpdateSpaceshipResult);
    }

    /**
     * Method under test:
     * {@link SpaceshipServiceImpl#updateSpaceship(Long, SpaceshipForm)}
     */
    @Test
    void testUpdateSpaceship2() throws NotFoundException {

        Optional<Spaceship> emptyResult = Optional.empty();
        Mockito.when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        SpaceshipServiceImpl spaceshipServiceImpl = new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo);

        Assertions.assertThrows(NotFoundException.class, () -> spaceshipServiceImpl.updateSpaceship(1L, new SpaceshipForm()));
        Mockito.verify(spaceshipRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link SpaceshipServiceImpl#updateSpaceship(Long, SpaceshipForm)}
     */
    @Test
    void testUpdateSpaceship3() throws NotFoundException {

        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Optional<Spaceship> ofResult = Optional.of(spaceship);

        Source source2 = new Source();
        source2.setName("Name");
        source2.setPremiereYear(1);
        source2.setType(SourceType.SERIES);

        Spaceship spaceship2 = new Spaceship();
        spaceship2.setImageUrl("https://example.org/example");
        spaceship2.setName("Name");
        spaceship2.setSource(source2);

        Mockito.when(spaceshipRepo.save(Mockito.<Spaceship>any())).thenReturn(spaceship2);
        Mockito.when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        var spaceshipServiceImpl = new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo);
        var SpaceshipForm = Mockito.mock(SpaceshipForm.class);
        Mockito.when(SpaceshipForm.getImageUrl()).thenReturn("https://example.org/example");
        Mockito.when(SpaceshipForm.getSpaceshipName()).thenReturn("Spaceship Name");

        var actualUpdateSpaceshipResult = spaceshipServiceImpl.updateSpaceship(1L, SpaceshipForm);

        Mockito.verify(SpaceshipForm, Mockito.atLeast(1)).getImageUrl();
        Mockito.verify(SpaceshipForm, Mockito.atLeast(1)).getSpaceshipName();
        Mockito.verify(spaceshipRepo).findById(Mockito.<Long>any());
        Mockito.verify(spaceshipRepo).save(Mockito.<Spaceship>any());
        Assertions.assertSame(spaceship2, actualUpdateSpaceshipResult);
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#findByName(String, Pageable)}
     */
    @Test
    void testFindByName() throws EmptyListReturnException {

        Mockito.when(spaceshipRepo.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(new ArrayList<>());

        Assertions.assertThrows(EmptyListReturnException.class,
                () -> (new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo)).findByName("Name", null));
        Mockito.verify(spaceshipRepo).findByNameContainingIgnoreCase(ArgumentMatchers.eq("Name"));
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#findByName(String, Pageable)}
     */
    @Test
    void testFindByName2() throws EmptyListReturnException {

        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        var spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);

        ArrayList<Spaceship> spaceshipList = new ArrayList<>();
        spaceshipList.add(spaceship);

        Mockito.when(spaceshipRepo.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(spaceshipList);

        List<Spaceship> actualFindByNameResult = (new SpaceshipServiceImpl(rabbitTemplate, spaceshipRepo))
                .findByName("Name", null);

        Mockito.verify(spaceshipRepo).findByNameContainingIgnoreCase(ArgumentMatchers.eq("Name"));
        Assertions.assertEquals(1, actualFindByNameResult.size());
        Assertions.assertSame(spaceship, actualFindByNameResult.get(0));
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#showAllSpaceshipsRequest()}
     */
    @Test
    void testShowAllSpaceshipsRequest() throws AmqpException {

        Mockito.doNothing().when(rabbitTemplate)
                .convertAndSend(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any());
        Mockito.when(spaceshipRepo.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        var actualShowAllSpaceshipsRequestResult = spaceshipServiceImpl.showAllSpaceshipsRequest();

        Mockito.verify(spaceshipRepo).findAll(Mockito.<Pageable>any());
        Mockito.verify(rabbitTemplate).convertAndSend(ArgumentMatchers.eq("defaultExchange"),
                ArgumentMatchers.eq("request.to.fetch.spaceship.images.to.send"), Mockito.<Object>any());
        Assertions.assertTrue(actualShowAllSpaceshipsRequestResult.isEmpty());
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#showAllSpaceshipsRequest()}
     */
    @Test
    void testShowAllSpaceshipsRequest2() throws AmqpException {

        Mockito.doNothing().when(rabbitTemplate)
                .convertAndSend(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any());

        var source = new Source();
        source.setName("Star Wars");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        var spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Star fighter");
        spaceship.setSource(source);

        ArrayList<Spaceship> content = new ArrayList<>();
        content.add(spaceship);
        PageImpl<Spaceship> pageImpl = new PageImpl<>(content);
        Mockito.when(spaceshipRepo.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        List<SpaceshipImgDto> actualShowAllSpaceshipsRequestResult = spaceshipServiceImpl.showAllSpaceshipsRequest();

        Mockito.verify(spaceshipRepo).findAll(Mockito.<Pageable>any());
        Mockito.verify(rabbitTemplate).convertAndSend(ArgumentMatchers.eq("defaultExchange"),
                ArgumentMatchers.eq("request.to.fetch.spaceship.images.to.send"), Mockito.<Object>any());
        Assertions.assertEquals(1, actualShowAllSpaceshipsRequestResult.size());
        SpaceshipImgDto getResult = actualShowAllSpaceshipsRequestResult.get(0);
        Assertions.assertEquals(spaceship.getName(), getResult.getName());
        Assertions.assertEquals(spaceship.getImageUrl(), getResult.getImageUrl());
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#showAllSpaceshipsRequest()}
     */
    @Test
    void testShowAllSpaceshipsRequest3() throws AmqpException {

        Mockito.doNothing().when(rabbitTemplate)
                .convertAndSend(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any());

        var source = new Source();
        source.setName("Star Wars2");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        var spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("x-wing");
        spaceship.setSource(source);

        var source2 = new Source();
        source2.setName("Star Wars2");
        source2.setPremiereYear(0);
        source2.setType(SourceType.FILM);

        var spaceship2 = new Spaceship();
        spaceship2.setImageUrl("https://example2.org/example2");
        spaceship2.setName("x-wing2");
        spaceship2.setSource(source2);

        ArrayList<Spaceship> content = new ArrayList<>();
        content.add(spaceship2);
        content.add(spaceship);
        PageImpl<Spaceship> pageImpl = new PageImpl<>(content);
        Mockito.when(spaceshipRepo.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        var actualShowAllSpaceshipsRequestResult = spaceshipServiceImpl.showAllSpaceshipsRequest();

        Mockito.verify(spaceshipRepo).findAll(Mockito.<Pageable>any());
        Mockito.verify(rabbitTemplate).convertAndSend(ArgumentMatchers.eq("defaultExchange"),
                ArgumentMatchers.eq("request.to.fetch.spaceship.images.to.send"), Mockito.<Object>any());
        Assertions.assertEquals(2, actualShowAllSpaceshipsRequestResult.size());
        var getResult = actualShowAllSpaceshipsRequestResult.get(0);
        Assertions.assertEquals(spaceship2.getImageUrl(), getResult.getImageUrl());
        var getResult2 = actualShowAllSpaceshipsRequestResult.get(1);
        Assertions.assertEquals(spaceship.getName(), getResult2.getName());
        Assertions.assertEquals(spaceship.getImageUrl(), getResult2.getImageUrl());
        Assertions.assertEquals(spaceship2.getName(), getResult.getName());
    }

}
