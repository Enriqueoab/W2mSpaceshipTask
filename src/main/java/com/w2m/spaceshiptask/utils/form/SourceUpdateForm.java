package com.w2m.spaceshiptask.utils.form;

import com.w2m.spaceshiptask.source.SourceType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SourceUpdateForm {

    @NotNull
    private Long id;

    @Min(value = 1888, message = "Year must be greater than or equal to 1900")
    @Max(value = 9999, message = "Year must be less than or equal to 9999")
    private int premiereYear;

    @Size(max = 25)
    private String name;

    private SourceType type;

}
