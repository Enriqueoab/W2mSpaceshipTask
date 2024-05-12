package com.w2m.spaceshiptask.source.controller;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import com.w2m.spaceshiptask.source.service.SourceService;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SourceControllerTest {
    @InjectMocks
    private SourceController sourceController;

    @Mock
    private SourceService sourceService;

    /**
     * Method under test:
     * {@link SourceController#updateSpaceship(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSpaceship() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/source/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SourceUpdateForm()));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sourceController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test:
     * {@link SourceController#updateSpaceship(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSpaceship2() throws Exception {
        var user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/source/update/{id}", 1L);
        putResult.principal(principal);
        MockHttpServletRequestBuilder contentTypeResult = putResult.contentType(MediaType.APPLICATION_JSON);

        var objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SourceUpdateForm()));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sourceController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
