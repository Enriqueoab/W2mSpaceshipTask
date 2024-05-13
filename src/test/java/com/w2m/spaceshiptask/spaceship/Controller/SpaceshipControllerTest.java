package com.w2m.spaceshiptask.spaceship.Controller;

import java.util.List;
import org.mockito.Mock;
import java.util.ArrayList;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import com.w2m.spaceshiptask.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import com.w2m.spaceshiptask.source.Source;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import com.w2m.spaceshiptask.source.SourceType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.spaceshiptask.utils.form.SpaceshipForm;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.spaceship.service.SpaceshipService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.w2m.spaceshiptask.spaceship.service.SpaceshipServiceImpl;
import com.w2m.spaceshiptask.spaceship.repository.SpaceshipRepository;
import com.w2m.spaceshiptask.utils.exception.EmptyListReturnException;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SpaceshipControllerTest extends TestUtils {

    @InjectMocks
    private SpaceshipController spaceshipController;

    @Mock
    private SpaceshipService spaceshipService;

    @Mock
    private SpaceshipRepository spaceshipRepo;

    /**
     * Method under test: {@link SpaceshipController#getAll(Pageable)}
     */
    @Test
    void testGetAll() {

        PageImpl<Spaceship> pageImpl = new PageImpl<>(new ArrayList<>());
        Mockito.when(spaceshipRepo.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        Page<Spaceship> actualAll = (new SpaceshipController(new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo)))
                .getAll(null);

        Mockito.verify(spaceshipRepo).findAll(Mockito.<Pageable>any());
        Assertions.assertTrue(actualAll.toList().isEmpty());
        Assertions.assertSame(pageImpl, actualAll);
    }

    /**
     * Method under test: {@link SpaceshipController#findById(Long)}
     */
    @Test
    void testFindById() throws Exception {

        Mockito.when(spaceshipService.findById(Mockito.<Long>any())).thenReturn(spaceship);

        MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(getRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                .string("{\"id\":null,\"name\":\"x-wing\"," +
                        "\"source\":{\"id\":null,\"premiereYear\":1998,\"name\":\"Star Trek\",\"type\":\"SERIES\"}," +
                        "\"imageUrl\":\"https://shorturl.at/jowO4\"}"));
    }

    /**
     * Method under test: {@link SpaceshipController#findById(Long)}
     */
    @Test
    void testFindById2() throws Exception {

        Mockito.when(spaceshipService.findById(Mockito.<Long>any())).thenThrow(new NotFoundException("An error occurred"));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(getRequestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link SpaceshipController#removeSpaceship(Long)}
     */
    @Test
    void testRemoveSpaceship() throws Exception {

        Mockito.when(spaceshipService.removeSpaceship(Mockito.<Long>any())).thenReturn(HttpStatus.CONTINUE);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/spaceship/remove/{id}", 1L);

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

        Mockito.when(spaceshipService.removeSpaceship(Mockito.<Long>any())).thenThrow(new NotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/spaceship/remove/{id}", 1L);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link SpaceshipController#findByName(String, Pageable)}
     */
    @Test
    void testFindByName() throws EmptyListReturnException {

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

        Mockito.when(spaceshipRepo.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(spaceshipList);

        List<Spaceship> actualFindByNameResult = (new SpaceshipController(
                new SpaceshipServiceImpl(new RabbitTemplate(), spaceshipRepo))).findByName("Name", null);

        Mockito.verify(spaceshipRepo).findByNameContainingIgnoreCase(ArgumentMatchers.eq("Name"));
        Assertions.assertEquals(1, actualFindByNameResult.size());
        Assertions.assertSame(spaceship, actualFindByNameResult.get(0));
    }

    /**
     * Method under test: {@link SpaceshipController#addNewSpaceship(SpaceshipForm)}
     */
    @Test
    void testAddNewSpaceship() throws Exception {

        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Mockito.when(spaceshipService.addNewSpaceship(Mockito.<SpaceshipForm>any())).thenReturn(spaceship);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/spaceship/save")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SpaceshipForm()));

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

        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);

        Spaceship spaceship = new Spaceship();
        spaceship.setImageUrl("https://example.org/example");
        spaceship.setName("Name");
        spaceship.setSource(source);
        Mockito.when(spaceshipService.updateSpaceship(Mockito.<Long>any(), Mockito.<SpaceshipForm>any())).thenReturn(spaceship);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/spaceship/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SpaceshipForm()));

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

        Mockito.when(spaceshipService.updateSpaceship(Mockito.<Long>any(), Mockito.<SpaceshipForm>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/spaceship/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SpaceshipForm()));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link SpaceshipController#showAllSpaceships()}
     */
    @Test
    void testShowAllSpaceships() throws Exception {

        Mockito.when(spaceshipService.showAllSpaceshipsRequest()).thenReturn(new ArrayList<>());
        getRequestBuilder = MockMvcRequestBuilders.get("/spaceship/showSpaceships");

        MockMvcBuilders.standaloneSetup(spaceshipController)
                .build()
                .perform(getRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
