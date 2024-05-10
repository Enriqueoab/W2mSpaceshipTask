package com.w2m.spaceshiptask.source.controller;

import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.utils.form.SourceUpdateForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import com.w2m.spaceshiptask.source.service.SourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;

@Validated
@RestController
@Tag(name = "Source")
@RequestMapping("/source")
public class SourceController {

    private final SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @Operation(summary = "Update an already saved Spaceship record")
    @PutMapping(path = "/update/{id}")
    public Source updateSpaceship(@PathVariable Long id, @Valid @RequestBody SourceUpdateForm sourceForm)
                                                                            throws NotFoundException {
        return sourceService.updateSource(id, sourceForm);
    }

}
