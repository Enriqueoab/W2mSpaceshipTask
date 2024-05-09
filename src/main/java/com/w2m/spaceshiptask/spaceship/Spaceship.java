package com.w2m.spaceshiptask.spaceship;

import com.w2m.spaceshiptask.source.Source;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

@Entity
public class Spaceship {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id")
    private Source source;

    private String imageUrl;

    public Spaceship() {
    }

    public Spaceship(String name, Source source, String imageUrl) {
        this.name = name;
        this.source = source;
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Source getSource() {
        return source;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
