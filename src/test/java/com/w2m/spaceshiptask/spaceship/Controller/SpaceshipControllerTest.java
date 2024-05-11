package com.w2m.spaceshiptask.spaceship.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.source.SourceType;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.spaceship.repository.SpaceshipRepository;
import com.w2m.spaceshiptask.spaceship.service.SpaceshipService;
import com.w2m.spaceshiptask.spaceship.service.SpaceshipServiceImpl;
import com.w2m.spaceshiptask.utils.exception.EmptyListReturnException;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.utils.form.SpaceshipForm;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SpaceshipController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SpaceshipControllerTest {
    @Autowired
    private SpaceshipController spaceshipController;

    @MockBean
    private SpaceshipService spaceshipService;

    /**
     * Method under test: {@link SpaceshipController#getAll(Pageable)}
     */
    @Test
    void testGetAll() {

        // Arrange
        SpaceshipRepository spaceshipRepo = mock(SpaceshipRepository.class);
        PageImpl<Spaceship> pageImpl = new PageImpl<>(new ArrayList<>());
        when(spaceshipRepo.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<Spaceship> actualAll = (new SpaceshipController(new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo)))
                .getAll(null);

        // Assert
        verify(spaceshipRepo).findAll(Mockito.<Pageable>any());
        assertTrue(actualAll.toList().isEmpty());
        assertSame(pageImpl, actualAll);
    }

    /**
     * Method under test: {@link SpaceshipController#findById(Long)}
     */
    @Test
    void testFindById() throws Exception {
        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        when(spaceshipService.findById(Mockito.<Long>any())).thenReturn(spaceship);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spaceship/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"name\":\"Name\",\"source\":{\"id\":null,\"premiereYear\":1,\"name\":\"Name\",\"type\":\"SERIES\"},\"imageUrl"
                                        + "\":\"https://example.org/example\"}"));
    }

    /**
     * Method under test: {@link SpaceshipController#findById(Long)}
     */
    @Test
    void testFindById2() throws Exception {
        // Arrange
        when(spaceshipService.findById(Mockito.<Long>any())).thenThrow(new NotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spaceship/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link SpaceshipController#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship() throws Exception {
        // Arrange
        when(spaceshipService.removeSpaceship(Mockito.<Long>any())).thenReturn(HttpStatus.CONTINUE);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/spaceship/remove/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("\"CONTINUE\""));
    }

    /**
     * Method under test: {@link SpaceshipController#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship2() throws Exception {
        // Arrange
        when(spaceshipService.removeSpaceship(Mockito.<Long>any())).thenThrow(new NotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/spaceship/remove/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link SpaceshipController#findByName(String, Pageable)}
     */
    @Test
    void testFindByName() throws EmptyListReturnException {

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
        List<Spaceship> actualFindByNameResult = (new SpaceshipController(
                new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo))).findByName("Name", null);

        // Assert
        verify(spaceshipRepo).findByNameContainingIgnoreCase(eq("Name"));
        assertEquals(1, actualFindByNameResult.size());
        assertSame(spaceship, actualFindByNameResult.get(0));
    }

    /**
     * Method under test: {@link SpaceshipController#addNewSpaceship(SpaceshipForm)}
     */
    @Test
    void testAddNewSpaceship() throws Exception {
        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        when(spaceshipService.addNewSpaceship(Mockito.<SpaceshipForm>any())).thenReturn(spaceship);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/spaceship/save")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SpaceshipForm()));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"name\":\"Name\",\"source\":{\"id\":null,\"premiereYear\":1,\"name\":\"Name\",\"type\":\"SERIES\"},\"imageUrl"
                                        + "\":\"https://example.org/example\"}"));
    }

    /**
     * Method under test:
     * {@link SpaceshipController#updateSpaceship(Long, SpaceshipForm)}
     */
    @Test
    void testUpdateSpaceship() throws Exception {
        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        when(spaceshipService.updateSpaceship(Mockito.<Long>any(), Mockito.<SpaceshipForm>any())).thenReturn(spaceship);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/spaceship/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SpaceshipForm()));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"name\":\"Name\",\"source\":{\"id\":null,\"premiereYear\":1,\"name\":\"Name\",\"type\":\"SERIES\"},\"imageUrl"
                                        + "\":\"https://example.org/example\"}"));
    }

    /**
     * Method under test:
     * {@link SpaceshipController#updateSpaceship(Long, SpaceshipForm)}
     */
    @Test
    void testUpdateSpaceship2() throws Exception {
        // Arrange
        when(spaceshipService.updateSpaceship(Mockito.<Long>any(), Mockito.<SpaceshipForm>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/spaceship/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SpaceshipForm()));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link SpaceshipController#showAllSpaceships()}
     */
    @Test
    void testShowAllSpaceships() throws Exception {
        // Arrange
        when(spaceshipService.showAllSpaceshipsRequest()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spaceship/showSpaceships");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
