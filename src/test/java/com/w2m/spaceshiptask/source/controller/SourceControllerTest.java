package com.w2m.spaceshiptask.source.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.spaceshiptask.source.service.SourceService;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SourceController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SourceControllerTest {
    @Autowired
    private SourceController sourceController;

    @MockBean
    private SourceService sourceService;

    /**
     * Method under test:
     * {@link SourceController#updateSpaceship(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSpaceship() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/source/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SourceUpdateForm()));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sourceController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test:
     * {@link SourceController#updateSpaceship(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSpaceship2() throws Exception {
        // Arrange
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/source/update/{id}", 1L);
        putResult.principal(principal);
        MockHttpServletRequestBuilder contentTypeResult = putResult.contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new SourceUpdateForm()));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sourceController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
