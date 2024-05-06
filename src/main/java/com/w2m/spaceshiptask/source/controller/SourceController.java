package com.w2m.spaceshiptask.source.controller;

import com.w2m.spaceshiptask.source.Source;
import org.springframework.stereotype.Controller;
import com.w2m.spaceshiptask.source.service.SourceService;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;

@Controller
public class SourceController {

    private final SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    public Source updateSpaceship(Long id, Source source) throws NotFoundException {
        return sourceService.updateSource(id, source);
    }

}
