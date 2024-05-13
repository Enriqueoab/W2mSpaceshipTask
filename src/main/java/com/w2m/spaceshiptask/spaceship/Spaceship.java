package com.w2m.spaceshiptask.spaceship;

import java.io.Serializable;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import org.hibernate.annotations.Cache;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import com.w2m.spaceshiptask.source.Source;
import org.springframework.cache.annotation.Cacheable;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Spaceship implements Serializable {

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
