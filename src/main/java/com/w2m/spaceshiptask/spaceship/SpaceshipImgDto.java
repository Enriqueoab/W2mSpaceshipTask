package com.w2m.spaceshiptask.spaceship;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dto to send some Spaceship info as message request")
public class SpaceshipImgDto implements Serializable {

    @Schema(description = "Spaceship reference, name, model, description", example = "N-1 Starfighter")
    private String name;

    @Schema(description = "Spaceship image URL", example = "https://shorturl.at/jowO4", defaultValue = "https://shorturl.at/jowO4")
    private String imageUrl;

    public SpaceshipImgDto(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
