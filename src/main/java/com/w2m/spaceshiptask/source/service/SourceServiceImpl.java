package com.w2m.spaceshiptask.source.service;

import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.source.repository.SourceRepository;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.utils.exception.messages.ExceptionMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Source updateSource(Long id, Source source) throws NotFoundException {

        var savedSource = findById(id);

        savedSource.setName(source.getName());
        savedSource.setPremiereYear(source.getPremiereYear());
        savedSource.setType(source.getType());

        return sourceRepo.save(savedSource);
    }
}
