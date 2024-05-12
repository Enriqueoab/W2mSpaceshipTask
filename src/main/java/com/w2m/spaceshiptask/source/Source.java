package com.w2m.spaceshiptask.source;

import java.util.Set;
import java.io.Serializable;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import org.hibernate.annotations.Cache;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import org.springframework.cache.annotation.Cacheable;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Source implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int premiereYear;

    private String name;

    private SourceType type;

    @OneToMany(mappedBy = "source", cascade = CascadeType.REMOVE)
    private Set<Spaceship> spaceships;

    public Source() {
    }

    public Source(int year, String name, SourceType type) {
        this.premiereYear = year;
        this.name = name;
        this.type = type;
    }

    public int getPremiereYear() {
        return premiereYear;
    }

    public void setPremiereYear(int premiereYear) {
        this.premiereYear = premiereYear;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }
}
