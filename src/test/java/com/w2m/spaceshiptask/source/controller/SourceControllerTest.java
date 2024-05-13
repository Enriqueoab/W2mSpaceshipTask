package com.w2m.spaceshiptask.source.controller;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import com.w2m.spaceshiptask.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import com.w2m.spaceshiptask.source.service.SourceService;
import org.springframework.test.web.servlet.ResultActions;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SourceControllerTest extends TestUtils {
    @InjectMocks
    private SourceController sourceController;

    @Mock
    private SourceService sourceService;

    /**
     * Method under test:
     * {@link SourceController#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    public void updateSource() throws NotFoundException {
        Mockito.when(sourceService.updateSource(1L, emptySourceUpdateForm)).thenReturn(source);
        Assertions.assertNotNull(sourceController.updateSource(1L, emptySourceUpdateForm));
        Mockito.verify(sourceService).updateSource(1L, emptySourceUpdateForm);
    }

    /**
     * Method under test:
     * {@link SourceController#updateSource(Long, SourceUpdateForm)}
     */

    @Test
    public void updateSource_NotFoundException() throws NotFoundException {

        Mockito.when(sourceService.updateSource(NON_EXISTING_SOURCE_ID, emptySourceUpdateForm))
                                        .thenThrow(new NotFoundException("No source found"));
        Assertions.assertThrows(NotFoundException.class, () -> sourceController.updateSource(NON_EXISTING_SOURCE_ID, emptySourceUpdateForm));
    }

    /**
     * Method under test:
     * {@link SourceController#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void updateSource1() throws Exception {
        putRequestBuilder.contentType(MediaType.APPLICATION_JSON);

        MockHttpServletRequestBuilder requestBuilder = putRequestBuilder
                .content(objectMapper.writeValueAsString(emptySourceUpdateForm));

        var actualPerformResult = MockMvcBuilders.standaloneSetup(sourceController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test:
     * {@link SourceController#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void updateSource2() throws Exception {
        var user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        putRequestBuilder.principal(principal);
        MockHttpServletRequestBuilder contentTypeResult = putRequestBuilder.contentType(MediaType.APPLICATION_JSON);

        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(emptySourceUpdateForm));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sourceController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
