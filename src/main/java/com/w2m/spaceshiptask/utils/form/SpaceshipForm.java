package com.w2m.spaceshiptask.utils.form;

import com.w2m.spaceshiptask.source.SourceType;
import jakarta.validation.constraints.*;

public class SpaceshipForm {

    public interface SpaceshipUpdateForm {}
    public interface SpaceshipCreateForm {}

    @Size(min = 3, max = 25, groups = { SpaceshipUpdateForm.class, SpaceshipCreateForm.class })
    @NotBlank(groups = { SpaceshipCreateForm.class })
    private String spaceshipName;

    private String imageUrl;

    @Min(value = 1888, message = "premiereYear must be greater than 1888", groups = { SpaceshipUpdateForm.class, SpaceshipCreateForm.class })
    @Max(value = 9999, message = "premiereYear must be less than 9999", groups = { SpaceshipUpdateForm.class, SpaceshipCreateForm.class })
    @NotNull(groups = { SpaceshipCreateForm.class })
    private int premiereYear;

    @Size(max = 25, groups = { SpaceshipUpdateForm.class, SpaceshipCreateForm.class }, message = "Max sourceName size 25")
    @NotBlank(groups = { SpaceshipCreateForm.class })
    private String sourceName;

    @NotNull(groups = { SpaceshipCreateForm.class }, message = "The types allowed are FILM or SERIES")
    private SourceType type;

    public String getSpaceshipName() {
        return spaceshipName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPremiereYear() {
        return premiereYear;
    }

    public String getSourceName() {
        return sourceName;
    }

    public SourceType getType() {
        return type;
    }
}
