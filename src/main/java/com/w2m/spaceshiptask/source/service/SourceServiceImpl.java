package com.w2m.spaceshiptask.source.service;

import com.w2m.spaceshiptask.source.Source;
import org.springframework.stereotype.Service;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.source.repository.SourceRepository;
import org.springframework.transaction.annotation.Transactional;
import com.w2m.spaceshiptask.utils.exception.messages.ExceptionMessages;

@Service
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepo;

    public SourceServiceImpl(SourceRepository sourceRepo) {
        this.sourceRepo = sourceRepo;
    }


    private Source findById(Long id) throws NotFoundException {
        return sourceRepo.findById(id).orElseThrow(() -> new NotFoundException(ExceptionMessages.SOURCE_NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public Source updateSource(Long id, SourceUpdateForm sourceForm) throws NotFoundException {

        var savedSource = findById(id);

        savedSource.setName(sourceForm.getName() != null ? sourceForm.getName() : savedSource.getName());

        savedSource.setPremiereYear(sourceForm.getPremiereYear() != null ? sourceForm.getPremiereYear() :
                                                                           savedSource.getPremiereYear());

        savedSource.setType(sourceForm.getType() != null ? sourceForm.getType() : savedSource.getType());

        return sourceRepo.save(savedSource);
    }
}
