package com.w2m.spaceshiptask.spaceship.Controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.utils.form.SpaceshipForm;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.w2m.spaceshiptask.spaceship.service.SpaceshipService;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;
import com.w2m.spaceshiptask.utils.exception.NotExpectedResultException;

@Validated
@RestController
@Tag(name = "Spaceship")
@RequestMapping("/spaceship")
public class SpaceshipController {

    private final SpaceshipService spaceshipService;

    public SpaceshipController(SpaceshipService spaceshipService) {
        this.spaceshipService = spaceshipService;
    }

    @Operation(summary = "Get all Spaceship records")
    @GetMapping(path = "/all")
    public Page<Spaceship> getAll(Pageable pageable) {
        return spaceshipService.getAll(pageable);
    }

    @Operation(summary = "Get Spaceship by id")
    @GetMapping(path = "/{id}")
    public Spaceship findById(@PathVariable Long id) throws NotFoundException {
        return spaceshipService.findById(id);
    }

    @Operation(summary = "Remove Spaceship by id")
    @DeleteMapping(path = "/remove/{id}")
    public HttpStatus removeSpaceship(@PathVariable Long id) throws NotExpectedResultException, NotFoundException {
        return spaceshipService.removeSpaceship(id);
    }

    @Operation(summary = "Find Spaceship by name")
    @GetMapping(path = "/byName")
    public Page<Spaceship> findByName(@RequestParam String name, Pageable pageable) {
        return spaceshipService.findByName(name, pageable);
    }

    @Operation(summary = "Saved new Spaceship record")
    @PostMapping(path = "/save")
    @Validated(SpaceshipForm.SpaceshipCreateForm.class)
    public Spaceship addNewSpaceship(@Valid @RequestBody SpaceshipForm spaceshipForm) {
        return spaceshipService.addNewSpaceship(spaceshipForm);
    }

    @Operation(summary = "Update an already saved Spaceship record")
    @PutMapping(path = "/update/{id}")
    @Validated(SpaceshipForm.SpaceshipUpdateForm.class)
    public Spaceship updateSpaceship(@PathVariable Long id, @Valid @RequestBody SpaceshipForm spaceshipForm) throws NotFoundException {
        return spaceshipService.updateSpaceship(id, spaceshipForm);
    }

}
