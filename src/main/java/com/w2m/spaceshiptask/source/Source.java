package com.w2m.spaceshiptask.source;

import com.w2m.spaceshiptask.spaceship.Spaceship;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import java.util.Set;

@Entity
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int premiereYear;

    private String name;

    private SourceType type;

    @OneToMany(mappedBy = "source", cascade = CascadeType.REMOVE)
    private Set<Spaceship> spaceships;

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
