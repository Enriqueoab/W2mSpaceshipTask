package com.w2m.spaceshiptask.spaceship.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.source.SourceType;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.spaceship.repository.SpaceshipRepository;
import com.w2m.spaceshiptask.utils.exception.EmptyListReturnException;
import com.w2m.spaceshiptask.utils.exception.NotExpectedResultException;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.utils.form.SpaceshipForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SpaceshipServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SpaceshipServiceImplTest {
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private SpaceshipServiceImpl spaceshipServiceImpl;

    /**
     * Method under test: {@link SpaceshipServiceImpl#findById(Long)}
     */
    @Test
    void testFindById() throws NotFoundException {

        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Optional<Spaceship> ofResult = Optional.of(spaceship);
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Spaceship actualFindByIdResult = (new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo)).findById(1L);

        // Assert
        verify(spaceshipRepo).findById(Mockito.<Long>any());
        assertSame(spaceship, actualFindByIdResult);
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship() throws NotExpectedResultException, NotFoundException {

        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Optional<Spaceship> ofResult = Optional.of(spaceship);
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        when(spaceshipRepo.existsById(Mockito.<Long>any())).thenReturn(true);
        doNothing().when(spaceshipRepo).delete(Mockito.<Spaceship>any());
        when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(NotExpectedResultException.class,
                () -> (new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo)).removeSpaceship(1L));
        verify(spaceshipRepo).delete(Mockito.<Spaceship>any());
        verify(spaceshipRepo).existsById(Mockito.<Long>any());
        verify(spaceshipRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship2() throws NotExpectedResultException, NotFoundException {

        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Optional<Spaceship> ofResult = Optional.of(spaceship);
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        when(spaceshipRepo.existsById(Mockito.<Long>any())).thenReturn(false);
        doNothing().when(spaceshipRepo).delete(Mockito.<Spaceship>any());
        when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        HttpStatus actualRemoveSpaceshipResult = (new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo))
                .removeSpaceship(1L);

        // Assert
        verify(spaceshipRepo).delete(Mockito.<Spaceship>any());
        verify(spaceshipRepo).existsById(Mockito.<Long>any());
        verify(spaceshipRepo).findById(Mockito.<Long>any());
        assertEquals(HttpStatus.OK, actualRemoveSpaceshipResult);
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship3() throws NotExpectedResultException, NotFoundException {

        // Arrange
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        Optional<Spaceship> emptyResult = Optional.empty();
        when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NotFoundException.class,
                () -> (new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo)).removeSpaceship(1L));
        verify(spaceshipRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#getAll(Pageable)}
     */
    @Test
    void testGetAll() {

        // Arrange
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        PageImpl<Spaceship> pageImpl = new PageImpl<>(new ArrayList<>());
        when(spaceshipRepo.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<Spaceship> actualAll = (new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo)).getAll(null);

        // Assert
        verify(spaceshipRepo).findAll(Mockito.<Pageable>any());
        assertTrue(actualAll.toList().isEmpty());
        assertSame(pageImpl, actualAll);
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#showAllSpaceshipsRequest()}
     */
    @Test
    void testShowAllSpaceshipsRequest() throws Throwable {

        // Arrange
        RetryTemplate retryTemplate = mock(RetryTemplate.class);
        when(
                retryTemplate.execute(Mockito.<RetryCallback<Object, Throwable>>any(), Mockito.<RecoveryCallback<Object>>any()))
                .thenReturn("Execute");

        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setRetryTemplate(retryTemplate);
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        ArrayList<String> stringList = new ArrayList<>();
        when(spaceshipRepo.getAllByImageUrlNotNull()).thenReturn(stringList);

        // Act
        List<String> actualShowAllSpaceshipsRequestResult = (new SpaceshipServiceImpl(rabbitTemplate, spaceshipRepo))
                .showAllSpaceshipsRequest();

        // Assert
        verify(spaceshipRepo).getAllByImageUrlNotNull();
        verify(retryTemplate).execute(Mockito.<RetryCallback<Object, Throwable>>any(),
                Mockito.<RecoveryCallback<Object>>any());
        assertTrue(actualShowAllSpaceshipsRequestResult.isEmpty());
        assertSame(stringList, actualShowAllSpaceshipsRequestResult);
    }

    /**
     * Method under test:
     * {@link SpaceshipServiceImpl#addNewSpaceship(SpaceshipForm)}
     */
    @Test
    void testAddNewSpaceship() {
        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        when(spaceshipRepository.save(Mockito.<Spaceship>any())).thenReturn(spaceship);

        // Act
        Spaceship actualAddNewSpaceshipResult = spaceshipServiceImpl.addNewSpaceship(new SpaceshipForm());

        // Assert
        verify(spaceshipRepository).save(Mockito.<Spaceship>any());
        assertSame(spaceship, actualAddNewSpaceshipResult);
    }

    /**
     * Method under test:
     * {@link SpaceshipServiceImpl#updateSpaceship(Long, SpaceshipForm)}
     */
    @Test
    void testUpdateSpaceship() throws NotFoundException {

        // Arrange
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
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        when(spaceshipRepo.save(Mockito.<Spaceship>any())).thenReturn(spaceship2);
        when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        SpaceshipServiceImpl spaceshipServiceImpl = new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo);

        // Act
        Spaceship actualUpdateSpaceshipResult = spaceshipServiceImpl.updateSpaceship(1L, new SpaceshipForm());

        // Assert
        verify(spaceshipRepo).findById(Mockito.<Long>any());
        verify(spaceshipRepo).save(Mockito.<Spaceship>any());
        assertSame(spaceship2, actualUpdateSpaceshipResult);
    }

    /**
     * Method under test:
     * {@link SpaceshipServiceImpl#updateSpaceship(Long, SpaceshipForm)}
     */
    @Test
    void testUpdateSpaceship2() throws NotFoundException {

        // Arrange
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        Optional<Spaceship> emptyResult = Optional.empty();
        when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        SpaceshipServiceImpl spaceshipServiceImpl = new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> spaceshipServiceImpl.updateSpaceship(1L, new SpaceshipForm()));
        verify(spaceshipRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link SpaceshipServiceImpl#updateSpaceship(Long, SpaceshipForm)}
     */
    @Test
    void testUpdateSpaceship3() throws NotFoundException {

        // Arrange
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
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        when(spaceshipRepo.save(Mockito.<Spaceship>any())).thenReturn(spaceship2);
        when(spaceshipRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        SpaceshipServiceImpl spaceshipServiceImpl = new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo);
        SpaceshipForm SpaceshipForm = mock(SpaceshipForm.class);
        when(SpaceshipForm.getImageUrl()).thenReturn("https://example.org/example");
        when(SpaceshipForm.getSpaceshipName()).thenReturn("Spaceship Name");

        // Act
        Spaceship actualUpdateSpaceshipResult = spaceshipServiceImpl.updateSpaceship(1L, SpaceshipForm);

        // Assert
        verify(SpaceshipForm, atLeast(1)).getImageUrl();
        verify(SpaceshipForm, atLeast(1)).getSpaceshipName();
        verify(spaceshipRepo).findById(Mockito.<Long>any());
        verify(spaceshipRepo).save(Mockito.<Spaceship>any());
        assertSame(spaceship2, actualUpdateSpaceshipResult);
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#findByName(String, Pageable)}
     */
    @Test
    void testFindByName() throws EmptyListReturnException {

        // Arrange
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        when(spaceshipRepo.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(EmptyListReturnException.class,
                () -> (new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo)).findByName("Name", null));
        verify(spaceshipRepo).findByNameContainingIgnoreCase(eq("Name"));
    }

    /**
     * Method under test: {@link SpaceshipServiceImpl#findByName(String, Pageable)}
     */
    @Test
    void testFindByName2() throws EmptyListReturnException {

        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);

        ArrayList<Spaceship> spaceshipList = new ArrayList<>();
        spaceshipList.add(spaceship);
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        when(spaceshipRepo.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(spaceshipList);

        // Act
        List<Spaceship> actualFindByNameResult = (new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo))
                .findByName("Name", null);

        // Assert
        verify(spaceshipRepo).findByNameContainingIgnoreCase(eq("Name"));
        assertEquals(1, actualFindByNameResult.size());
        assertSame(spaceship, actualFindByNameResult.get(0));
    }
}
