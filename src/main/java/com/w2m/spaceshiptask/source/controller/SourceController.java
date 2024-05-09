package com.w2m.spaceshiptask.source.controller;

import com.w2m.spaceshiptask.source.Source;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import com.w2m.spaceshiptask.source.service.SourceService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;

@RestController
@Tag(name = "Source")
@RequestMapping("/source")
@Controller
public class SourceController {

    private final SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @Operation(summary = "Update an already saved Spaceship record")
    @PutMapping(path = "/update/{id}")
    public Source updateSpaceship(@PathVariable Long id, Source source) throws NotFoundException {
        return sourceService.updateSource(id, source);
    }

}
