package com.w2m.spaceshiptask;

import org.junit.jupiter.api.BeforeEach;
import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.source.SourceType;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class TestUtils {

    protected static final Long NON_EXISTING_SOURCE_ID = 33L;

    protected MockHttpServletRequestBuilder getRequestBuilder;

    protected MockHttpServletRequestBuilder putRequestBuilder;

    protected SourceUpdateForm emptySourceUpdateForm;

    protected ObjectMapper objectMapper;

    protected Spaceship spaceship;

    protected Spaceship spaceship2;

    protected Source source;

    protected Source source2;

    @BeforeEach
    public void init()  {

        source = new Source(1998, "Star Trek", SourceType.SERIES);

        spaceship = new Spaceship("x-wing", source, "https://shorturl.at/jowO4");


        source2 = new Source(2001, "Star Wars", SourceType.FILM);

        spaceship2 = new Spaceship("Star Fighter", source, "https://shorturl.at/jowO4");


        emptySourceUpdateForm = new SourceUpdateForm();

        objectMapper = new ObjectMapper();

        getRequestBuilder = MockMvcRequestBuilders.get("/spaceship/{id}", 1L);

        putRequestBuilder = MockMvcRequestBuilders.put("/source/update/{id}", 1L);
    }

}
