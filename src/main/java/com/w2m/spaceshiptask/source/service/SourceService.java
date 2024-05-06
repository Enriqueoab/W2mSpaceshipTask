package com.w2m.spaceshiptask.source.service;

import com.w2m.spaceshiptask.source.Source;
import com.w2m.spaceshiptask.spaceship.Spaceship;
import com.w2m.spaceshiptask.utils.exception.NotFoundException;

public interface SourceService {

    Source updateSource(Long id, Source source) throws NotFoundException;


}
