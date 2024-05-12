package com.w2m.spaceshiptask.utils.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.w2m.spaceshiptask.source.SourceType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Form to Create or Update the Spaceship entity")
public class SpaceshipForm {

    public interface SpaceshipUpdateForm {}
    public interface SpaceshipCreateForm {}

    @Schema(description = "Spaceship reference, name, model, description", example = "Death star")
    @NotBlank(groups = { SpaceshipCreateForm.class })
    @Size(min = 3, max = 25, groups = { SpaceshipUpdateForm.class, SpaceshipCreateForm.class })
    private String spaceshipName;

    @Size(min = 10, max = 255, groups = { SpaceshipUpdateForm.class, SpaceshipCreateForm.class })
    @Schema(description = "Spaceship image URL", example = "https://shorturl.at/kpNQT", defaultValue = "https://shorturl.at/kpNQT")
    private String imageUrl = "https://shorturl.at/kpNQT";

    @NotNull(groups = { SpaceshipCreateForm.class })
    @Schema(description = "Film - series premiere year", example = "2001")
    @Max(value = 9999, message = "premiereYear must be less than 9999", groups = { SpaceshipCreateForm.class })
    @Min(value = 1888, message = "premiereYear must be greater than 1888", groups = { SpaceshipCreateForm.class })
    private int premiereYear;

    @NotBlank(groups = { SpaceshipCreateForm.class })
    @Schema(description = "Name of, film - series, where the Spaceship appears", example = "Star Wars")
    @Size(max = 25, groups = { SpaceshipUpdateForm.class, SpaceshipCreateForm.class }, message = "Max sourceName size 25")
    private String sourceName;

    @Schema(description = "Reference to where the Spaceship was seen", example = "SERIES", allowableValues = { "FILM", "SERIES"}, defaultValue = "FILM")
    private SourceType type = SourceType.FILM;

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
