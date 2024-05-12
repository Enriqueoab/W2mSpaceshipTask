package com.w2m.spaceshiptask.utils.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import com.w2m.spaceshiptask.source.SourceType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Form to Update the Source entity")
public class SourceUpdateForm {

    @Schema(description = "Film - series premiere year", example = "2001")
    @Max(value = 9999, message = "Year must be less than or equal to 9999")
    @Min(value = 1888, message = "Year must be greater than or equal to 1900")
    private int premiereYear;

    @Size(max = 25)
    @Schema(description = "Name of, film - series, where the Spaceship appears", example = "Star Wars")
    private String name;

    @Schema(description = "Reference to where the Spaceship was seen", example = "SERIES", allowableValues = { "FILM", "SERIES"})
    private SourceType type;

    public Integer getPremiereYear() {
        return premiereYear;
    }

    public String getName() {
        return name;
    }

    public SourceType getType() {
        return type;
    }
}
