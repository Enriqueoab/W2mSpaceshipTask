package com.w2m.spaceshiptask.source.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.source.SourceType;
import com.w2m.spaceshiptask.source.repository.SourceRepository;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SourceServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SourceServiceImplTest {
    @MockBean
    private SourceRepository sourceRepository;

    @Autowired
    private SourceServiceImpl sourceServiceImpl;

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource() throws NotFoundException {
        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);
        Optional<Source> ofResult = Optional.of(source);

        Source source2 = new Source();
        source2.setName("Name");
        source2.setPremiereYear(1);
        source2.setType(SourceType.SERIES);
        when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Source actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, new SourceUpdateForm());

        // Assert
        verify(sourceRepository).findById(Mockito.<Long>any());
        verify(sourceRepository).save(Mockito.<Source>any());
        assertSame(source2, actualUpdateSourceResult);
    }

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource2() throws NotFoundException {
        // Arrange
        Optional<Source> emptyResult = Optional.empty();
        when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> sourceServiceImpl.updateSource(1L, new SourceUpdateForm()));
        verify(sourceRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource3() throws NotFoundException {
        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);
        Optional<Source> ofResult = Optional.of(source);

        Source source2 = new Source();
        source2.setName("Name");
        source2.setPremiereYear(1);
        source2.setType(SourceType.SERIES);
        when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        SourceUpdateForm sourceForm = mock(SourceUpdateForm.class);
        when(sourceForm.getType()).thenReturn(SourceType.SERIES);
        when(sourceForm.getPremiereYear()).thenReturn(1);
        when(sourceForm.getName()).thenReturn("Name");

        // Act
        Source actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, sourceForm);

        // Assert
        verify(sourceForm, atLeast(1)).getName();
        verify(sourceForm, atLeast(1)).getPremiereYear();
        verify(sourceForm, atLeast(1)).getType();
        verify(sourceRepository).findById(Mockito.<Long>any());
        verify(sourceRepository).save(Mockito.<Source>any());
        assertSame(source2, actualUpdateSourceResult);
    }

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource4() throws NotFoundException {
        // Arrange
        Source source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);
        Optional<Source> ofResult = Optional.of(source);

        Source source2 = new Source();
        source2.setName("Name");
        source2.setPremiereYear(1);
        source2.setType(SourceType.SERIES);
        when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        SourceUpdateForm sourceForm = mock(SourceUpdateForm.class);
        when(sourceForm.getType()).thenReturn(SourceType.SERIES);
        when(sourceForm.getPremiereYear()).thenReturn(null);
        when(sourceForm.getName()).thenReturn("Name");

        // Act
        Source actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, sourceForm);

        // Assert
        verify(sourceForm, atLeast(1)).getName();
        verify(sourceForm).getPremiereYear();
        verify(sourceForm, atLeast(1)).getType();
        verify(sourceRepository).findById(Mockito.<Long>any());
        verify(sourceRepository).save(Mockito.<Source>any());
        assertSame(source2, actualUpdateSourceResult);
    }
}
