package com.w2m.spaceshiptask.source.service;

import org.mockito.Mock;
import java.util.Optional;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.source.SourceType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.source.repository.SourceRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SourceServiceImplTest {

    @InjectMocks
    private SourceServiceImpl sourceServiceImpl;

    @Mock
    private SourceRepository sourceRepository;

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource() throws NotFoundException {
        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);
        Optional<Source> ofResult = Optional.of(source);

        var source2 = new Source();
        source2.setName("Name");
        source2.setPremiereYear(1);
        source2.setType(SourceType.SERIES);
        Mockito.when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        Mockito.when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, new SourceUpdateForm());

        Mockito.verify(sourceRepository).findById(Mockito.<Long>any());
        Mockito.verify(sourceRepository).save(Mockito.<Source>any());
        Assertions.assertSame(source2, actualUpdateSourceResult);
    }

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource2() throws NotFoundException {
        Optional<Source> emptyResult = Optional.empty();
        Mockito.when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Assertions.assertThrows(NotFoundException.class, () -> sourceServiceImpl.updateSource(1L, new SourceUpdateForm()));
        Mockito.verify(sourceRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource3() throws NotFoundException {
        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);
        Optional<Source> ofResult = Optional.of(source);

        var source2 = new Source();
        source2.setName("Name");
        source2.setPremiereYear(1);
        source2.setType(SourceType.SERIES);
        Mockito.when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        Mockito.when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        SourceUpdateForm sourceForm = Mockito.mock(SourceUpdateForm.class);
        Mockito.when(sourceForm.getType()).thenReturn(SourceType.SERIES);
        Mockito.when(sourceForm.getPremiereYear()).thenReturn(1);
        Mockito.when(sourceForm.getName()).thenReturn("Name");

        var actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, sourceForm);

        Mockito.verify(sourceForm, Mockito.atLeast(1)).getName();
        Mockito.verify(sourceForm, Mockito.atLeast(1)).getPremiereYear();
        Mockito.verify(sourceForm, Mockito.atLeast(1)).getType();
        Mockito.verify(sourceRepository).findById(Mockito.<Long>any());
        Mockito.verify(sourceRepository).save(Mockito.<Source>any());
        Assertions.assertSame(source2, actualUpdateSourceResult);
    }

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource4() throws NotFoundException {

        var source = new Source();
        source.setName("Name");
        source.setPremiereYear(1);
        source.setType(SourceType.SERIES);
        Optional<Source> ofResult = Optional.of(source);

        var source2 = new Source();
        source2.setName("Name");
        source2.setPremiereYear(1);
        source2.setType(SourceType.SERIES);
        Mockito.when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        Mockito.when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        var sourceForm = Mockito.mock(SourceUpdateForm.class);
        Mockito.when(sourceForm.getType()).thenReturn(SourceType.SERIES);
        Mockito.when(sourceForm.getPremiereYear()).thenReturn(null);
        Mockito.when(sourceForm.getName()).thenReturn("Name");

        var actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, sourceForm);

        Mockito.verify(sourceForm, Mockito.atLeast(1)).getName();
        Mockito.verify(sourceForm).getPremiereYear();
        Mockito.verify(sourceForm, Mockito.atLeast(1)).getType();
        Mockito.verify(sourceRepository).findById(Mockito.<Long>any());
        Mockito.verify(sourceRepository).save(Mockito.<Source>any());
        Assertions.assertSame(source2, actualUpdateSourceResult);
    }
}
