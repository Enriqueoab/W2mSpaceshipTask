package com.w2m.spaceshiptask.source.service;

import org.mockito.Mock;
import java.util.Optional;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import com.w2m.spaceshiptask.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import com.w2m.spaceshiptask.source.Source;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.source.repository.SourceRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SourceServiceImplTest extends TestUtils {

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

        Optional<Source> ofResult = Optional.of(source);

        Mockito.when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        Mockito.when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, emptySourceUpdateForm);

        Mockito.verify(sourceRepository).findById(Mockito.<Long>any());
        Mockito.verify(sourceRepository).save(Mockito.<Source>any());
        Assertions.assertSame(source2, actualUpdateSourceResult);
    }

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource2() {
        Optional<Source> emptyResult = Optional.empty();
        Mockito.when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Assertions.assertThrows(NotFoundException.class, () ->
                            sourceServiceImpl.updateSource(1L, emptySourceUpdateForm));

        Mockito.verify(sourceRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link SourceServiceImpl#updateSource(Long, SourceUpdateForm)}
     */
    @Test
    void testUpdateSource3() throws NotFoundException {

        Optional<Source> ofResult = Optional.of(source);

        Mockito.when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        Mockito.when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, emptySourceUpdateForm);

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

        Optional<Source> ofResult = Optional.of(source);

        Mockito.when(sourceRepository.save(Mockito.<Source>any())).thenReturn(source2);
        Mockito.when(sourceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var actualUpdateSourceResult = sourceServiceImpl.updateSource(1L, emptySourceUpdateForm);

        Mockito.verify(sourceRepository).findById(Mockito.<Long>any());
        Mockito.verify(sourceRepository).save(Mockito.<Source>any());
        Assertions.assertSame(source2, actualUpdateSourceResult);
    }
}
